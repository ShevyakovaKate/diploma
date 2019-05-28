package backend.demo.service.impl;

import backend.demo.analysisFunctions.model.Model;
import backend.demo.analysisFunctions.model.impl.FFSModel;
import backend.demo.analysisFunctions.qualityAnalysisUtil.Hi2;
import backend.demo.analysisFunctions.model.impl.PhaseFrequencyModel;
import backend.demo.analysisFunctions.qualityAnalysisUtil.WeightedAverageBalance;
import backend.demo.model.AnalysisData;
import backend.demo.model.InputFileData;
import backend.demo.model.Parameter;
import backend.demo.service.api.AnalysisServiceApi;
import org.apache.commons.math3.linear.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MarquardtAnalysisServiceImpl implements AnalysisServiceApi {
    private Model model;

    @Autowired
    public MarquardtAnalysisServiceImpl(){}

    @Override
    public AnalysisData startAnalysis(double[] inputValues, List<Parameter> parametersList, double[] outputRealValues,
                                  Double sigma, int modelID) {
        switch (modelID) {
            case PhaseFrequencyModel.ModelID: {
               this.model = new PhaseFrequencyModel();
                break;
            }
            case FFSModel.ModelID: {
                this.model = new FFSModel();
                break;
            }
        }

        //get parameters as arrays
        double[] parameters = new double[parametersList.size()];
        double[] parametersMin = new double[parametersList.size()];
        double[] parametersMax = new double[parametersList.size()];
        for (int i = 0; i < parametersList.size(); i++) {
            parameters[i] = parametersList.get(i).get_value();
            parametersMin[i] = parametersList.get(i).get_minValue();
            parametersMax[i] = parametersList.get(i).get_maxValue();
        }

        //count hi2
        double[] model = this.model.countModel(parameters, inputValues);
        double hi2 = Hi2.countHi2(model, outputRealValues, sigma, this.model.ParametersNumbers);

        double lambda = 0.001;
        double lambdaMax = 10E10;
        double eps = 10E-5;
        double hi2New = 10000;
        double[] modelNew = new double[0];


        while ((lambda < lambdaMax) && ((Math.abs(hi2New - hi2 ) / hi2 ) > eps)) {
            if(hi2New < hi2) {
                hi2 = hi2New;
            }
            //count new parameters
            double[] newParameters = optimizeParameters(parameters,
                    inputValues,
                    outputRealValues,
                    sigma, modelID,
                    lambda);

            //count new hi2
            modelNew = this.model.countModel(
                    newParametersChecking(newParameters, parameters, parametersMin, parametersMax),
                    inputValues);

            hi2New = Hi2.countHi2(modelNew, outputRealValues, sigma, this.model.ParametersNumbers);

            if (hi2New < hi2) {
                parameters = newParameters;
                lambda = 0.1 * lambda;
            } else {
                lambda = 10 * lambda;
            }
        }

        double[] weightedAverageBalance = WeightedAverageBalance.getWeightedAverageBalance(modelNew, outputRealValues, sigma);
        double[] autocorrelationalOfWeightedAverageBalances = WeightedAverageBalance.getAutocorrelationalOfWeightedAverageBalances(weightedAverageBalance, this.model.ParametersNumbers);
        AnalysisData analysisData = transformToAnalysisData(parametersList, parameters, hi2New);
        analysisData.setWeightedAverageBalances(weightedAverageBalance);
        analysisData.setAutocorrelationalFunction(autocorrelationalOfWeightedAverageBalances);
        return analysisData;
    }

    private double[] newParametersChecking(double[] newParameters, double[] oldParameters, double[] parametersMin, double[] parametersMax) {
        for (int i = 0; i < newParameters.length; i++) {
            if (newParameters[i] < parametersMin[i] || newParameters[i] > parametersMax[i]) {
                newParameters[i] = oldParameters[i];
            }
        }
        return newParameters;
    }

    private double[] optimizeParameters(double[] parameters, double[] inputValues, double[] realValues, double sigma, int modelID, double lambda) {
        List<double[][]> matrix = calcMatrix(parameters, inputValues, realValues, sigma, modelID);
        double[][] A = matrix.get(0);
        double[][] B = matrix.get(1);


        for (int i = 0; i < A.length; i++) {
            A[i][i] = A[i][i] * (1 + lambda);
        }

        double[] b = new double[B.length*B[0].length];
        for(int i = 0; i < B.length; i++) {
            double[] row = B[i];
            for (int j = 0; j < row.length; j++) {
                double number = B[i][j];
                b[i*row.length+j] = number;
            }
        }

        double[] accessionArray =  solveLinearSystem(A, b);

        //add accessions to parameters
        double[] newParameters = new double[parameters.length];
        for (int i = 0; i < accessionArray.length; i++) {
            newParameters[i]= parameters[i] + accessionArray[i];
        }

        return newParameters;
    }

    /*
    * startApproxParameters Phase model - a1, t1, a2, t2;
    * startApproxParameters FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
    * */
    private List<double[][]> calcMatrix(double[] parameters, double[] inputValues, double[] realValues,
                                        double sigma, int modelID) {
        int parametersSize = parameters.length;
        double[][] A = new double[parametersSize][parametersSize];
        double[][] B = new double[1][parametersSize];

        //count model
        double[] Fth = this.model.countModel(parameters, inputValues);

        //count A matrix
        for (int i = 0; i < parametersSize; i++) {
            for (int j = 0; j < parametersSize; j++) {

                A[i][j] = countAElement(inputValues, parameters, Fth, sigma, i, j, modelID);
            }
        }

        //count B matrix
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < parametersSize; j++) {
                B[i][j] = countBElement(inputValues, parameters, Fth, realValues, sigma, j, modelID);
            }
        }

        return Arrays.asList(A, B);
    }

    private double countAElement (double[] inputValues, double[] parameters, double[] Fth,
                                  double sigma, int iEl, int jEl, int modelID) {
        int inputValuesLength = inputValues.length;
        double returnValue = 0;

        //count derivative on i element
        double[] iDerivative = countDerivative(inputValues,  parameters, Fth, iEl, modelID);
        //count derivative on j element
        double[] jDerivative = countDerivative(inputValues,  parameters, Fth, jEl, modelID);

        //count matrix point
        for (int i =0; i < inputValuesLength; i++) {
            returnValue += (iDerivative[i] * jDerivative[i]) / (sigma * sigma);
        }
        return returnValue;
    }

    private double countBElement(double[] inputValues, double[] parameters, double[] Fth,
                                 double[] realValues, double sigma, int jEl, int modelID) {
        int inputValuesLength = inputValues.length;
        double returnValue = 0;

        //count derivative on j element
        double[] jDerivative = countDerivative(inputValues,  parameters, Fth, jEl, modelID);

        for (int i = 0; i < inputValuesLength; i++) {
            returnValue +=  (realValues[i] - Fth[i]) / (sigma * sigma) * jDerivative[i];
        }

        return returnValue;
    }

    private double[] countDerivative(double[] inputValues, double[] parameters, double[] Fth,
                                     int jEl, int modelID) {

        int inputValuesLength = inputValues.length;
        double[] returnDerivative = new double[inputValuesLength];

        double derivativeElement = parameters[jEl];

        //increment parameter
        double[] newParameters = parameters.clone();
        double parameterIncrement = parameters[jEl] * 0.05;
        newParameters[jEl] = parameters[jEl] + parameterIncrement;

        //count new model
        double[] Fnew =  this.model.countModel(newParameters, inputValues);

        //derivative
        for (int i = 0; i < inputValuesLength; i++) {
            returnDerivative[i] = (Fnew[i] - Fth[i]) / parameterIncrement;
        }
        return returnDerivative;
    }


    private double[] solveLinearSystem(double[][] a, double[] b) {
        RealMatrix coefficients =
                new Array2DRowRealMatrix(a, false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

        RealVector constants = new ArrayRealVector(b, false);
        RealVector solution = solver.solve(constants);

        double[] accessionArray = new double[b.length];
        for(int i = 0; i < b.length; i++) {
            accessionArray[i] = solution.getEntry(i);
        }
        return accessionArray;
    }

    private AnalysisData transformToAnalysisData(List<Parameter> initParametersList, double[] parameters,  double hi2New) {
        AnalysisData analysisData = new AnalysisData();
        List<Parameter> parameterList = new ArrayList<>();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = new Parameter();
            String parameterName = initParametersList.get(i).get_name();
            double parameterMinValue = initParametersList.get(i).get_minValue();
            double parameterMaxValue = initParametersList.get(i).get_maxValue();
            double parameterValue = parameters[i];

            parameter.set_name(parameterName);
            parameter.set_value(parameterValue);
            parameter.set_minValue(parameterMinValue);
            parameter.set_maxValue(parameterMaxValue);
            parameterList.add(parameter);
        }

        analysisData.setParameters(parameterList);
        analysisData.setHi2(hi2New);

        return analysisData;
    }


}

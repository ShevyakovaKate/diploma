package backend.demo.service.impl;

import backend.demo.analysis.model.FFSModel;
import backend.demo.analysis.model.Hi2;
import backend.demo.analysis.model.PhaseFrequencyModel;
import backend.demo.service.api.AnalysisServiceApi;
import backend.demo.service.api.ParseInputFileServiceApi;
import javafx.util.Pair;
import org.apache.commons.math3.linear.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisServiceApi {
    private static final Integer phaseParametersNumbers = 4;
    private static final Integer FFSParametersNumbers = 5;
    private Integer parametersNumber;

    @Autowired
    public AnalysisServiceImpl(){}

    @Override
    public double[] startAnalysis(double[] inputValues, double[] parameters, double[] outputRealValues, Double sigma, int modelID, double[] parametersMin, double[] parametersMax) {
        switch (modelID) {
            case PhaseFrequencyModel.ModelID: {
                parametersNumber = phaseParametersNumbers;
                break;
            }
            case FFSModel.ModelID: {parametersNumber = FFSParametersNumbers;
            break;}

        }
        //count hi2
        double[] model = countModel(parameters, inputValues, modelID);
        double hi2 = Hi2.countHi2(model, outputRealValues, sigma, parametersNumber);

        double lambda = 0.001;
        double lambdaMax = 10E10;
        double eps = 10E-5;
        double hi2New = 10000;///как задать его

        while ((lambda < lambdaMax) && ((Math.abs(hi2New - hi2 ) / hi2 ) > eps)) {
            if(hi2New < hi2) {
                hi2 = hi2New;
            }
            //count new parameters
            double[] newParameters = optimizeParameters(parameters, inputValues, outputRealValues, sigma, modelID, lambda);


            newParameters = newParametersChecking(newParameters, parameters, parametersMin, parametersMax);

            //count new hi2
            double[] modelNew = countModel(newParameters, inputValues, modelID);
            hi2New = Hi2.countHi2(modelNew, outputRealValues, sigma, parametersNumber);

            if (hi2New < hi2) {
                parameters = newParameters;
                lambda = 0.1 * lambda;
            } else {
                lambda = 10 * lambda;
            }
        }

        return parameters;

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
        double[] Fth = countModel(parameters, inputValues, modelID);

        /*double[] jDerivative1 = countDerivative(inputValues,  parameters, Fth, 0, modelID);
        double[] jDerivative2 = countDerivative(inputValues,  parameters, Fth, 1, modelID);
        double[] jDerivative3 = countDerivative(inputValues,  parameters, Fth, 2, modelID);
        double[] jDerivative4 = countDerivative(inputValues,  parameters, Fth, 3, modelID);*/
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

        /*//increment input values
        double[] inputValuesIncremented = new double[inputValuesLength];
        for (int i = 0; i < inputValuesLength; i++) {
            inputValuesIncremented[i] = inputValues[i] + (inputValues[i] * 0.00001);
        }*/

        //count new model
        double[] Fnew =  countModel(newParameters, inputValues, modelID);

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
    /*
     * startApproxParameters Phase model - a1, a2, t1, t2;
     * startApproxParameters FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
     * */
    @Override
    public double[] countModel(double[] parameters, double[] inputValues, int modelID) {
        switch (modelID) {
            case PhaseFrequencyModel.ModelID: {
                double a1 = parameters[0];
                double a2 = parameters[2];
                double t1 = parameters[1];
                double t2 = parameters[3];
                Pair<Double, Double> component1 = new Pair<>(a1, t1);
                Pair<Double, Double> component2 = new Pair<>(a2, t2);
                List<Pair<Double, Double>> components = new ArrayList<>();

                components.add(component1);
                components.add(component2);

               return PhaseFrequencyModel.countModel(inputValues, components);
            }
            case FFSModel.ModelID: {
                double N_eff = parameters[0];
                double f_trip = parameters[1];
                double tay_trip = parameters[2];
                double tay_diff = parameters[3];
                double a = parameters[4];

                return FFSModel.countModel(inputValues, N_eff, f_trip, tay_trip, tay_diff, a);
            }
            default: return new double[0];
        }
    }


}

package backend.demo.service.impl;

import backend.demo.analysis.model.FFSModel;
import backend.demo.analysis.model.Hi2;
import backend.demo.analysis.model.PhaseFrequencyModel;
import backend.demo.service.api.AnalysisServiceApi;
import com.sun.deploy.util.ArrayUtil;
import javafx.util.Pair;
import org.ejml.data.Matrix;
import org.ejml.data.SingularMatrixException;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.simple.SimpleMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisServiceApi {
    private static final Integer phaseParametersNumbers = 4;
    private static final Integer FFSParametersNumbers = 5;

    @Autowired
    public AnalysisServiceImpl(){}

    @Override
    public void startAnalysis(double[] inputValues, double[] parameters, double[] outputRealValues, Double sigma, int modelID) {
        int i = parameters.length;
        optimize(parameters, inputValues, outputRealValues, sigma, modelID);
    }

    public void optimize(double[] parameters, double[] inputValues, double[] realValues, double sigma, int modelID) {
        double lambda = 0.01;
        List<double[][]> matrix = calcMatrix(parameters, inputValues, realValues, sigma, modelID);
        double[][] A = matrix.get(0);
        double[][] B = matrix.get(1);


        for (int i = 0; i < A.length; i++) {
            A[i][i] = A[i][i] + lambda;
        }

    }

    /*
    * startApproxParameters Phase model - a1, a2, t1, t2;
    * startApproxParameters FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
    * */
    private List<double[][]> calcMatrix(double[] parameters, double[] inputValues, double[] realValues,
                                        double sigma, int modelID) {
        int parametersSize = parameters.length;
        double[][] A = new double[parametersSize][parametersSize];
        double[][] B = new double[1][parametersSize];

        //count model
        double[] Fth = countModel(parameters, inputValues, modelID);

        //count A matrix
        for (int i = 0; i < parametersSize; i++) {
            for (int j = 0; j < parametersSize; j++) {
                A[i][j] = - countAElement(inputValues, parameters, Fth, sigma, i, j, modelID);
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
        double parameterIncrement = parameters[jEl] * 0.00001;
        newParameters[jEl] = parameters[jEl] + parameterIncrement;

        //increment input values
        double[] inputValuesIncremented = new double[inputValuesLength];
        for (int i = 0; i < inputValuesLength; i++) {
            inputValuesIncremented[i] = inputValues[i] + (inputValues[i] * 0.00001);
        }


        //count new model
        double[] Fnew =  countModel(newParameters, inputValuesIncremented, modelID);

        //derivative
        for (int i = 0; i < inputValuesLength; i++) {
            returnDerivative[i] = (Fnew[i] - Fth[i]) / parameterIncrement;
        }
        return returnDerivative;
    }


    /*
     * startApproxParameters Phase model - a1, a2, t1, t2;
     * startApproxParameters FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
     * */
    private double[] countModel(double[] parameters, double[] inputValues, int modelID) {
        switch (modelID) {
            case PhaseFrequencyModel.ModelID: {
                double a1 = parameters[0];
                double a2 = parameters[1];
                double t1 = parameters[2];
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

package backend.demo.service.impl;

import backend.demo.analysis.model.FFSModel;
import backend.demo.analysis.model.Hi2;
import backend.demo.analysis.model.PhaseFrequencyModel;
import backend.demo.service.api.AnalysisServiceApi;
import com.sun.deploy.util.ArrayUtil;
import javafx.util.Pair;
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
    public Double startPhaseFrequencyAnalysis(double[] frequencies, List<Pair<Double, Double>> components,
                                              double[] realValues, Double sigma) {

        double[] modelValues = PhaseFrequencyModel.countModel(frequencies, components);
        return Hi2.countHi2(modelValues, realValues, sigma, phaseParametersNumbers);
    }

   /* @Override
    public Double startFFSAnalysis(List<Double> tays, Double N_eff, Double f_trip, Double tay_trip,
                                   Double tay_diff, Double a, List<Double> realValues, Double sigma) {

        List<Double> modelValues = FFSModel.countModel(tays, N_eff, f_trip, tay_trip, tay_diff, a);
        return Hi2.countHi2(modelValues, realValues, sigma, FFSParametersNumbers);
    }*/

    private void optimize(double[] parameters, double[] inputValues, double sigma, int modelID) {
        List<double[][]> matrix = calcMatrix(parameters, inputValues, sigma, modelID);
        double[][] A = matrix.get(0);
        double[][] B = matrix.get(1);

    }

    /*
    * startApproxParameters Phase model - a1, a2, t1, t2;
    * startApproxParameters FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
    * */
    private List<double[][]> calcMatrix(double[] startApproxParameters, double[] inputValues, double sigma, int modelID) {
        int parametersSize = startApproxParameters.length;
        double[][] A = new double[parametersSize][parametersSize];
        double[][] B = new double[parametersSize][1];

        double[] Fth = countModel(startApproxParameters, inputValues, modelID);

        for(int i = 0; i <= parametersSize; i++) {
            for (int j = 0; j <= parametersSize; j++) {

                A[i][j] = countAElement(inputValues, startApproxParameters, Fth, sigma,  i, j) ;
                //todo производные
            }
        }
/*
        for(int i = 0; i <= 1; i++) {
            for (int j = 0; j <= parametersSize; j++) {

                B[i][j] = countMatrixElement(inputValues, startApproxParameters, Fth, i, j) ;
                //todo производные
            }
        }*/

       return Arrays.asList(A, B);
    }

    private double countAElement(double[] inputValues, double[] parameters, double[] Fth, double sigma, int iEl, int jEl) {
        int inputValuesLenght = inputValues.length;
        double returnValue = 0;

        double[] iDerivative = countDerivative(inputValues,  parameters, Fth, iEl);
        double[] jDerivative = countDerivative(inputValues,  parameters, Fth, iEl);

        for (int i =0; i <= inputValuesLenght; i++) {
            returnValue += (iDerivative[i] * jDerivative[i]) / (sigma * sigma);
        }
        return returnValue;
    }

    private double countBElement(double[] inputValues, double[] parameters, double[] Fth, int iEl, int jEl) {
        double[] F = new double[Fth.length];



        for (int i = 0; i <= inputValues.length; i++) {
            F[i] - Fth[i] / accession
        }
        return 0;
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
        }
    }
}

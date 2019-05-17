package backend.demo.analysis.model;

import javafx.util.Pair;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class PhaseFrequencyModel {
    public static final int ModelID = 1;

    public static double[] countModel(double[] frequencies, List<Pair<Double, Double>> components) {
        double[] phaseFrequencyResult = new double[frequencies.length];

        for (int i = 0; i < frequencies.length; i++) {
            phaseFrequencyResult[i] = (countPoint(frequencies[i], components));
        }
        return phaseFrequencyResult;
    };

    private static Double countPoint(double frequency,  List<Pair<Double, Double>> components) {
        double numerator = 0;
        double denominator = 0;
        double temp = 0;

        for (Pair<Double, Double> component: components) {
            double a = component.getKey();
            double t = component.getValue();

            temp = a * t /(1 + frequency *frequency * t * t);
            numerator += temp * frequency * t;
            denominator += temp;
        }

        return Math.atan2(numerator, denominator);
    }
}

package backend.demo.analysisFunctions.model.impl;

import backend.demo.analysisFunctions.model.Model;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class PhaseFrequencyModel implements Model {
    public static final int ModelID = 1;
    private static final Integer ParametersNumbers = 4;

    @Override
    public double[] countModel(double[] parameters, double[] inputValues) {
        double a1 = parameters[0];
        double a2 = parameters[1];
        double t1 = parameters[2];
        double t2 = parameters[3];
        Pair<Double, Double> component1 = new Pair<>(a1, t1);
        Pair<Double, Double> component2 = new Pair<>(a2, t2);
        List<Pair<Double, Double>> components = new ArrayList<>();

        components.add(component1);
        components.add(component2);
        return count(inputValues, components);
    }

    private double[] count(double[] frequencies, List<Pair<Double, Double>> components) {
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

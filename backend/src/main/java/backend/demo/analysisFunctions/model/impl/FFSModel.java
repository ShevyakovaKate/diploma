package backend.demo.analysisFunctions.model.impl;


import backend.demo.analysisFunctions.model.Model;

public class FFSModel implements Model {
    public static final int ModelID = 2;
    public static final Integer ParametersNumbers = 5;

    @Override
    public double[] countModel(double[] parameters, double[] inputValues) {
        double N_eff = parameters[0];
        double f_trip = parameters[1];
        double tay_trip = parameters[2];
        double tay_diff = parameters[3];
        double a = parameters[4];

        return count(inputValues, N_eff, f_trip, tay_trip, tay_diff, a);
    }

    private double[] count(double[] tays, double N_eff, double f_trip,
                                 double tay_trip, double tay_diff, double a) {
        double[] FFSResult = new double[tays.length];

        for (int i = 0; i < tays.length; i++) {
            FFSResult[i] = (countPoint(tays[i], N_eff, f_trip, tay_trip, tay_diff, a));
        }
        return FFSResult;
    };

    private Double countPoint( double tay, double N_eff, double f_trip,
                              double tay_trip, double tay_diff, double a) {
        double point = 1.0;
        double numerator;
        double denominator;

        numerator = 1 + (f_trip / (1 - f_trip))*Math.exp(-tay/tay_trip);
        denominator = N_eff * (1 + (tay / tay_diff)) * Math.sqrt(1 + tay/(a * a * tay_diff));
        point += numerator / denominator;
        return point;
    }
}

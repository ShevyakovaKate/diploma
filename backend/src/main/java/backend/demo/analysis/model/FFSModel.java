package backend.demo.analysis.model;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class FFSModel {
    public static final int ModelID = 2;

    public static double[] countModel(double[] tays, double N_eff, double f_trip,
                                   double tay_trip, double tay_diff, double a) {
        double[] FFSResult = new double[tays.length];

        for (int i = 0; i < tays.length; i++) {
            FFSResult[i] = (countPoint(tays[i], N_eff, f_trip, tay_trip, tay_diff, a));
        }
        return FFSResult;
    };

    private static Double countPoint( double tay, double N_eff, double f_trip,
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

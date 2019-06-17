package backend.demo.analysisFunctions.qualityAnalysisUtil;

public class WeightedAverageBalance {

    public static double[] getWeightedAverageBalance(double[] modelValues, double[] realValues, double[] sigma) {

        double[] weightedAverageBalances = new double[modelValues.length];
        for (int i = 0; i < modelValues.length; i++) {
            weightedAverageBalances[i] = (double) Math.round(((modelValues[i] - realValues[i]) / sigma[i]) * 1000000) / 1000000;
        }
        return weightedAverageBalances;
    }

    public static double[] getAutocorrelationalOfWeightedAverageBalances(double[] weightedAverageBalance, int parametersNumbers) {
        double variance = weightedAverageBalance.length - parametersNumbers + 1;
        int pointNumbers = weightedAverageBalance.length;
        double[] autocorrelationalArray = new double[weightedAverageBalance.length];


        autocorrelationalArray[0] = 1.0;
        for (int i = 1; i < pointNumbers; i++) {
            autocorrelationalArray[i] = countAutocorrelationalPoint(weightedAverageBalance, i, weightedAverageBalance.length);
        }

        return autocorrelationalArray;
    }

    private static double countAutocorrelationalPoint(double[] weightedAverageBalances, int k, int N) {
        double numerator = 0;
        double denominator = 0;

        for (int i = 0; i < N - k +1; i++) {
            numerator += weightedAverageBalances[i] * weightedAverageBalances[i + k - 1];
        }

        for (int i = 0; i < N; i++) {
            denominator += weightedAverageBalances[i] * weightedAverageBalances[i];
        }

        numerator = numerator / ( N - k + 1 );
        denominator = denominator / N;

        return (double) Math.round((numerator / denominator) * 1000000) / 1000000;
    }


}

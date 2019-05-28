package backend.demo.model;

import java.util.List;

public class AnalysisData {
    private List<Parameter> parameters;
    private double hi2;
    private double[] autocorrelationalFunction;
    private double[] weightedAverageBalances;

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public double getHi2() {
        return hi2;
    }

    public void setHi2(double hi2) {
        this.hi2 = hi2;
    }

    public double[] getAutocorrelationalFunction() {
        return autocorrelationalFunction;
    }

    public void setAutocorrelationalFunction(double[] autocorrelationalFunction) {
        this.autocorrelationalFunction = autocorrelationalFunction;
    }

    public double[] getWeightedAverageBalances() {
        return weightedAverageBalances;
    }

    public void setWeightedAverageBalances(double[] weightedAverageBalances) {
        this.weightedAverageBalances = weightedAverageBalances;
    }
}

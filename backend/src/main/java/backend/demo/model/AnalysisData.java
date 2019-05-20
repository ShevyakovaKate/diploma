package backend.demo.model;

import java.util.List;

public class AnalysisData {
    private List<Parameter> parameters;
    private double hi2;

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
}

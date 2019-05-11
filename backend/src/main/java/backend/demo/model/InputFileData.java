package backend.demo.model;

public class InputFileData {

    private double[] inputValues;
    private double[] outputValues;
    private double sigma;

    public double[] getInputValues() {
        return inputValues;
    }

    public void setInputValues(double[] inputValues) {
        this.inputValues = inputValues;
    }

    public double[] getOutputValues() {
        return outputValues;
    }

    public void setOutputValues(double[] outputValues) {
        this.outputValues = outputValues;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }
}

package backend.demo.service.api;

import javafx.util.Pair;

import java.util.List;

public interface AnalysisServiceApi {

    void startAnalysis(double[] inputValues, double[] parameters, double[] outputRealValues, Double sigma, int modelID);
}

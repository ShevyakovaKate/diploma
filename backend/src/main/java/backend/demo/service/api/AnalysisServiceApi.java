package backend.demo.service.api;

import org.springframework.stereotype.Service;

public interface AnalysisServiceApi {

    double[] startAnalysis(double[] inputValues, double[] parameters, double[] outputRealValues, Double sigma, int modelID, double[] parametersMin, double[] parametersMax);
}

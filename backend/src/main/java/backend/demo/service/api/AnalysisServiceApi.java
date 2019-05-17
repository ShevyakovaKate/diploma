package backend.demo.service.api;

import org.springframework.stereotype.Service;

public interface AnalysisServiceApi {

    double[] startAnalysis(double[] inputValues, double[] parameters, double[] outputRealValues, Double sigma, int modelID, double[] parametersMin, double[] parametersMax);

    /*
     * startApproxParameters Phase model - a1, a2, t1, t2;
     * startApproxParameters FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
     * */
    double[] countModel(double[] parameters, double[] inputValues, int modelID);
}

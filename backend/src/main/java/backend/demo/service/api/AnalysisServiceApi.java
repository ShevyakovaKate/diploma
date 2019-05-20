package backend.demo.service.api;

import backend.demo.model.AnalysisData;
import backend.demo.model.Parameter;

import java.util.List;

public interface AnalysisServiceApi {

    AnalysisData startAnalysis(double[] inputValues, List<Parameter> parameters,
                               double[] outputRealValues, Double sigma, int modelID);

    /*
     * startApproxParameters Phase model - a1, a2, t1, t2;
     * startApproxParameters FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
     * */
    double[] countModel(double[] parameters, double[] inputValues, int modelID);
}

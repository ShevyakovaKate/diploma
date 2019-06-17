package backend.demo.service.api;

import backend.demo.model.AnalysisData;
import backend.demo.model.Parameter;

import java.util.List;

public interface AnalysisServiceApi {

    /**
     * Start optimization process
     * @param inputValues input values from uploaded file
     * @param parameters for phase model - a1, a2, t1, t2;
     *      *                    for FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
     * @param outputRealValues  model from uploaded file
     * @param sigma value describing noise
     * @param modelID  id of needed model
     * @return matched parameters
     */
    AnalysisData startAnalysis(double[] inputValues, List<Parameter> parameters,
                               double[] outputRealValues, double[] sigma, int modelID);

}

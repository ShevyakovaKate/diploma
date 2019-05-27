package backend.demo.analysisFunctions.model;

public interface Model {
    int ModelID = 0;
    int ParametersNumbers = 0;

    /**
     * Count theoretical model
     * @param parameters  for phase model - a1, a2, t1, t2;
     *                    for FFS model - N_eff, f_trip, tay_trip, tay_diff, a;
     * @param inputValues input values from uploaded file
     * @return theoretical model as array of double
     */
    double[] countModel(double[] parameters, double[] inputValues);
}

package backend.demo.service.api;

import javafx.util.Pair;

import java.util.List;

public interface AnalysisServiceApi {

    Double startPhaseFrequencyAnalysis(List<Double> frequencies,  List<Pair<Double, Double>> components, List<Double> realValues, Double sigma);
    Double startFFSAnalysis(List<Double> tays, Double N_eff, Double f_trip, Double tay_trip, Double tay_diff, Double a, List<Double> realValues, Double sigma);
}

package backend.demo.analysisFunctions.qualityAnalysisUtil;

import org.springframework.stereotype.Service;

@Service
public class Hi2 {

    public static Double countHi2(double[] modelValues, double[] realValues, Double sigma, Integer parametersNumbers) {
        double variance = modelValues.length - parametersNumbers - 1;
        double temp = 0;
        for (int i = 0; i < modelValues.length; i++) {
            double realValue = realValues[i];
            double modelValue = modelValues[i];
            temp += Math.pow((modelValue - realValue) / sigma, 2);
        }
        return temp / variance;
    }
}

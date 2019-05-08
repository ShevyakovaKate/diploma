package backend.demo.analysis.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Hi2 {
    private static final Integer PhaseParametersNumbers = 4;
    private static final Integer PFFSParametersNumbers = 5;

    public static Double countHi2(double[] modelValues,double[] realValues, Double sigma, Integer parametersNumbers) {
        double variance = modelValues.length - parametersNumbers - 1; //todo в енам число оцениваемых параметров
        double temp = 0;
        for (int i = 0; i < modelValues.length; i++) {
            double realValue = realValues[i];
            double modelValue = modelValues[i];
            temp += Math.pow((modelValue - realValue) / sigma, 2);
        }
        return temp / variance;
    }
}

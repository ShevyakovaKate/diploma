package backend.demo.service.impl;

import backend.demo.model.InputFileData;
import backend.demo.service.api.ParseInputFileServiceApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParseInputFileServiceImpl implements ParseInputFileServiceApi {
    private static final int inputValueColumn = 0;
    private static final int outputValueColumn = 1;
    private static final int sigmaValueColumn = 2;

    @Override
    public InputFileData parseInputData(MultipartFile file) {
        String fileContent;

        try {
            fileContent = IOUtils.toString(file.getInputStream(), Charset.defaultCharset().toString());
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] lines = fileContent.split("\\r?\\n");

        double[] inputValues = new double[lines.length];
        double[] outputValues = new double[lines.length];
        double[] sigma = new double[lines.length];

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String[] values = line.split("\\s+");
            inputValues[i] = Double.valueOf(values[inputValueColumn]);
            outputValues[i] = Double.valueOf(values[outputValueColumn]);
            sigma[i] = Double.valueOf(values[sigmaValueColumn]);
        }

        InputFileData inputFileData = new InputFileData();
        inputFileData.setInputValues(inputValues);
        inputFileData.setOutputValues(outputValues);
        inputFileData.setSigma(sigma);

        return inputFileData;
    }

    @Override
    public double[] parseInitParamsStringToArray(String initParams) {
        String result = initParams.replaceAll("[\\{\\}]", "");
        String[] stringArray = result.split(",");
        double[] returnArray = new double[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            returnArray[i] = Double.valueOf(stringArray[i]);
        }
        return returnArray;
    }

}

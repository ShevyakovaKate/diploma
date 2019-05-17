package backend.demo.service.api;

import backend.demo.model.InputFileData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ParseInputFileServiceApi {
    /**
     * @param file
     * @return Input file data as arrays of input data: input values, output values, sigma
     */
    InputFileData parseInputData(MultipartFile file);

    double[] parseInitParamsStringToArray(String initParams);
}

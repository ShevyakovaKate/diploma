package backend.demo.service.api;

import backend.demo.model.InputFileData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ParseInputFileServiceApi {

    /** Parse file with experimental data
     * @param file uploaded file with experimental data
     * @return Input file data as arrays of input data: input values, output values, sigma
     */
    InputFileData parseInputData(MultipartFile file);

    /**
     * Parse init parameters as string to double array
     * @param initParams parameters as string
     * @return array of double
     */
    double[] parseInitParamsStringToArray(String initParams);
}

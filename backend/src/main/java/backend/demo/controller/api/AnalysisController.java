package backend.demo.controller.api;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping()
public interface AnalysisController {

    @RequestMapping(method = GET , value = "/frequency/model")
    double[] startPhaseFrequencyAnalysis();

    @RequestMapping(method = GET , value = "/ffs/model")
    double[] startFFSAnalysis();

    @RequestMapping(method = POST, value = "/file")
    String _fileUpload(MultipartFile file);

    @RequestMapping(value = "/model/{id}", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    double[] getModel(String initParams,
            MultipartFile file, @PathVariable Integer id  );


}
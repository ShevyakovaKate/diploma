package backend.demo.controller.api;


import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RequestMapping()
public interface AnalysisController {

    @RequestMapping(method = GET , value = "/frequency/model")
    void startPhaseFrequencyAnalysis();
    @RequestMapping(method = GET , value = "/ffs/model")
    void startFFSAnalysis();
}
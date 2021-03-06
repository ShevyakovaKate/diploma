package backend.demo.controller.impl;

import backend.demo.analysisFunctions.model.Model;
import backend.demo.analysisFunctions.model.impl.FFSModel;
import backend.demo.analysisFunctions.model.impl.PhaseFrequencyModel;
import backend.demo.analysisFunctions.qualityAnalysisUtil.WeightedAverageBalance;
import backend.demo.controller.api.AnalysisController;
import backend.demo.model.AnalysisData;
import backend.demo.model.InputFileData;
import backend.demo.model.Parameter;
import backend.demo.service.api.AnalysisServiceApi;
import backend.demo.service.api.ParseInputFileServiceApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AnalysisControllerImpl implements AnalysisController {
    private AnalysisServiceApi analysisService;
    private ParseInputFileServiceApi parseInputFileService;
    private Model model;

    @Autowired
    public AnalysisControllerImpl(AnalysisServiceApi analysisService, ParseInputFileServiceApi parseInputFileService) {
        this.analysisService = analysisService;
        this.parseInputFileService = parseInputFileService;
    }

    /*@Override
    public double[] startPhaseFrequencyAnalysis() {

        double[] frequencies = {125663706.143592, 188495559.215388, 251327412.287183,314159265.358979, 376991118.430775,
                439822971.502571, 502654824.574367,565486677.646163,628318530.717959,691150383.789755,
                753982236.861550,816814089.933346,879645943.005142,942477796.076938,1005309649.14873};
        double[] realValues = {0.262208317399775,
                0.372639718622166,0.465926207507242,0.543815340888859,0.609226474949149,0.665029767699937,0.713561156116506,
                0.756555827782014, 0.795245603018200,
                0.830489486643397,0.862887399011352,0.892865325178978,0.920734449335539,0.946730505939556 ,0.971039299753354};
        *//*double[] startParameters = {0.33, 0.67, 1, 3}; *//*
        *//* startApproxParameters Phase model - a1, t1, a2, t2; *//*
        *//* startApproxParameters FFS model - N_eff, f_trip, tay_trip, tay_diff, a; *//*
        double[] startParameters = {0.5, 2E-9, 0.5, 5E-9};

        double[] parametersMin = {1E-10, 0, 1E-10, 0};
        double[] parametersMax = {1E10, 10E5, 1E10, 10E5};

        double sigma = 1.0;

       *//* Double hi2Value = analysisService.startPhaseFrequencyAnalysis(frequencies, components, realValues, sigma);*//*
        return analysisService.startAnalysis(frequencies, startParameters, realValues, sigma, PhaseFrequencyModel.ModelID, parametersMin, parametersMax);

    }

    @Override
    public double[] startFFSAnalysis() {

        double[] tays = {0.0, 0.2, 0.4, 0.6, 0.8, 1.0, 1.2, 1.4, 1.6, 1.8, 2.0, 2.2, 2.4,  2.6, 2.8, 3.0, 3.4, 3.8, 4.2,
                4.6, 5.0, 5.4, 5.8, 6.2, 6.6, 7.0, 7.4, 7.8, 8.2, 8.6, 9.0, 9.4, 9.8, 10.2, 10.6, 11.0, 11.4, 11.8, 12.2,
                12.6, 13.0, 13.4, 13.8, 14.2, 14.6, 15.0, 15.4, 15.8, 16.2, 16.6, 17.0, 17.4, 17.8, 18.2, 18.6, 19.0, 19.4,
                19.8, 20.2, 20.6, 20.0, 21.4, 21.8, 22.2, 22.6, 22.0, 23.4, 23.8, 24.2, 24.6, 24.0, 25.4, 25.8,26.2, 26.6,
                26.0, 27.4, 27.8, 28.2, 28.6, 28.0, 29.4, 29.8,30.2, 30.6, 30.0, 31.4, 31.8, 32.2, 32.6, 32.0, 33.4, 33.8,
                34.2, 34.6, 34.0, 35.4, 35.8, 36.2, 36.6, 36.0, 37.4, 37.8, 38.2, 38.6, 38.0, 39.4, 39.8, 40.2, 40.6, 40.0,
                41.4, 41.8, 42.2, 42.6, 42.0, 43.4, 43.8, 44.2, 44.6, 44.0, 45.4, 45.8, 46.2, 46.6, 46.0, 47.4, 47.8, 48.2,
                48.6, 48.0, 49.4, 49.8, 50.2, 50.6, 50.0, 51.4, 51.8, 52.2, 52.6, 52.0, 53.4, 53.8, 54.2, 54.6, 54.0, 55.4,
                55.8, 56.2, 56.6, 56.0, 57.4, 57.8, 58.2, 58.6, 58.0, 59.4, 59.8, 60.2, 60.6, 60.0, 61.4, 61.8, 62.2, 62.6,
                62.0, 63.4, 63.8, 64.2, 64.6, 64.0, 65.4, 65.8, 66.2, 66.6, 66.0, 67.4, 67.8, 68.2, 68.6, 68.0, 69.4, 69.8,
                70.2, 70.6, 70.0, 71.4, 71.8, 72.2, 72.6, 72.0, 73.4};

        double[] realValues = {51.0, 40.94092739671259, 34.199403665071046, 29.672419442222424, 26.62378414515896, 24.56212703145273,
                23.159476240547846, 22.19691826268793, 21.528330324131634, 21.05617447239372, 20.715341669545563, 20.462368572649307, 20.268240181136424,
                20.113585822616812, 19.9854725668013, 19.87526485676017, 19.687413531787282, 19.52326875986456, 19.370876122363008, 19.22488156756153,
                19.0828734706082, 18.943752399007654, 18.807004508540135, 18.672377659724095, 18.53973702582556, 18.409000705036433, 18.280110995210283,
                18.1530215668778, 18.02769172452732, 17.90408382880229, 17.78216212875534, 17.661892223541372, 17.543240805475985, 17.42617552922904,
                17.31066493790588, 17.196678415107776, 17.084186149160754, 16.97315910332719, 16.86356898921669, 16.755388242131172, 16.648589997757423,
                16.543148069924158, 16.439036929277115, 16.336231682787705, 16.23470805403904, 16.13444236424719, 16.03541151398211, 15.93759296555668,
                15.840964726054931, 15.7455053309721, 15.651193828440826, 15.558009764019147, 15.465933166016958, 15.374944531338855, 15.28502481182227,
                15.196155401050854, 15.108318121623808, 15.021495212863025, 14.935669318940427, 14.850823477408973, 14.978458708288048, 14.684006002520858,
                14.60200231329107, 14.520914544348441, 14.44072754116657, 14.561344893835818, 14.282996865923565, 14.20542450989229, 14.128695534449662,
                14.052796358434698, 14.166955455303333, 13.903434521230285, 13.82994611608923, 13.75723600782081, 13.68529198966146, 13.793494543792718,
                13.543654658501712, 13.473938174396183, 13.404941425678082, 13.336653410457494, 13.43935052602213, 13.202160681648127, 13.135935056305803,
                13.070376330226905, 13.005474561355204, 13.103072956898176, 12.877603104302509, 12.814614494965813, 12.752244991292336, 12.690485586475388,
                12.783352921966415, 12.568761910326444, 12.50878047714843, 12.449374811159425, 12.390536733385844, 12.479006188093301, 12.274531392943205,
                12.217348527960953, 12.160702039654135, 12.10458448393585, 12.188958705225303, 11.993907075463595, 11.939333006766779, 11.88525943275973,
                11.83167956354191, 11.912234085189995, 11.725974387627902, 11.673836100764886, 11.622165553111, 11.570956538656114, 11.647942749998997,
                11.469898828650885, 11.420038257325388, 11.370615463057115, 11.321624762302891, 11.395272495597908, 11.224917394123406, 11.177189840341239,
                11.129872603329384, 11.0829604681812, 11.153480260251772, 10.99033108129876, 10.944603831720862, 10.899261684772462, 10.854299846873241,
                10.921884921889882, 10.765498317759658, 10.721649428410352, 10.678162448710259, 10.6350329647123, 10.699860977415478, 10.549829183411997,
                10.50774640931966, 10.466004174580183, 10.424598408040882, 10.486832980505136, 10.342780316272943, 10.302360166271889, 10.262260832470071,
                10.22247855387782, 10.282270633791223, 10.143850409469833, 10.104997309415584, 10.066446793910409,
                10.028195383153657, 10.08568444733719, 9.952576220920754, 9.915201771083943, 9.87811302732874, 9.841306765257444, 9.896621888612238,
                9.768529028866627, 9.732551343367456, 9.696843715455646, 9.661403153136511, 9.714663960252201, 9.591311476198722, 9.556654594099363,
                9.522253241076712, 9.488104636980278, 9.539422151161128, 9.420554754720275, 9.387148113867609,
                9.353983495169109, 9.321058311903851, 9.370535714285714, 9.255916087374882, 9.223694053329305, 9.191701467901016, 9.15993592127176,
                9.207669230944806, 9.097076472293097, 9.065977915730583, 9.03509708841379, 9.004431742566013,
                9.050510427129616, 8.94373865743515, 8.913706574301694, 8.883881283920172, 8.854260687167463, 8.89876821187966, 8.795625318791625,
                8.766606488247984, 8.737784232574091, 8.709156589218018, 8.752170911825528, 8.652477418667395, 8.624422094509562, 8.596553787912313,
                8.568870661971928, 8.610464679387372, 8.514052723617922};


        Double sigma = 1.0;

   *//*     *//**//*
         * this is real values
         *//**//*
        Double N_eff = 0.05;
        Double f_trip = 0.6;
        Double tay_trip = 0.5;
        Double tay_diff = 50.0;
        Double a= 3.0;
*//*
        *//*
         * this is fake values
         *//*
       double[] inputParameters = {0.07, 0.5, 0.7, 20.0, 2.0};

       double[] parametersMin = {0, 0, 0, 0, 0};
       double[] parametersMax = {10E10, 10E10, 10E10, 10E10, 10E10};

       return analysisService.startAnalysis(tays, inputParameters, realValues, sigma, FFSModel.ModelID, parametersMin, parametersMax);
    }*/

   /* @Override
    public String _fileUpload(MultipartFile file) {
        InputFileData inputFileData = parseInputFileService.parseInputData(file);
        return inputFileData.getInputValues().toString();
    }
*/
    /**
     *
     * @param initParams
     * @param file
     * @param id
     * @return 3 arrays = input values, theoretical model, real model
     */
    @Override
    public List<double[]> getModel(String initParams, MultipartFile file, Integer id) {
        switch (id) {
            case PhaseFrequencyModel.ModelID: {
                this.model = new PhaseFrequencyModel();
                break;
            }
            case FFSModel.ModelID: {
                this.model = new FFSModel();
                break;
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Parameter> parameters = new ArrayList<>();
        try {
            parameters = objectMapper.readValue(initParams, new TypeReference<List<Parameter>>(){});
        } catch (IOException e) {
            e.printStackTrace();;
        }

        double[] initParamsArray  = new double[parameters.size()];

        for (int i = 0; i < parameters.size(); i++) {
            initParamsArray[i] = parameters.get(i).get_value();
        }

        InputFileData inputFileData = parseInputFileService.parseInputData(file);
        List<double[]> returnModel = new ArrayList<>();
        returnModel.add(inputFileData.getInputValues());
        returnModel.add(this.model.countModel(initParamsArray, inputFileData.getInputValues()));
        returnModel.add(inputFileData.getOutputValues());
        return returnModel;
    }

    @Override
    public AnalysisData startAnalysis(String initParams, MultipartFile file, Integer id) {
        InputFileData inputFileData = parseInputFileService.parseInputData(file);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Parameter> parameters = new ArrayList<>();
        try {
            parameters = objectMapper.readValue(initParams, new TypeReference<List<Parameter>>(){});
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return analysisService.startAnalysis(inputFileData.getInputValues(),
                parameters,
                inputFileData.getOutputValues(),
                inputFileData.getSigma(),
                id);
    }
}

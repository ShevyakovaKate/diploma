import { Injectable } from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {init} from "protractor/built/launcher";
import {Parameter} from "../../../models/parameter";

@Injectable({
  providedIn: 'root'
})
export class AnalysisService {

  constructor(private http: HttpClient) {  }

  public startPhaseFrequencyAnalysis() {

    return this.http.get('/api/frequency/model');

  }

  public startFFSAnalysis() {
    return this.http.get('/api/ffs/model');
  }

  public getModelWithParamenter(file: File, initParams: Parameter[], modelId: number) {
    let formData:FormData = new FormData();
    formData.append('file', file, file.name);

     /*formData.append("initParams", JSON.stringify(
     [{"_name":"a1","_value":0.5,"_minValue":1E-10,"_maxValue":1E10},
       {"_name":"a2","_value":0.5,"_minValue":1E-10,"_maxValue":10E10},
       {"_name":"τ1","_value":2E-9,"_minValue":0,"_maxValue":10E5},
       {"_name":"τ2","_value":5E-9,"_minValue":0,"_maxValue":10E5}]));*/
    formData.append("initParams", JSON.stringify([{"_name":"N_eff","_value":0.038374845595426095,"_minValue":1E-3,"_maxValue":100},
      {"_name":"f_trip","_value":0.5,"_minValue":0,"_maxValue":0.09999},
      {"_name":"tay_trip","_value":0.2898983159337773,"_minValue":10E-6,"_maxValue":1},
      {"_name":"tay_diff","_value":20.0,"_minValue":10E-3,"_maxValue":10},
      {"_name":"a","_value":10.651805478190674,"_minValue":1.001, "_maxValue":20}]));
    return this.http.post<any>("/api/model/" + modelId, formData);

  }

  public startAnalysis(file: File, initParams: Parameter[], modelId: number) {
    let formData:FormData = new FormData();

    formData.append('file', file, file.name);

    formData.append("initParams", JSON.stringify(
      [{"_name":"a1","_value":0.5,"_minValue":1E-10,"_maxValue":1E10},
        {"_name":"a2","_value":0.5,"_minValue":1E-10,"_maxValue":10E10},
        {"_name":"τ1","_value":2E-9,"_minValue":0,"_maxValue":10E5},
        {"_name":"τ2","_value":5E-9,"_minValue":0,"_maxValue":10E5}]));

   /* formData.append("initParams", JSON.stringify(
      [
        {
          "_name": "a1",
          "_value": 1.1356750940810787,
          "_minValue": 1e-10,
          "_maxValue": 10000000000
        },
        {
          "_name": "a2",
          "_value": 0.5593623597712739,
          "_minValue": 1e-10,
          "_maxValue": 100000000000
        },
        {
          "_name": "τ1",
          "_value": 1.000000000000004e-9,
          "_minValue": 0,
          "_maxValue": 1000000
        },
        {
          "_name": "τ2",
          "_value": 3.000000000000005e-9,
          "_minValue": 0,
          "_maxValue": 1000000
        }
      ]));*/

  /*  formData.append("initParams", JSON.stringify([{"_name":"N_eff","_value":0.07,"_minValue":1E-3,"_maxValue":100},
      {"_name":"f_trip","_value":0.5,"_minValue":0,"_maxValue":0.09999},
      {"_name":"tay_trip","_value":0.7,"_minValue":10E-6,"_maxValue":1},
      {"_name":"tay_diff","_value":20.0,"_minValue":10E-3,"_maxValue":10},
      {"_name":"a","_value":2.0,"_minValue":1.001, "_maxValue":20}]));*/
    return this.http.post<any>("/api/analysis/" + modelId, formData);
  }




}

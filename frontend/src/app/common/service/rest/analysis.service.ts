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

  public getModelWithParamenter(file: File, initParams: string, modelId: number) {
    let formData:FormData = new FormData();
    formData.append('file', file, file.name);

    formData.append("initParams", initParams);

    return this.http.post<any>("/api/model/" + modelId, formData);

  }

  public startAnalysis(file: File, initParams: string, modelId: number) {
    let formData:FormData = new FormData();
    formData.append('file', file, file.name);
    formData.append("initParams", initParams);
    new Parameter();
    return this.http.post<any>("/api/analysis/" + modelId, formData);
  }




}

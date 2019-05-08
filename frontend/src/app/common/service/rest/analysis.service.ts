import { Injectable } from '@angular/core';
import {HttpClient, HttpClientModule} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AnalysisService {

  constructor(private http: HttpClient) {  }

  public startPhaseFrequencyAnalysis() {
    return this.http.get('/api/frequency/model');

  } public startFFSAnalysis() {
    return this.http.get('/api/ffs/model');
  }


}

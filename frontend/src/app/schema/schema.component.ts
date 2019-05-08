import { Component, OnInit } from '@angular/core';
import {AnalysisService} from "../common/service/rest/analysis.service";

@Component({
  selector: 'app-schema',
  templateUrl: './schema.component.html',
  styleUrls: ['./schema.component.css']
})
export class SchemaComponent implements OnInit {
  public returnValue;

  constructor(private analysisService: AnalysisService) { }

  ngOnInit() {
    this.initCall();
  }

  initCall() {

    this.analysisService.startFFSAnalysis().subscribe(res =>
      this.returnValue = res,
      err =>
        this.returnValue = 'error'
    );
  }

}

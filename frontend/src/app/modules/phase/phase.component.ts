import { Component, OnInit } from '@angular/core';
import {AnalysisService} from "../../common/service/rest/analysis.service";

@Component({
  selector: 'app-phase',
  templateUrl: './phase.component.html',
  styleUrls: ['./phase.component.scss']
})
export class PhaseComponent implements OnInit {

  constructor(private analysisService: AnalysisService) { }

  ngOnInit() {
    this.analysisService.startPhaseFrequencyAnalysis().subscribe(res => console.log(res));
  }

}

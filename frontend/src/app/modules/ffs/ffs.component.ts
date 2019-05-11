import { Component, OnInit } from '@angular/core';
import {AnalysisService} from "../../common/service/rest/analysis.service";

@Component({
  selector: 'app-ffs',
  templateUrl: './ffs.component.html',
  styleUrls: ['./ffs.component.scss']
})
export class FfsComponent implements OnInit {

  constructor(private analysisService: AnalysisService) { }

  ngOnInit() {
    this.analysisService.startFFSAnalysis().subscribe(res => console.log(res));
  }

}

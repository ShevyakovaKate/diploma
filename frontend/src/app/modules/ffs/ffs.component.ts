import { Component, OnInit } from '@angular/core';
import {AnalysisService} from "../../common/service/rest/analysis.service";
import {RouterModule} from "@angular/router";

@Component({
  selector: 'app-ffs',
  templateUrl: './ffs.component.html',
  styleUrls: ['./ffs.component.scss']
})
export class FfsComponent implements OnInit {

  constructor(private analysisService: AnalysisService, private router: RouterModule) { }

  ngOnInit() {
  }

}

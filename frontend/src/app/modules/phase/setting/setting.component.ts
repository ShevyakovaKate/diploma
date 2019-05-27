import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component, Input,
  OnInit, ViewChild,
} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {TableComponent} from "./table/table.component";
import {AnalysisService} from "../../../common/service/rest/analysis.service";
import {Router, RouterModule} from "@angular/router";
import {init} from "protractor/built/launcher";
import {Parameter} from "../../../models/parameter";
import {AnalysisData} from "../../../models/analysis-data";

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.scss']
})
export class SettingComponent implements OnInit{
  components = [];
  components$ = new BehaviorSubject(this.components);
  file: File = null;
  @ViewChild(TableComponent) tableComponent;

  constructor(private cd: ChangeDetectorRef,
              private analysisService: AnalysisService,
              private router: Router) {}

  ngOnInit() {

  }

  changeComponentNumber(value) {
    this.components = [];
    for (let i = 0; i < value; i++) {
      this.components.push({ i });
      this.components$.next(this.components);
    }
  }

  fileChanged(file) {
    this.file = file;
    localStorage.setItem('file', file);
  }

  modelWithInitialParameters() {
    let initValuesA = this.tableComponent.parametersA;
    let initValuesT = this.tableComponent.parametersT;
    console.log(initValuesA);
    console.log(initValuesT);
    let parameters: Parameter[] = [];
    initValuesA.forEach(item => {
      parameters.push(item);
      }
    );
    initValuesT.forEach(item => {
        parameters.push(item);
      }
    );

    this.analysisService.getModelWithParamenter(this.file, parameters, 1).subscribe(
      (res: Array<number>) => {
        console.log(res);
        localStorage.setItem('allFrequencies', JSON.stringify(res[0]));
        localStorage.setItem('allTheoreticalModelValues', JSON.stringify(res[1]));
        localStorage.setItem('allRealModelValues', JSON.stringify(res[2]));
        this.router.navigate(['/phase/results']);
      },
      error => {
        console.log(error);
      }
    );
  }

  startAnalysis() {
    let initValuesA = this.tableComponent.parametersA;
    let initValuesT = this.tableComponent.parametersT;

    let parameters: Parameter[] = [];

    initValuesA.forEach( item => {
      parameters.push(item);
    });

    initValuesT.forEach( item => {
      parameters.push(item);
    });

    this.analysisService.startAnalysis(this.file, parameters, 1).subscribe(
      (res: AnalysisData) => {
        console.log(res);
        localStorage.removeItem('parameters');
        localStorage.removeItem('hi2');
        localStorage.setItem('parameters',JSON.stringify(res.parameters));
        localStorage.setItem('hi2', JSON.stringify(res.hi2));
        this.router.navigate(['/phase/results']);
      },
      error => {
        console.log(error);
      }
    );
  }

}

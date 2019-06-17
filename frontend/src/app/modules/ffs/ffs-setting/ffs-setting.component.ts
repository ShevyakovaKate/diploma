import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {TableComponent} from "../../phase/setting/table/table.component";
import {AnalysisService} from "../../../common/service/rest/analysis.service";
import {Router} from "@angular/router";
import {Parameter} from "../../../models/parameter";
import {AnalysisData} from "../../../models/analysis-data";
import {FfsTableComponent} from "./ffs-table/ffs-table.component";

@Component({
  selector: 'app-ffs-setting',
  templateUrl: './ffs-setting.component.html',
  styleUrls: ['./ffs-setting.component.scss']
})
export class FfsSettingComponent implements OnInit {
  components = [];
  components$ = new BehaviorSubject(this.components);
  file: File = null;
  @ViewChild(FfsTableComponent) tableComponent;

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
    let parameters = this.constructParameters();

    this.analysisService.getModelWithParamenter(this.file, parameters, 2).subscribe(
      (res: Array<number>) => {
        console.log(res);
        localStorage.setItem('allFrequenciesffs', JSON.stringify(res[0]));
        localStorage.setItem('allTheoreticalModelValuesffs', JSON.stringify(res[1]));
        localStorage.setItem('allRealModelValuesffs', JSON.stringify(res[2]));
        this.router.navigate(['/ffs/results']);
      },
      error => {
        console.log(error);
      }
    );
  }

  startAnalysis() {
    let parameters = this.constructParameters();

    this.analysisService.startAnalysis(this.file, parameters, 2).subscribe(
      (res: AnalysisData) => {
        console.log(res);
        localStorage.setItem('parametersffs',JSON.stringify(res.parameters));
        localStorage.setItem('hi2ffs', JSON.stringify(res.hi2));
        localStorage.setItem('autocorrelationalFunctionffs',  JSON.stringify(res.autocorrelationalFunction));
        localStorage.setItem('weightedAverageBalancesffs',  JSON.stringify(res.weightedAverageBalances));
        this.router.navigate(['/ffs/results']);
      },
      error => {
        console.log(error);
      }
    );
  }

  private constructParameters() {
    let initValuesA = this.tableComponent.parametersA;
    let initValuesTaydiff = this.tableComponent.parametersTaydiff;
    let initValuesTaytrip = this.tableComponent.parametersTaytrip;
    let initValuesFtrip = this.tableComponent.parametersFtrip;
    let initValuesNeff = this.tableComponent.parametersNeff;

    let parameters: Parameter[] = [];

    initValuesA.forEach( item => {
      parameters.push(item);
    });

    initValuesTaydiff.forEach( item => {
      parameters.push(item);
    });

    initValuesTaytrip.forEach( item => {
      parameters.push(item);
    });

    initValuesNeff.forEach( item => {
      parameters.push(item);
    });

    initValuesFtrip.forEach( item => {
      parameters.push(item);
    });

    return parameters;
  }

}

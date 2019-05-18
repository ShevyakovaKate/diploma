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
    console.log(file);
    this.file = file;

  }

  modelWithInitialParameters() {
    let initValues = this.tableComponent.initValues;
    this.analysisService.getModelWithParamenter(this.file, initValues.toString(), 1).subscribe(
      res => {
        this.router.navigate(["/phase/results"]);
      },
      error => {
        console.log(error);
      }
    );
  }

}

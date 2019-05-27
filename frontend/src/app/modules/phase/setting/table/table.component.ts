import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component, DoCheck,
  Input,
  OnInit, Output,
  SimpleChanges,
  ViewChildren
} from '@angular/core';
import {OnChanges} from "@angular/core/src/metadata/lifecycle_hooks";
import {BehaviorSubject, Observable} from "rxjs";
import {forEach} from "@angular/router/src/utils/collection";
import {Parameter} from "../../../../models/parameter";

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent implements OnInit, DoCheck {
  @Input() components$: Observable<number[]>;
  a: number;

  parametersA: Parameter[] = [];
  parametersT: Parameter[] = [];

  ngDoCheck(): void {
    this.initParameters();
    console.log("do check");
  }

  ngOnInit() {
  }


  initParameters() {
    this.parametersA = [];
    this.parametersT = [];
    var index = 1;
     this.components$.subscribe( items => {
       items.forEach(item => {
         let parameterA = new Parameter();
         parameterA._name = 'a' + index;
         parameterA._value = 0.5;
         parameterA._minValue = 0;
         parameterA._maxValue = 100;
         this.parametersA.push(parameterA);
         ////////////
         let parameterT= new Parameter();
         parameterT._name = 'τ' + index;
         parameterT._value = 2E-9;
         parameterT._minValue = 0;
         parameterT._maxValue = 100;
         this.parametersT.push(parameterT);
         ///////////
         index++;
       })
     });
  }

}

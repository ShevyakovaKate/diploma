import {Component, Input, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {Parameter} from "../../../../models/parameter";

@Component({
  selector: 'app-ffs-table',
  templateUrl: './ffs-table.component.html',
  styleUrls: ['./ffs-table.component.scss']
})
export class FfsTableComponent implements OnInit {

  @Input() components$: Observable<number[]>;
  a: number;

  parametersNeff: Parameter[] = [];
  parametersFtrip: Parameter[] = [];
  parametersTaytrip: Parameter[] = [];
  parametersTaydiff: Parameter[] = [];
  parametersA: Parameter[] = [];

  ngDoCheck(): void {
    this.initParameters();
    console.log("do check");
  }

  ngOnInit() {
  }


  initParameters() {
    this.parametersA = [];
    this.parametersNeff = [];
    this.parametersFtrip = [];
    this.parametersTaydiff = [];
    this.parametersTaytrip = [];
    var index = 1;
    this.components$.subscribe( items => {
      items.forEach(item => {
        let parameterNeff = new Parameter();
        parameterNeff._name = 'N_eff' + index;
        parameterNeff._value = 0.07;
        parameterNeff._minValue = 10E-3;
        parameterNeff._maxValue = 100;
        this.parametersNeff.push(parameterNeff);

        let parameterA= new Parameter();
        parameterA._name = 'a' + index;
        parameterA._value = 2.0;
        parameterA._minValue = 1.001;
        parameterA._maxValue = 20;
        this.parametersA.push(parameterA);

        let parameterFtrip= new Parameter();
        parameterFtrip._name = 'Ftrip' + index;
        parameterFtrip._value = 0.5;
        parameterFtrip._minValue = 0;
        parameterFtrip._maxValue = 0.09999;
        this.parametersFtrip.push(parameterFtrip);

        let Taytrip= new Parameter();
        Taytrip._name = 'Taytrip' + index;
        Taytrip._value = 0.7;
        Taytrip._minValue = 10E-6;
        Taytrip._maxValue = 1;
        this.parametersTaytrip.push(Taytrip);

        let Taydiff= new Parameter();
        Taydiff._name = 'Taydiff' + index;
        Taydiff._value = 20;
        Taydiff._minValue = 10E-3;
        Taydiff._maxValue = 10;
        this.parametersTaydiff.push(Taydiff);

        index++;
      })
    });
  }

}

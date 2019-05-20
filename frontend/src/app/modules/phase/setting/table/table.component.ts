import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
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
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TableComponent implements OnInit {
  @Input() components$: Observable<number[]>;
  _components: number[];
  a: number;

  parameters: Parameter[];

  initValues: string[];
  minValues: string[];
  maxValues: string[];

  constructor(private changeDetectorRef: ChangeDetectorRef) {
  }

  ngOnInit() {
    this.components$.subscribe(items => {
      this._components = items;
    });
    this.a = 1 / this._components.length;

    //todo взаимосвязь компонентов и параметров

    this.initValues = new Array<string>(this._components.length);
    this.minValues = new Array<string>(this._components.length);
    this.maxValues = new Array<string>(this._components.length);
    this.ngOnAfterInit();
  }

  ngOnAfterInit() {
    this._components.forEach(component => {
        this.initValues[component - 1] = this.a.toString();
        this.initValues[component] = "1e-9";

        this.minValues[component - 1] = "1";
        this.minValues[component] = "1e-9";

        this.minValues[component - 1] = "10";
        this.minValues[component] = "10e-9";
      }
    )
  }

  refresh() {
    this.changeDetectorRef.detectChanges();
  }


  changeInitNumber($event, paramName, componentNumber) {
    console.log($event.target.value, paramName, componentNumber);

    switch (paramName) {
      case 'a': {
        this.initValues[(componentNumber - 1)* this._components.length] = $event.target.value;
        break;
      }
      case 't': {
        this.initValues[(componentNumber * this._components.length) - 1] = $event.target.value  + "E-9";
        break;
      }
    }
    console.log(this.initValues);
  }

  changeMinNumber($event, paramName, componentNumber) {
    switch (paramName) {
      case 'a': {
        this.minValues[componentNumber - 1] = $event.target.value;
        break;
      }
      case 't': {
        this.minValues[(componentNumber - 1) + this._components.length] = $event.target.value  + "E-9";
        break;
      }
    }
    console.log(this.minValues);
  }

  changeMaxNumber($event, paramName, componentNumber) {
    switch (paramName) {
      case 'a': {
        this.maxValues[componentNumber - 1] = $event.target.value;
        break;
      }
      case 't': {
        this.maxValues[(componentNumber - 1) + this._components.length] = $event.target.value  + "E-9";
        break;
      }
    }
    console.log(this.maxValues);
  }

}

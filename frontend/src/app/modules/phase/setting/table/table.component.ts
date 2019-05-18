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

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TableComponent implements OnInit {
  @Input() components$: Observable<number[]>;
  _components: number[];

  @Output() initValues: string[];
  @Output() minValues: string[];
  @Output() maxValues: string[];

  constructor(private changeDetectorRef: ChangeDetectorRef) {}

  ngOnInit() {
    this.components$.subscribe(items => {
      this._components = items;
    });

    this.initValues = new Array<string>(this._components.length);

    this.minValues = new Array<string>(this._components.length);
    this.maxValues = new Array<string>(this._components.length);
  }

  refresh() {
    this.changeDetectorRef.detectChanges();
  }


  changeInitNumber($event, paramName, componentNumber) {
    console.log($event.target.value, paramName, componentNumber);

    switch (paramName) {
      case 'a': {
        this.initValues[componentNumber - 1] = $event.target.value;
        break;
      }
      case 't': {
        this.initValues[(componentNumber - 1) + this._components.length] = ($event.target.value * 0.000000001)
          .toExponential().toString();
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
        this.minValues[(componentNumber - 1) + this._components.length] = ($event.target.value * 0.000000001)
          .toExponential().toString();
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
        this.maxValues[(componentNumber - 1) + this._components.length] = ($event.target.value * 0.000000001)
      .toExponential().toString();
        break;
      }
    }
    console.log(this.maxValues);
  }




}

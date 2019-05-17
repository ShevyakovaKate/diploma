import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component, Input,
  OnInit,
} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.scss']
})
export class SettingComponent implements OnInit{
  components = [];
  components$ = new BehaviorSubject(this.components);

  constructor(private cd: ChangeDetectorRef) {}

  ngOnInit() {
  }

  changeComponentNumber(value) {
    this.components = [];
    for (let i = 0; i < value; i++) {
      this.components.push({ i });
      this.components$.next(this.components);
    }
}

}

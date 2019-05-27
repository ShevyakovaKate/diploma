import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FfsSettingComponent } from './ffs-setting.component';

describe('FfsSettingComponent', () => {
  let component: FfsSettingComponent;
  let fixture: ComponentFixture<FfsSettingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FfsSettingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FfsSettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

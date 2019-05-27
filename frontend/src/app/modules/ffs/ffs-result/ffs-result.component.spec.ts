import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FfsResultComponent } from './ffs-result.component';

describe('FfsResultComponent', () => {
  let component: FfsResultComponent;
  let fixture: ComponentFixture<FfsResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FfsResultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FfsResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

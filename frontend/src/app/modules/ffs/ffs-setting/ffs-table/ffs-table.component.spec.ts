import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FfsTableComponent } from './ffs-table.component';

describe('FfsTableComponent', () => {
  let component: FfsTableComponent;
  let fixture: ComponentFixture<FfsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FfsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FfsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FfsComponent } from './ffs.component';

describe('FfsComponent', () => {
  let component: FfsComponent;
  let fixture: ComponentFixture<FfsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FfsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FfsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

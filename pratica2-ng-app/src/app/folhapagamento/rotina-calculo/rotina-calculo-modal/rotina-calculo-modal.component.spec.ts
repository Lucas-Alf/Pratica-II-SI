import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RotinaCalculoModalComponent } from './rotina-calculo-modal.component';

describe('RotinaCalculoModalComponent', () => {
  let component: RotinaCalculoModalComponent;
  let fixture: ComponentFixture<RotinaCalculoModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RotinaCalculoModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RotinaCalculoModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

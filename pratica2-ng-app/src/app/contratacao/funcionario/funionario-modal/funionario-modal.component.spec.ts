import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FunionarioModalComponent } from './funionario-modal.component';

describe('FunionarioModalComponent', () => {
  let component: FunionarioModalComponent;
  let fixture: ComponentFixture<FunionarioModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FunionarioModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FunionarioModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

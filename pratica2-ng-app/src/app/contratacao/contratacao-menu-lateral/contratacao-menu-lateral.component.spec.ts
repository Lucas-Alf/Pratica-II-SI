import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ContratacaoMenuLateralComponent } from './contratacao-menu-lateral.component';

describe('ContratacaoMenuLateralComponent', () => {
  let component: ContratacaoMenuLateralComponent;
  let fixture: ComponentFixture<ContratacaoMenuLateralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ContratacaoMenuLateralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContratacaoMenuLateralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfiguracoesMenuLateralComponent } from './configuracoes-menu-lateral.component';

describe('ConfiguracoesMenuLateralComponent', () => {
  let component: ConfiguracoesMenuLateralComponent;
  let fixture: ComponentFixture<ConfiguracoesMenuLateralComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfiguracoesMenuLateralComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfiguracoesMenuLateralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

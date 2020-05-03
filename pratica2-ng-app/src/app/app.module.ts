import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConstantsService } from './common/services/constants.service';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSliderModule } from '@angular/material/slider';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatMenuModule } from '@angular/material/menu';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTabsModule } from '@angular/material/tabs';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule } from '@angular/material/paginator';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HomeComponent } from './geral/home/home.component';
import { IndexComponent } from './geral/index/index.component';
import { LoaderService } from './services/loader.service';
import { FolhapagamentoComponent } from './folhapagamento/home/folhapagamento.component';
import { FolhaMenuLateralComponent } from './folhapagamento/folha-menu-lateral/folha-menu-lateral.component';
import { NavbarNavigationComponent } from './componentes/navbar-navigation/navbar-navigation.component';
import { ConfiguracoesComponent } from './geral/configuracoes/configuracoes.component';
import { LoadingSpinnerComponent } from './componentes/loading-spinner/loading-spinner.component';
import { CabecalhoSistemaComponent } from './componentes/cabecalho-sistema/cabecalho-sistema.component';
import { RodapeSistemaComponent } from './componentes/rodape-sistema/rodape-sistema.component';
import { IncidenciaComponent } from './folhapagamento/incidencia/incidencia.component';
import { EventoComponent } from './folhapagamento/evento/evento.component';
import { ContratacaoComponent } from './contratacao/home/contratacao.component';
import { ContratacaoMenuLateralComponent } from './contratacao/contratacao-menu-lateral/contratacao-menu-lateral.component';
import { RecrutamentoMenuLateralComponent } from './recrutamento/recrutamento-menu-lateral/recrutamento-menu-lateral.component';
import { RecrutamentoComponent } from './recrutamento/home/recrutamento.component';
import { CargoComponent } from './recrutamento/cargo/cargo.component';
import { DepartamentoComponent } from './contratacao/departamento/departamento.component';
import { IncidenciaModalComponent } from './folhapagamento/incidencia/incidencia-modal/incidencia-modal.component';
import { EventoModalComponent } from './folhapagamento/evento/evento-modal/evento-modal.component';
import { RotinaCalculoComponent } from './folhapagamento/rotina-calculo/rotina-calculo.component';
import { RotinaCalculoModalComponent } from './folhapagamento/rotina-calculo/rotina-calculo-modal/rotina-calculo-modal.component';
import { PeriodoCalculoComponent } from './folhapagamento/periodo-calculo/periodo-calculo.component';
import { PeriodoCalculoModalComponent } from './folhapagamento/periodo-calculo/periodo-calculo-modal/periodo-calculo-modal.component';
import { CargoModalComponent } from './recrutamento/cargo/cargo-modal/cargo-modal.component';
import { ConhecimentoComponent } from './recrutamento/conhecimento/conhecimento.component';
import { ConhecimentoModalComponent } from './recrutamento/conhecimento/conhecimento-modal/conhecimento-modal.component';
import { DepartamentoModalComponent } from './contratacao/departamento/departamento-modal/departamento-modal.component';
import { HabilidadeAtitudeComponent } from './recrutamento/habilidadeAtitude/habilidadeAtitude.component';
import { HabilidadeAtitudeModalComponent } from './recrutamento/habilidadeAtitude/habilidadeAtitude-modal/habilidadeAtitude-modal.component';
import { EmpresaComponent } from './contratacao/empresa/empresa.component';
import { EmpresaModalComponent } from './contratacao/empresa/empresa-modal/empresa-modal.component';
import { EnderecoComponent } from './contratacao/endereco/endereco.component';
import { EnderecoModalComponent } from './contratacao/endereco/endereco-modal/endereco-modal.component';
import { FuncionarioComponent } from './contratacao/funcionario/funcionario.component';
import { FunionarioModalComponent } from './contratacao/funcionario/funionario-modal/funionario-modal.component';
import { ParametroEmpresaComponent } from './folhapagamento/parametro-empresa/parametro-empresa.component';
import { ParametroEmpresaModalComponent } from './folhapagamento/parametro-empresa/parametro-empresa-modal/parametro-empresa-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    IndexComponent,
    NavbarNavigationComponent,
    ConfiguracoesComponent,
    LoadingSpinnerComponent,
    FolhapagamentoComponent,
    FolhaMenuLateralComponent,
    CabecalhoSistemaComponent,
    RodapeSistemaComponent,
    IncidenciaComponent,
    EventoComponent,
    ContratacaoComponent,
    ContratacaoMenuLateralComponent,
    RecrutamentoMenuLateralComponent,
    RecrutamentoComponent,
    CargoComponent,
    DepartamentoComponent,
    IncidenciaModalComponent,
    EventoModalComponent,
    RotinaCalculoComponent,
    RotinaCalculoModalComponent,
    PeriodoCalculoComponent,
    PeriodoCalculoModalComponent,
    CargoModalComponent,
    ConhecimentoComponent,
    ConhecimentoModalComponent,
    DepartamentoModalComponent,
    HabilidadeAtitudeComponent,
    HabilidadeAtitudeModalComponent,
    EmpresaComponent,
    EmpresaModalComponent,
    EnderecoComponent,
    FuncionarioComponent,
    FunionarioModalComponent,
    EnderecoModalComponent,
    ParametroEmpresaComponent,
    ParametroEmpresaModalComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatButtonModule,
    MatInputModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatRadioModule,
    MatSelectModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatMenuModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatGridListModule,
    MatCardModule,
    MatStepperModule,
    MatTabsModule,
    MatExpansionModule,
    MatButtonToggleModule,
    MatChipsModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatDialogModule,
    MatTooltipModule,
    MatSnackBarModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [ConstantsService, LoaderService],
  bootstrap: [AppComponent]
})
export class AppModule { }

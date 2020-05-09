import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndexComponent } from './geral/index/index.component';
import { HomeComponent } from './geral/home/home.component';
import { ConfiguracoesComponent } from './geral/configuracoes/configuracoes.component';
import { FolhapagamentoComponent } from './folhapagamento/home/folhapagamento.component';
import { IncidenciaComponent } from './folhapagamento/incidencia/incidencia.component';
import { EventoComponent } from './folhapagamento/evento/evento.component';
import {ContratacaoComponent} from './contratacao/home/contratacao.component';
import { RecrutamentoComponent } from './recrutamento/home/recrutamento.component';
import { CargoComponent } from './recrutamento/cargo/cargo.component';
import { DepartamentoComponent } from './contratacao/departamento/departamento.component';
import { EmpresaComponent} from './contratacao/empresa/empresa.component';
import { RotinaCalculoComponent } from './folhapagamento/rotina-calculo/rotina-calculo.component';
import { PeriodoCalculoComponent } from './folhapagamento/periodo-calculo/periodo-calculo.component';
import { ConhecimentoComponent } from './recrutamento/conhecimento/conhecimento.component';
import { HabilidadeAtitudeComponent } from './recrutamento/habilidadeAtitude/habilidadeAtitude.component';
import { EnderecoComponent } from './contratacao/endereco/endereco.component';
import { FuncionarioComponent } from './contratacao/funcionario/funcionario.component';
import { VagaComponent } from './recrutamento/vaga/vaga.component';
import { TabelaSalarialComponent } from './recrutamento/tabelasalarial/tabelasalarial.component';
import { FaixaTabelaSalarialComponent } from './recrutamento/faixatabelasalarial/faixatabelasalarial.component';
import { PermissaoComponent } from './geral/configuracoes/permissao/permissao.component';


const routes: Routes = [
  { path: 'index', component: IndexComponent },
  { path: '',   redirectTo: '/index', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'configuracoes', component: ConfiguracoesComponent },
  { path: 'configuracoes/permissao', component: PermissaoComponent },
  { path: 'contratacao', component: ContratacaoComponent },
  { path: 'contratacao/departamento', component: DepartamentoComponent },
  { path: 'contratacao/empresa', component: EmpresaComponent },
  { path: 'contratacao/endereco', component: EnderecoComponent },
  { path: 'contratacao/funcionario', component: FuncionarioComponent },
  { path: 'folhapagamento', component: FolhapagamentoComponent },
  { path: 'folhapagamento/incidencia', component: IncidenciaComponent },
  { path: 'folhapagamento/evento', component: EventoComponent },
  { path: 'folhapagamento/rotinacalculo', component: RotinaCalculoComponent },
  { path: 'folhapagamento/periodocalculo', component: PeriodoCalculoComponent },
  { path: 'recrutamento', component: RecrutamentoComponent },
  { path: 'recrutamento/cargo', component: CargoComponent },
  { path: 'recrutamento/conhecimento', component: ConhecimentoComponent },
  { path: 'recrutamento/habilidadeAtitude', component: HabilidadeAtitudeComponent },
  { path: 'recrutamento/vaga', component: VagaComponent },
  { path: 'recrutamento/tabelasalarial', component: TabelaSalarialComponent },
  { path: 'recrutamento/faixatabelasalarial', component: FaixaTabelaSalarialComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
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
import { ParametroEmpresaComponent } from './folhapagamento/parametro-empresa/parametro-empresa.component';
import { UsuarioComponent } from './geral/configuracoes/usuario/usuario.component';
import { RecrutamentoInternoComponent } from './recrutamento/recrutamentoInterno/recrutamentoInterno.component';
import { RecrutamentoExternoComponent } from './recrutamento/recrutamentoExterno/recrutamentoExterno.component';
import { BeneficioComponent } from './contratacao/beneficio/beneficio.component';
import { CalculoComponent } from './folhapagamento/calculo/calculo.component';
import { EventoVariavelComponent } from './folhapagamento/evento-variavel/evento-variavel.component';
import { SelecaoCandidatoComponent } from './recrutamento/selecaoCandidato/selecaoCandidato.component';

const routes: Routes = [
  { path: 'index', component: IndexComponent },
  { path: '',   redirectTo: '/index', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'configuracoes', component: ConfiguracoesComponent },
  { path: 'configuracoes/permissao', component: PermissaoComponent },
  { path: 'configuracoes/usuario', component: UsuarioComponent },
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
  { path: 'folhapagamento/parametroempresa', component: ParametroEmpresaComponent },
  { path: 'folhapagamento/calculo', component: CalculoComponent },
  { path: 'folhapagamento/eventovariavel', component: EventoVariavelComponent },
  { path: 'recrutamento', component: RecrutamentoComponent },
  { path: 'recrutamento/cargo', component: CargoComponent },
  { path: 'recrutamento/conhecimento', component: ConhecimentoComponent },
  { path: 'recrutamento/habilidadeAtitude', component: HabilidadeAtitudeComponent },
  { path: 'recrutamento/vaga', component: VagaComponent },
  { path: 'recrutamento/tabelasalarial', component: TabelaSalarialComponent },
  { path: 'recrutamento/faixatabelasalarial', component: FaixaTabelaSalarialComponent },
  { path: 'recrutamento/recrutamentoInterno', component: RecrutamentoInternoComponent },
  { path: 'recrutamento/recrutamentoExterno', component: RecrutamentoExternoComponent },
  { path: 'contratacao/beneficio', component: BeneficioComponent },
  { path: 'recrutamento/selecaoCandidato', component: SelecaoCandidatoComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

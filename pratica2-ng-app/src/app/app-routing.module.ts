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

const routes: Routes = [
  { path: 'index', component: IndexComponent },
  { path: '',   redirectTo: '/index', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'configuracoes', component: ConfiguracoesComponent },
  { path: 'contratacao', component: ContratacaoComponent },
  { path: 'contratacao/departamento', component: DepartamentoComponent },
  { path: 'folhapagamento', component: FolhapagamentoComponent },
  { path: 'folhapagamento/incidencia', component: IncidenciaComponent },
  { path: 'folhapagamento/evento', component: EventoComponent },
  { path: 'recrutamento', component: RecrutamentoComponent },
  { path: 'recrutamento/cargo', component: CargoComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

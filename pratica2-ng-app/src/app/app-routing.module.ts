import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndexComponent } from './geral/index/index.component';
import { HomeComponent } from './geral/home/home.component';
import { ConfiguracoesComponent } from './geral/configuracoes/configuracoes.component';
import { FolhapagamentoComponent } from './folhapagamento/home/folhapagamento.component';
import { IncidenciaComponent } from './folhapagamento/incidencia/incidencia.component';


const routes: Routes = [
  { path: 'index', component: IndexComponent },
  { path: '',   redirectTo: '/index', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'configuracoes', component: ConfiguracoesComponent },
  { path: 'folhapagamento', component: FolhapagamentoComponent },
  { path: 'folhapagamento/incidencia', component: IncidenciaComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

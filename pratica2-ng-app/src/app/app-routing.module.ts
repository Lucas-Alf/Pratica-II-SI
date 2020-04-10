import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndexComponent } from './geral/index/index.component';
import { HomeComponent } from './geral/home/home.component';
import { ConfiguracoesComponent } from './geral/configuracoes/configuracoes.component';
import { FolhapagamentoComponent } from './folhapagamento/home/folhapagamento.component';


const routes: Routes = [
  { path: 'index', component: IndexComponent },
  { path: '',   redirectTo: '/index', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'configuracoes', component: ConfiguracoesComponent },
  { path: 'folhapagamento', component: FolhapagamentoComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  infosUsuario = JSON.parse(localStorage.getItem('userData'));
  diaSemanaAtual: string = "";
  corDiaSemanaAtual: string = "";
  dataExtensaAtual: string = "";
  constructor(private router: Router) { }
  public sair() {
    localStorage.removeItem('userData');
    this.router.navigate(['/index/']);
  }
  public retornaPermissao(modulo) {
    return !(this.infosUsuario.nivel.modulonivelList.filter(x => x.moduloid.id == modulo).length > 0);
  }
  public retornaRota(modulo) {
    if (!this.retornaPermissao(modulo)) {
      return this.infosUsuario.nivel.modulonivelList.filter(x => x.moduloid.id == modulo)[0].moduloid.rota;
    } else {
      return '/home';
    }
  }
  private diaSemana() {
    return [
      { nome: "Domingo", cor: "#008f96" },
      { nome: "Segunda-feira", cor: "#008f96" },
      { nome: "Terça-feira", cor: "#008f96" },
      { nome: "Quarta-feira", cor: "#008f96" },
      { nome: "Quinta-feira", cor: "#008f96" },
      { nome: "Sexta-feira", cor: "#008f96" },
      { nome: "Sábado", cor: "#008f96" }
    ][new Date().getDay()];
  }
  private dataExtensa() {
    var data = new Date()
    var mes = ["janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"][data.getMonth()];
    return data.getDate() + " de " + mes + " de " + data.getFullYear();
  }
  ngOnInit() {
    var dia = this.diaSemana();
    this.diaSemanaAtual = dia.nome;
    this.corDiaSemanaAtual = dia.cor;
    this.dataExtensaAtual = this.dataExtensa();
  }
}

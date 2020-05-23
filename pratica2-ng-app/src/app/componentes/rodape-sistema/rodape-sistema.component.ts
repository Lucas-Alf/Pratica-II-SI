import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-rodape-sistema',
  templateUrl: './rodape-sistema.component.html',
  styleUrls: ['./rodape-sistema.component.css']
})
export class RodapeSistemaComponent implements OnInit {

  constructor() { }

  anoAtual = 2020;

  usuario = '';

  ngOnInit(): void {
    this.anoAtual = new Date().getFullYear();
    if (localStorage.getItem('userData')) {
        const info = JSON.parse(localStorage.getItem('userData'));
        this.usuario = info.pessoa.nome;
    }
  }
}

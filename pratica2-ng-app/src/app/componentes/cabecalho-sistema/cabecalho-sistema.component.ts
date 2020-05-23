import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cabecalho-sistema',
  templateUrl: './cabecalho-sistema.component.html',
  styleUrls: ['./cabecalho-sistema.component.css']
})
export class CabecalhoSistemaComponent implements OnInit {

  constructor(private router: Router) {

  }

  sair(): void {
    if (localStorage.getItem('userData')) {
      this.router.navigate(['/home/']);
    } else {
      this.router.navigate(['/index/']);
    }
  }

  ngOnInit(): void {
  }

}

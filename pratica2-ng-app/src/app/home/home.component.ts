import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  infosUsuario = JSON.parse(localStorage.getItem('userData'));
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
  ngOnInit() { }
}

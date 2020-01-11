import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar-navigation',
  templateUrl: './navbar-navigation.component.html',
  styleUrls: ['./navbar-navigation.component.css']
})
export class NavbarNavigationComponent implements OnInit {

  infosUsuario = JSON.parse(localStorage.getItem('userData'));

  constructor(private router: Router) { }

  public sair() {
    localStorage.removeItem('userData');
    this.router.navigate(['/index/']);
  }

  ngOnInit() {
  }

}

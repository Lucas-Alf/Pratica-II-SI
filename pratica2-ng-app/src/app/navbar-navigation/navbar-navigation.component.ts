import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar-navigation',
  templateUrl: './navbar-navigation.component.html',
  styleUrls: ['./navbar-navigation.component.css']
})
export class NavbarNavigationComponent implements OnInit {

  infosUsuario = JSON.parse(localStorage.getItem('userData'));

  constructor() { }

  ngOnInit() {
  }

}

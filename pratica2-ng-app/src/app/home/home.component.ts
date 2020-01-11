import { Component, OnInit } from '@angular/core';
import axios from 'axios';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  infosUsuario = JSON.parse(localStorage.getItem('userData'));
  fotoUsuario: string;
  constructor() { }

  public fotoAleatoria() {
    axios.get('https://randomuser.me/api/?results=1').then((response) => {
      this.fotoUsuario = response.data.results[0].picture.large;
    });
  }

  ngOnInit() {
    this.fotoAleatoria();
   }
}

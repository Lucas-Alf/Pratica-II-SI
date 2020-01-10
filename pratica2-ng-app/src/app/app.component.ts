import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { ConstantsService } from './common/services/constants.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'Login';
  apiUrl: string;
  email: string;
  senha: string;
  hidePassword = true;
  constructor(private constant: ConstantsService) {
    this.apiUrl = this.constant.apiUrl;
  }
  public login() {
    axios.post(this.apiUrl + 'usuario/login', { email: this.email, senha: this.senha }).then((response) => {
      alert(response.data.email);
    }).catch((error) => {
      if (error.response) {
        alert(error.response.data.message);
      }
    });
  }
  public recuperarSenha() {
    alert('Chorra');
  }
  ngOnInit() { }
}

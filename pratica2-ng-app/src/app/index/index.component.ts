import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { ConstantsService } from '../common/services/constants.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  title = 'Login';
  apiUrl: string;
  email: string;
  senha: string;
  hidePassword = true;
  constructor(private constant: ConstantsService, private router: Router) {
    this.apiUrl = this.constant.apiUrl;
  }
  public login() {
    $('#cover-spin').show(0);
    axios.post(this.apiUrl + 'usuario/login', { email: this.email, senha: this.senha }).then((response) => {
      if (response && response.data) {
        localStorage.setItem('userData', JSON.stringify(response.data));
        this.router.navigate(['/home/']);
        // alert(response.data);
      } else {
        alert('Usuário e senha não encontrados.');
      }
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

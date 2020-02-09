import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { ConstantsService } from '../common/services/constants.service';
import { Router } from '@angular/router';
import { LoaderService } from '../services/loader.service';

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
  constructor(private constant: ConstantsService, private router: Router, private loaderService: LoaderService) {
    this.apiUrl = this.constant.apiUrl;
  }
  public login() {
    this.loaderService.show();
    axios.post(this.apiUrl + 'usuario/login', { email: this.email, senha: this.senha }).then((response) => {
      if (response && response.data) {
        localStorage.setItem('userData', JSON.stringify(response.data));
        this.loaderService.hide();
        this.router.navigate(['/home/']);
        // alert(response.data);
      } else {
        this.loaderService.hide();
        alert('Usuário e senha não encontrados.');
      }
    }).catch((error) => {
      if (error.response) {
        this.loaderService.hide();
        alert(error.response.data.message);
      }
    });
  }
  public recuperarSenha() {
    alert('Chorra');
  }
  ngOnInit() {
  }
}

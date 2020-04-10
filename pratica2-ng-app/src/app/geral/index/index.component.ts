import { Component, OnInit } from '@angular/core';
import axios from 'axios';
import { ConstantsService } from '../../common/services/constants.service';
import { Router } from '@angular/router';
import { LoaderService } from '../../services/loader.service';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';

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
  constructor(
    private constant: ConstantsService,
    private router: Router,
    private loaderService: LoaderService,
    private snackBar: MatSnackBar
  ) {
    this.apiUrl = this.constant.apiUrl;
  }
  public login() {
    if (this.email && this.senha) {
      this.loaderService.show();
      axios.post(this.apiUrl + 'usuario/login', { email: this.email, senha: this.senha }).then((response) => {
        if (response && response.data) {
          localStorage.setItem('userData', JSON.stringify(response.data));
          this.loaderService.hide();
          this.router.navigate(['/home/']);
          // alert(response.data);
        } else {
          this.loaderService.hide();
          this.snackBar.open("Usuário e senha não encontrados. 🤔", null, { duration: 5000 });
        }
      }).catch((error) => {
        this.loaderService.hide();
        if (error.response) {
          console.error(error.response.data.message);
        }
        this.snackBar.open("Ocorreu um erro ao fazer login. 😬", null, { duration: 5000 });
      });
    } else {
      this.snackBar.open("❌ Email e Senha são obrigatórios.", null, { duration: 5000 });
    }
  }
  public recuperarSenha() {
    this.snackBar.open("Essa função ainda não foi implementada. 😥", null, { duration: 5000 });
  }
  ngOnInit() {
  }
}

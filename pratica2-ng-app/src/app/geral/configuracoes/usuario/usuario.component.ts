import { Component, OnInit } from '@angular/core';
import { UsuarioModalComponent } from './usuario-modal/usuario-modal.component';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { Usuario } from './usuario';
import axios from 'axios';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {

  infosUsuario = JSON.parse(localStorage.getItem('userData'));
  apiUrl: string;
  dialogRef: MatDialogRef<UsuarioModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  salvar(action: string, data: Usuario): void {
    this.loaderService.show();
    debugger
    axios.post(this.constant.apiUrl + 'usuario/' + action, data).then((response) => {
      if (response && response.data) {
        this.loaderService.hide();
        this.dialogRef.close();
      } else {
        this.loaderService.hide();
        this.snackBar.open('Ocorreu um erro ao salvar. 😬', null, { duration: 5000 });
      }
    }).catch((error) => {
      this.loaderService.hide();
      if (error.response) {
        console.error(error.response.data.message);
        this.snackBar.open(error.response.data.message, null, { duration: 5000 });
      } else {
        this.snackBar.open('Ocorreu um erro ao salvar. 😬', null, { duration: 5000 });
      }
    });
  }

  alterar(): void {
    this.dialogRef = this.dialog.open(UsuarioModalComponent, { data: { action: 'Alterar', component: this, info: this.infosUsuario }, disableClose: true });
  }

  ngOnInit(): void {
    
    //Preenche a tabela
    this.alterar();

  }

}

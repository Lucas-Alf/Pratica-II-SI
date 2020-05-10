import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Usuario } from '../usuario';
import { Pessoa } from '../pessoa';
import { Nivel } from '../../permissao/permissao';
import axios from 'axios';
import { LoaderService } from 'src/app/services/loader.service';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';



@Component({
  selector: 'app-usuario-modal',
  templateUrl: './usuario-modal.component.html',
  styleUrls: ['./usuario-modal.component.css']
})
export class UsuarioModalComponent implements OnInit {
  codigoHabilitado: boolean;
  apiUrl: string;
  id: number;
  email: string;
  senha: string;
  pessoa: string;
  nivel: number;

  pessoas: Pessoa[];
  niveis: Nivel[];
  dummyDate: Date;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<UsuarioModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) { this.apiUrl = this.constant.apiUrl; this.listarPessoas(); this.listarNiveis() }

  close(): void {
    this.dialogRef.close();
  }

  listarPessoas(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'pessoa/all').then((response) => {
      if (response && response.data) {
        this.pessoas = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarNiveis(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'nivel/all').then((response) => {
      if (response && response.data) {
        this.niveis = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  save(): void {
    const dados: Usuario = { id: this.id, email: this.email, senha: this.senha, 
      pessoa:{cpf:this.pessoa,nome:'',datanascimento:this.dummyDate,paisnascimentoid:0,rg:'',sexo:'',telefonecelular:'',telefonefixo:''},
      nivel:{id:this.nivel, descricao:''} };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {

    if (this.data.info) {
      this.id = this.data.info.id;
      this.email = this.data.info.email;
      this.senha = this.data.info.senha;
      this.pessoa = this.data.info.pessoa.cpf;
      this.nivel = this.data.info.nivel.id;
      this.codigoHabilitado = false;
    } else {
      this.codigoHabilitado = true;
    }
  }
}

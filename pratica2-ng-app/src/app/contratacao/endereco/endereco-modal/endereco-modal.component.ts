import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Endereco } from '../endereco';

export interface Cidade {
  id: number;
  nome: string;
  estadoid: {
    id: number;
    nome: string;
    sigla: string;
  }
}

@Component({
  selector: 'app-endereco-modal',
  templateUrl: './endereco-modal.component.html',
  styleUrls: ['./endereco-modal.component.css'],
})

export class EnderecoModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  logradouro: string;
  bairro: string;
  cidadeid: number;
  cep: string;

  cidades: Cidade[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<EnderecoModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarCidade();
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Endereco = { id: this.id, logradouro: this.logradouro, bairro: this.bairro, cep: this.cep, cidadeid: { id: this.cidadeid, nome: '', estadoid: { id: 0, nome: '', sigla: '' } } };
    this.data.component.salvar(this.data.action, dados);
  }

  listarCidade(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'cidade/all').then((response) => {
      if (response && response.data) {
        debugger
        this.cidades = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.logradouro = this.data.info.logradouro;
      this.bairro = this.data.info.bairro;
      this.cep = this.data.info.cep;
      this.cidadeid = this.data.info.cidadeid.id;
    }
  }

}
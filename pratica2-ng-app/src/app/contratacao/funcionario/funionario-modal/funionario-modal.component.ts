import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Funcionario } from '../funcionario';
import { Pais } from '../pais';

@Component({
  selector: 'app-funionario-modal',
  templateUrl: './funionario-modal.component.html',
  styleUrls: ['./funionario-modal.component.css']
})
export class FunionarioModalComponent implements OnInit {
  apiUrl: string;

  cpf: string;
  nome: string;
  paisnascimentoid: number;
  paises: Pais[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<FunionarioModalComponent>, private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) { this.apiUrl = this.constant.apiUrl; this.listarPais(); }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Funcionario = { cpf: this.cpf, nome: this.nome, paisnascimentoid: { id: this.paisnascimentoid, nome: '' }, };
    this.data.component.salvar(this.data.action, dados);
  }

  listarPais(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'pais/all').then((response) => {
      if (response && response.data) {
        this.paises = response.data;
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
      this.cpf = this.data.info.cpf;
      this.nome = this.data.info.nome;
      this.paisnascimentoid = this.data.info.paisnascimentoid.id;
    }
  }

}

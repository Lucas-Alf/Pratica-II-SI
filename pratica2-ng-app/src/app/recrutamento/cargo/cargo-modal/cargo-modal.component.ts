import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Cargo } from '../cargo';

export interface Cbo {
  id: string;
  descricaosumaria: string;
}

export interface Departamento {
  id: string;
  nome: string;
  descricao: string;
}

@Component({
  selector: 'app-cargo-modal',
  templateUrl: './cargo-modal.component.html',
  styleUrls: ['./cargo-modal.component.css'],
})

export class CargoModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  descricao: string;
  cboid: string;
  departamentoid: number;
  faixatabelasalarialid: number;

  cbos: Cbo[];
  departamentos: Departamento[];
  faixaTabelaSalarialList: any[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<CargoModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarCbo();
    this.listarDepartamento();
    this.listarFaixaTabelaSalarial();
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Cargo = { id: this.id, descricao: this.descricao, cboid: { id: this.cboid, descricaosumaria: '' }, departamentoid: { id: this.departamentoid, nome: '', descricao: '' }, faixatabelasalarial: { id: this.faixatabelasalarialid } };
    this.data.component.salvar(this.data.action, dados);
  }

  listarCbo(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'cbo/all').then((response) => {
      if (response && response.data) {
        this.cbos = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarDepartamento(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'departamento/all').then((response) => {
      if (response && response.data) {
        this.departamentos = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarFaixaTabelaSalarial(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'FaixaTabelaSalarial/all').then((response) => {
      if (response && response.data) {
        this.faixaTabelaSalarialList = response.data;
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
      this.descricao = this.data.info.descricao;
      this.cboid = this.data.info.cboid.id;
      this.departamentoid = this.data.info.departamentoid.id;
      this.faixatabelasalarialid = this.data.info.faixatabelasalarial.id;
    }
  }

}

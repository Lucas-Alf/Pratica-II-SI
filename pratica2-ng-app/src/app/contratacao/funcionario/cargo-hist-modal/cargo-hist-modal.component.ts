import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { FormBuilder } from '@angular/forms';
import { Contrato } from '../contrato';
import { Cargo } from 'src/app/recrutamento/cargo/cargo';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { Historicocargocontrato } from '../historicocargocontrato';
import axios from 'axios';

@Component({
  selector: 'app-cargo-hist-modal',
  templateUrl: './cargo-hist-modal.component.html',
  styleUrls: ['./cargo-hist-modal.component.css']
})
export class CargoHistModalComponent implements OnInit {
  apiUrl: string;

  id: number;
  data: Date;
  matricula: number;
  cargoid: number;

  cargos: Cargo[];
  constructor(
    @Inject(MAT_DIALOG_DATA) public dataD,
    public dialogCargo: MatDialogRef<CargoHistModalComponent>, private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService,
    private _formBuilder: FormBuilder,
    public dialog: MatDialog
  ) { this.apiUrl = this.constant.apiUrl; this.listarCargo(); }
  displayedColumns: string[] = ['data', 'cargoid'];
  storeHistCargoCont = new MatTableDataSource();
  selection = new SelectionModel<Historicocargocontrato>();

  gravar() {
    const dados: Historicocargocontrato = {
      id: null,
      data: this.data,
      matricula: {
        situacao: null,
        dataadmissao: null,
        regimetrabalho: null,
        horastrabalho: null,
        datademissao: null,
        departamentoid: null,
        pessoa: null,
        matricula: this.matricula,
      },
      cargoid: {
        descricao: null,
        cboid: null,
        faixatabelasalarial: null,
        cargoConhecimentos: null,
        id: this.cargoid,
        cargoHabilidadeAtitudes: null
      }
    };
    console.log(dados);
    this.salvarCargoContrato(dados);
  }

  alterar() { }
  cancelar() { }
  save() { }
  close(): void {
    this.dialogCargo.close();
  }

  listar(matricula): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'historicocargocontrato/allMatricula?matricula=' + matricula).then((response) => {
      if (response && response.data) {
        this.storeHistCargoCont.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }
  listarCargo(): void {
    //this.loaderService.show();
    axios.get(this.apiUrl + 'cargo/all').then((response) => {
      if (response && response.data) {
        this.cargos = response.data;
        // this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  salvarCargoContrato(data: Historicocargocontrato): void {
    this.loaderService.show();
    axios.post(this.constant.apiUrl + 'historicocargocontrato/Incluir', data).then((response) => {
      if (response && response.data) {
        this.listar(this.matricula);
      } else {
        this.loaderService.hide();
        this.snackBar.open('Ocorreu um erro ao salvar o cargo do contrato. 😬', null, { duration: 5000 });
      }
    }).catch((error) => {
      this.loaderService.hide();
      if (error.response) {
        console.error(error.response.data.message);
        this.snackBar.open(error.response.data.message, null, { duration: 5000 });
      } else {
        this.snackBar.open('Ocorreu um erro ao salvar o cargo do contrato. 😬', null, { duration: 5000 });
      }
    });
  }
  ngOnInit(): void {
    this.matricula = this.dataD.info.matricula;
    this.listar(this.dataD.info.matricula);
  }

}

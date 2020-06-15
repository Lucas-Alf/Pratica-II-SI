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
  matricula: Contrato;
  cargoid: Cargo;

  cargos: Cargo[];
  constructor(
    @Inject(MAT_DIALOG_DATA) public dataD,
    public dialogCargo: MatDialogRef<CargoHistModalComponent>, private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService,
    private _formBuilder: FormBuilder,
    public dialog: MatDialog
  ) { this.apiUrl = this.constant.apiUrl; this.listarCargo(); }
  displayedColumns: string[] = ['select', 'data', 'cargoid'];
  storeHistCargoCont = new MatTableDataSource();
  selection = new SelectionModel<Historicocargocontrato>();

  gravar(){}
  alterar(){}
  cancelar(){}
  save() { }
  close(): void {
    this.dialogCargo.close();
  }


  listarCargo(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'cargo/all').then((response) => {
      if (response && response.data) {
        this.cargos = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
  }

}

import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { FaixaTabelaSalarial } from './faixatabelasalarial';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { FaixaTabelaSalarialModalComponent } from './faixatabelasalarial-modal/faixatabelasalarial-modal.component';

@Component({
  selector: 'app-faixatabelasalarial',
  templateUrl: './faixatabelasalarial.component.html',
  styleUrls: ['./faixatabelasalarial.component.css']
})
export class FaixaTabelaSalarialComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<FaixaTabelaSalarialModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'id', 'tabelasalarial.descricao', 'tabelasalarial.valorbase','nivel', 'percentual'];
  storeFaixaTabelaSalarial = new MatTableDataSource();
  selection = new SelectionModel<FaixaTabelaSalarial>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeFaixaTabelaSalarial.data.length;
    return numSelected == numRows;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeFaixaTabelaSalarial.filter = filterValue;
  }

  incluir(): void {
    this.dialogRef = this.dialog.open(FaixaTabelaSalarialModalComponent, { data: { action: 'Incluir', component: this } });
  }

  salvar(action: string, data: FaixaTabelaSalarial): void {
    debugger
    this.loaderService.show();
    if (data.tabelasalarial.id == undefined || data.tabelasalarial.id == 0) {
      this.loaderService.hide();
      this.snackBar.open('Informe a Tabela Salarial.', null, { duration: 5000 });
    } else if (data.nivel == undefined || data.nivel == 0) {
      this.loaderService.hide();
      this.snackBar.open('Informe o Nível.', null, { duration: 5000 });
    } else if (data.percentual == undefined || data.percentual == 0) {
      this.loaderService.hide();
      this.snackBar.open('Informe o Percentual', null, { duration: 5000 });
    } else {
      axios.post(this.constant.apiUrl + 'FaixaTabelaSalarial/' + action, data).then((response) => {
        if (response && response.data) {
          this.loaderService.hide();
          this.listar();
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
  }

  alterar(): void {
    if (this.selection.selected.length > 0) {
      const selection = this.selection.selected[0];
      this.dialogRef = this.dialog.open(FaixaTabelaSalarialModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'FaixaTabelaSalarial/delete/' + this.selection.selected[0].id).then((response) => {
        if (response && response.status === 200) {
          this.loaderService.hide();
          this.listar();
        } else {
          this.loaderService.hide();
          console.log('response -> ', response);
          this.snackBar.open('Ocorreu um erro ao excluir o registro. 🤔', null, { duration: 5000 });
        }
      }).catch((error) => {
        this.loaderService.hide();
        console.log(error);
        this.snackBar.open('Ocorreu um erro ao excluir o registro. 🤔', null, { duration: 5000 });
      });
    } else {
      this.snackBar.open('Selecione um registro para excluir. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'FaixaTabelaSalarial/all').then((response) => {
      debugger
      if (response && response.data) {
        this.storeFaixaTabelaSalarial.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeFaixaTabelaSalarial.filterPredicate = (data: FaixaTabelaSalarial, filter) => {
      return !filter || data.tabelasalarial.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<FaixaTabelaSalarial>(allowMultiSelect, initialSelection);
  }

}
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { PeriodoCalculo } from './PeriodoCalculo';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { PeriodoCalculoModalComponent } from './periodo-calculo-modal/periodo-calculo-modal.component';

@Component({
  selector: 'app-periodo-calculo',
  templateUrl: './periodo-calculo.component.html',
  styleUrls: ['./periodo-calculo.component.css']
})
export class PeriodoCalculoComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<PeriodoCalculoModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'dataInicial', 'dataFinal', 'execucao'];
  storePeriodoCalculo = new MatTableDataSource();
  selection = new SelectionModel<PeriodoCalculo>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storePeriodoCalculo.data.length;
    return numSelected == numRows;
  }

  incluir(): void {
    this.dialogRef = this.dialog.open(PeriodoCalculoModalComponent, { data: { action: 'Incluir', component: this } });
  }

  alterar(): void {
    if (this.selection.selected.length > 0) {
      const selection = this.selection.selected[0];
      this.dialogRef = this.dialog.open(PeriodoCalculoModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'PeriodoCalculo/delete/' + this.selection.selected[0].id).then((response) => {
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

  salvar(action: string, data: PeriodoCalculo): void {
    this.loaderService.show();
    axios.post(this.constant.apiUrl + 'PeriodoCalculo/' + action, data).then((response) => {
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

  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'PeriodoCalculo/all').then((response) => {
      if (response && response.data) {
        this.storePeriodoCalculo.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.listar();
    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<PeriodoCalculo>(allowMultiSelect, initialSelection);
  }
}


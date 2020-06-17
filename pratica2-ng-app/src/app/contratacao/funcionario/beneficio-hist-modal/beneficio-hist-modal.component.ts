import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { FormBuilder } from '@angular/forms';
import axios from 'axios';
import { EventoFixo } from '../eventofixo';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-beneficio-hist-modal',
  templateUrl: './beneficio-hist-modal.component.html',
  styleUrls: ['./beneficio-hist-modal.component.css']
})
export class BeneficioHistModalComponent implements OnInit {

  apiUrl: string;
  storeEventosFixos = new MatTableDataSource<EventoFixo>();

  matricula: number;

  selection = new SelectionModel<EventoFixo>();
  displayedColumns: string[] = ['select', 'evento', 'dataInicial', 'dataFinal', 'referencia', 'valor'];

  constructor(@Inject(MAT_DIALOG_DATA) public data,
    public dialogCargo: MatDialogRef<BeneficioHistModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService,
    public dialog: MatDialog) {
    this.apiUrl = this.constant.apiUrl;
    this.listarPorContrato();
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeEventosFixos.data.length;
    return numSelected == numRows;
  }

  close(): void {
    this.dialogCargo.close();
  }

  listarPorContrato(): void {
    if (this.matricula) {
      axios.get(this.apiUrl + 'eventofixo/buscaPorContrato?matricula=' + this.matricula).then((response) => {
        if (response && response.data) {
          this.storeEventosFixos.data = response.data;
        }
      }).catch((error) => {
        console.log(error);
        this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
      });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      axios.delete(this.apiUrl + 'eventofixo/delete/' + this.selection.selected[0].id).then((response) => {
        if (response && response.status === 200) {
          this.listarPorContrato();
        } else {
          console.log('response -> ', response);
          this.snackBar.open('Ocorreu um erro ao excluir o registro. 🤔', null, { duration: 5000 });
        }
      }).catch((error) => {
        console.log(error);
        this.snackBar.open('Ocorreu um erro ao excluir o registro. 🤔', null, { duration: 5000 });
      });
    } else {
      this.snackBar.open('Selecione um registro para excluir. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  ngOnInit(): void {
    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<EventoFixo>(allowMultiSelect, initialSelection);
    this.matricula = this.data.info.matricula;
    this.listarPorContrato();
  }
}

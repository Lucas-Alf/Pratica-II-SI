import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { EventoVariavel } from './eventoVariavel';
import { EventoVariavelModalComponent } from './evento-variavel-modal/evento-variavel-modal.component';

@Component({
  selector: 'app-evento-variavel',
  templateUrl: './evento-variavel.component.html',
  styleUrls: ['./evento-variavel.component.css']
})
export class EventoVariavelComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<EventoVariavelModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'cpf', 'eventoid', 'data', 'referencia', 'valor'];
  storeEvento = new MatTableDataSource();
  selection = new SelectionModel<EventoVariavel>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeEvento.data.length;
    return numSelected == numRows;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeEvento.filter = filterValue;
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'eventovariavel/delete/' + this.selection.selected[0].id).then((response) => {
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

  incluir(): void {
    this.dialogRef = this.dialog.open(EventoVariavelModalComponent, { data: { action: 'Incluir', component: this } });
  }

  alterar(): void {
    if (this.selection.selected.length > 0) {
      const selection = this.selection.selected[0];
      this.dialogRef = this.dialog.open(EventoVariavelModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  salvar(action: string, data: EventoVariavel): void {
    this.loaderService.show();
    axios.post(this.constant.apiUrl + 'eventovariavel/' + action, data).then((response) => {
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
    axios.get(this.apiUrl + 'eventovariavel/all').then((response) => {
      if (response && response.data) {
        this.storeEvento.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeEvento.filterPredicate = (data: EventoVariavel, filter) => {
      return !filter || data.contratoMatricula.pessoa.nome.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<EventoVariavel>(allowMultiSelect, initialSelection);
  }
}

import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { TabelaSalarial } from './tabelasalarial';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TabelaSalarialModalComponent } from './tabelasalarial-modal/tabelasalarial-modal.component';

@Component({
  selector: 'app-tabelasalarial',
  templateUrl: './tabelasalarial.component.html',
  styleUrls: ['./tabelasalarial.component.css']
})
export class TabelaSalarialComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<TabelaSalarialModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'id', 'descricao', 'valorbase'];
  storeTabelaSalarial = new MatTableDataSource();
  selection = new SelectionModel<TabelaSalarial>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeTabelaSalarial.data.length;
    return numSelected == numRows;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeTabelaSalarial.filter = filterValue;
  }

  incluir(): void {
    this.dialogRef = this.dialog.open(TabelaSalarialModalComponent, { data: { action: 'Incluir', component: this } });
  }

  salvar(action: string, data: TabelaSalarial): void {
    this.loaderService.show();
    if (data.descricao == undefined || data.descricao == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe a Descrição.', null, { duration: 5000 });
    } else if (data.valorbase == undefined || data.valorbase == 0) {
      this.loaderService.hide();
      this.snackBar.open('Informe a Formação.', null, { duration: 5000 });
    } else {
      axios.post(this.constant.apiUrl + 'TabelaSalarial/' + action, data).then((response) => {
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
      this.dialogRef = this.dialog.open(TabelaSalarialModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'TabelaSalarial/delete/' + this.selection.selected[0].id).then((response) => {
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
    axios.get(this.apiUrl + 'TabelaSalarial/all').then((response) => {
      if (response && response.data) {
        this.storeTabelaSalarial.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeTabelaSalarial.filterPredicate = (data: TabelaSalarial, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<TabelaSalarial>(allowMultiSelect, initialSelection);
  }

}
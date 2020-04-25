import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { HabilidadeAtitude } from './habilidadeAtitude';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { HabilidadeAtitudeModalComponent } from './habilidadeAtitude-modal/habilidadeAtitude-modal.component';

@Component({
selector: 'app-habilidadeAtitude',
  templateUrl: './habilidadeAtitude.component.html',
  styleUrls: ['./habilidadeAtitude.component.css']
})
export class HabilidadeAtitudeComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<HabilidadeAtitudeModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'id', 'descricao', 'tipo'];
  storeHabilidadeAtitude = new MatTableDataSource();
  selection = new SelectionModel<HabilidadeAtitude>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeHabilidadeAtitude.data.length;
    return numSelected == numRows;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeHabilidadeAtitude.filter = filterValue;
  }

  incluir(): void {
    this.dialogRef = this.dialog.open(HabilidadeAtitudeModalComponent, { data: { action: 'Incluir', component: this } });
  }

  salvar(action: string, data: HabilidadeAtitude): void {
    debugger
    this.loaderService.show();
    if (data.descricao == undefined || data.descricao == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe a Descrição.', null, { duration: 5000 });
    } else if (data.tipo == undefined) {
      this.loaderService.hide();
      this.snackBar.open('Informe o Tipo.', null, { duration: 5000 });
    } else {
      axios.post(this.constant.apiUrl + 'habilidadeAtitude/' + action, data).then((response) => {
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
      this.dialogRef = this.dialog.open(HabilidadeAtitudeModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'habilidadeAtitude/delete/' + this.selection.selected[0].id).then((response) => {
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
    axios.get(this.apiUrl + 'habilidadeAtitude/all').then((response) => {
      if (response && response.data) {
        this.storeHabilidadeAtitude.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeHabilidadeAtitude.filterPredicate = (data: HabilidadeAtitude, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<HabilidadeAtitude>(allowMultiSelect, initialSelection);
  }

}
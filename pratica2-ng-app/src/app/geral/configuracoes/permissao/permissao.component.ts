import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { PermissaoModalComponent } from './permissao-modal/permissao-modal.component';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { Nivel } from './permissao';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-permissao',
  templateUrl: './permissao.component.html',
  styleUrls: ['./permissao.component.css']
})
export class PermissaoComponent implements OnInit {
  displayedColumns: string[] = ['select', 'id', 'descricao'];
  storePermissao = new MatTableDataSource();
  selection = new SelectionModel<Nivel>();

  apiUrl: string;
  dialogRef: MatDialogRef<PermissaoModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storePermissao.data.length;
    return numSelected == numRows;
  }

  incluir(): void {
    this.dialogRef = this.dialog.open(PermissaoModalComponent, { data: { action: 'Incluir', component: this } });
  }

  salvar(action: string, data: Nivel): void {
    this.loaderService.show();
    axios.post(this.constant.apiUrl + 'nivel/' + action, data).then((response) => {
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

  alterar(): void {
    if (this.selection.selected.length > 0) {
      const selection = this.selection.selected[0];
      this.dialogRef = this.dialog.open(PermissaoModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'nivel/delete/' + this.selection.selected[0].id).then((response) => {
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
    axios.get(this.apiUrl + 'nivel/all').then((response) => {
      if (response && response.data) {
        this.storePermissao.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }
  ngOnInit(): void {
    this.storePermissao.filterPredicate = (data: Nivel, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storePermissao.filter = filterValue;
  }
}
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SelectionModel } from '@angular/cdk/collections';
import { LoaderService } from 'src/app/services/loader.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Pessoa } from './pessoa';
import { FunionarioModalComponent } from './funionario-modal/funionario-modal.component';

@Component({
  selector: 'app-funcionario',
  templateUrl: './funcionario.component.html',
  styleUrls: ['./funcionario.component.css']
})
export class FuncionarioComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<FunionarioModalComponent, any>;

  constructor(private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog) { this.apiUrl = this.constant.apiUrl; }

  displayedColumns: string[] = ['select', 'cpf', 'nome'];
  storeFuncionario = new MatTableDataSource();
  selection = new SelectionModel<Pessoa>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeFuncionario.data.length;
    return numSelected == numRows;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeFuncionario.filter = filterValue;
  }
  ngOnInit(): void {
    this.storeFuncionario.filterPredicate = (data: Pessoa, filter) => {
      return !filter || data.nome.toLowerCase().includes(filter.toLowerCase());
    }

    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<Pessoa>(allowMultiSelect, initialSelection);
  }
  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'pessoa/all').then((response) => {
      if (response && response.data) {
        this.storeFuncionario.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  incluir(): void {
    this.dialogRef = this.dialog.open(FunionarioModalComponent, { data: { action: 'Incluir', component: this } });
  }
  alterar(): void {
    if (this.selection.selected.length > 0) {
      const selection = this.selection.selected[0];
      this.dialogRef = this.dialog.open(FunionarioModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'funcionario/delete/' + this.selection.selected[0].cpf).then((response) => {
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

  salvar(action: string, data: Pessoa): void {
    this.loaderService.show();
    if (data.nome == undefined || data.nome == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe o Nome.', null, { duration: 5000 });
    } else {
      axios.post(this.constant.apiUrl + 'funcionario/' + action, data).then((response) => {
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
}

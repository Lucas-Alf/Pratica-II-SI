import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Cargo } from './cargo';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { CargoModalComponent } from './cargo-modal/cargo-modal.component';

@Component({
  selector: 'app-cargo',
  templateUrl: './cargo.component.html',
  styleUrls: ['./cargo.component.css']
})
export class CargoComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<CargoModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'id', 'descricao', 'cboid'];
  storeCargo = new MatTableDataSource();
  selection = new SelectionModel<Cargo>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeCargo.data.length;
    return numSelected == numRows;
  }

  applyFilter(event: Event) {
    debugger
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeCargo.filter = filterValue;
  }

  incluir(): void {
    this.dialogRef = this.dialog.open(CargoModalComponent, { data: { action: 'Incluir', component: this } });
  }

  salvar(action: string, data: Cargo): void {
    debugger
    this.loaderService.show();
    if (data.descricao == undefined || data.descricao == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe a Descrição.', null, { duration: 5000 });
    } else if (data.cboid.id == undefined || data.cboid.id == null) {
      this.loaderService.hide();
      this.snackBar.open('Informe o CBO.', null, { duration: 5000 });
    } else if (data.faixatabelasalarial.id == undefined || data.faixatabelasalarial.id == null) {
      this.loaderService.hide();
      this.snackBar.open('Informe a Faixa Tabela Salarial', null, { duration: 5000 });
    } else {
      axios.post(this.constant.apiUrl + 'cargo/' + action, data).then((response) => {
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
    debugger
    if (this.selection.selected.length > 0) {
      const selection = this.selection.selected[0];
      this.dialogRef = this.dialog.open(CargoModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'cargo/delete/' + this.selection.selected[0].id).then((response) => {
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
    axios.get(this.apiUrl + 'cargo/all').then((response) => {
      if (response && response.data) {
        this.storeCargo.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeCargo.filterPredicate = (data: Cargo, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<Cargo>(allowMultiSelect, initialSelection);
  }
}

import { Component, OnInit } from '@angular/core';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { RotinaCalculo } from './RotinaCalculo';
import { MatTableDataSource } from '@angular/material/table';
import { RotinaCalculoModalComponent } from './rotina-calculo-modal/rotina-calculo-modal.component';
import axios from 'axios';

@Component({
  selector: 'app-rotina-calculo',
  templateUrl: './rotina-calculo.component.html',
  styleUrls: ['./rotina-calculo.component.css']
})
export class RotinaCalculoComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<RotinaCalculoModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'id', 'descricao'];
  storeRotinaCalculo = new MatTableDataSource();
  selection = new SelectionModel<RotinaCalculo>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeRotinaCalculo.data.length;
    return numSelected == numRows;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeRotinaCalculo.filter = filterValue;
  }

  incluir(): void {
    this.dialogRef = this.dialog.open(RotinaCalculoModalComponent, { data: { action: 'Incluir', component: this } });
  }

  alterar(): void {
    if (this.selection.selected.length > 0) {
      const selection = this.selection.selected[0];
      this.dialogRef = this.dialog.open(RotinaCalculoModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'RotinaCalculo/delete/' + this.selection.selected[0].id).then((response) => {
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

  salvar(action: string, data: RotinaCalculo): void {
    this.loaderService.show();
    axios.post(this.constant.apiUrl + 'RotinaCalculo/' + action, data).then((response) => {
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
    axios.get(this.apiUrl + 'RotinaCalculo/all').then((response) => {
      if (response && response.data) {
        this.storeRotinaCalculo.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeRotinaCalculo.filterPredicate = (data: RotinaCalculo, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<RotinaCalculo>(allowMultiSelect, initialSelection);
  }

}

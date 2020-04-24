import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Departamento } from './departamento';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SelectionModel } from '@angular/cdk/collections';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-departamento',
  templateUrl: './departamento.component.html',
  styleUrls: ['./departamento.component.css']
})
export class DepartamentoComponent implements OnInit {

  apiUrl: string;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'id', 'descricao', 'nome'];
  storeDepartamento = new MatTableDataSource();

  selection = new SelectionModel<Departamento>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeDepartamento.data.length;
    return numSelected == numRows;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeDepartamento.filter = filterValue;
  }

  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'departamento/delete/' + this.selection.selected[0].id).then((response) => {
        if (response && response.status === 200) {
          this.loaderService.hide();
          //this.snackBar.open("Departamento: " + this.selection.selected[0].nome, null, { duration: 5000 });
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
    axios.get(this.apiUrl + 'departamento/all').then((response) => {
      if (response && response.data) {
        this.storeDepartamento.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeDepartamento.filterPredicate = (data: Departamento, filter) => {
      return !filter || data.nome.toLowerCase().includes(filter.toLowerCase());
    }

    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<Departamento>(allowMultiSelect, initialSelection);
  }

}

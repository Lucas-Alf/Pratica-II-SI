import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { Pessoa } from 'src/app/contratacao/funcionario/pessoa';

@Component({
  selector: 'app-calculo',
  templateUrl: './calculo.component.html',
  styleUrls: ['./calculo.component.css']
})
export class CalculoComponent implements OnInit {

  apiUrl: string;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['select', 'cpf', 'nome'];
  displayedColumnsCalc: string[] = ['tipo', 'evento', 'valor'];
  storeFuncionario = new MatTableDataSource();
  storeCalculo = new MatTableDataSource();
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
  ngOnInit(): void {
    this.storeFuncionario.filterPredicate = (data: Pessoa, filter) => {
      return !filter || data.nome.toLowerCase().includes(filter.toLowerCase());
    }

    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<any>(allowMultiSelect, initialSelection);
  }
}



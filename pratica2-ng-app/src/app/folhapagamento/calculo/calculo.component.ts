import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { Pessoa } from 'src/app/contratacao/funcionario/pessoa';
import { Subject } from 'rxjs';

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

  isLoading: boolean = false;
  displayedColumns: string[] = ['select', 'situacao', 'matricula', 'nome'];
  displayedColumnsCalc: string[] = ['tipo', 'evento', 'referencia', 'valor'];
  storeFuncionario = new MatTableDataSource();
  storeCalculo = new MatTableDataSource();
  selection = new SelectionModel<any>();

  calcular(): void {
    var contratoMatricula = null;
    if (this.selection.selected.length > 0) {
      contratoMatricula = this.selection.selected[0].matricula;
    }
    this.loaderService.show();
    axios.get(encodeURI(this.apiUrl + 'calculo/calcular?matricula=' + contratoMatricula)).then((response) => {
      this.loaderService.hide();
      if (this.selection.selected.length > 0) {
        this.buscaCalculo(this.selection.selected[0]);
      } else {
        this.buscaCalculo(this.storeFuncionario.data[0]);
      }
    }).catch((error) => {
      this.loaderService.hide();
      if (error.response) {
        console.error(error.response.data.message);
      }
      this.snackBar.open('Ocorreu um erro durrante o processamento. 😬', null, { duration: 5000 });
    });
  }

  imprimir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.get(this.apiUrl + 'calculo/imprimir?matricula=' + this.selection.selected[0].matricula, { responseType: 'blob' }).then((response) => {
        this.loaderService.hide();
        var blob = new Blob([response.data], { type: 'application/pdf' });
        var url = window.URL.createObjectURL(blob);
        window.open(url);
      }).catch((error) => {
        this.loaderService.hide();
        if (error.response) {
          console.error(error.response.data.message);
        }
        this.snackBar.open('Ocorreu um erro durrante o processamento. 😬', null, { duration: 5000 });
      });
    } else {
      this.snackBar.open('Selecione um contrato. 🤦‍♂️', null, { duration: 5000 });
    }
  }

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
    axios.get(this.apiUrl + 'contrato/listagemFolhaPagamento').then((response) => {
      if (response && response.data) {
        this.storeFuncionario.data = response.data;
        this.loaderService.hide();
        if (response.data.length > 0) {
          const first = response.data[0];
          this.selection.select(first);
          this.buscaCalculo(first);
        }
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  buscaCalculo(row): void {
    this.isLoading = true;
    this.storeCalculo = new MatTableDataSource();
    this.selection.select(row);
    axios.get(this.apiUrl + 'calculo/buscaPorContrato?matricula=' + row.matricula).then((response) => {
      if (response && response.data) {
        this.storeCalculo.data = response.data;
        this.isLoading = false;
      }
    }).catch((error) => {
      console.log(error);
      this.isLoading = false;
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<any>(allowMultiSelect, initialSelection);
    this.storeFuncionario.filterPredicate = (data: Pessoa, filter) => {
      return !filter || data.nome.toLowerCase().includes(filter.toLowerCase());
    }
    this.listar();
  }
}



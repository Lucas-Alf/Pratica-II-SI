import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Incidencia } from './incidencia';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-incidencia',
  templateUrl: './incidencia.component.html',
  styleUrls: ['./incidencia.component.css']
})
export class IncidenciaComponent implements OnInit {

  apiUrl: string;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['id', 'descricao'];
  storeIncidencia = new MatTableDataSource();

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeIncidencia.filter = filterValue;
  }

  ngOnInit(): void {
    this.storeIncidencia.filterPredicate = (data: Incidencia, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.loaderService.show();
    axios.get(this.apiUrl + 'incidencia/all').then((response) => {
      if (response && response.data) {
        this.storeIncidencia.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }
}

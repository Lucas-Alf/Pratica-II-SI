import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Evento } from './evento';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';

@Component({
  selector: 'app-evento',
  templateUrl: './evento.component.html',
  styleUrls: ['./evento.component.css']
})
export class EventoComponent implements OnInit {

  apiUrl: string;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['id', 'descricao', 'tipo', 'incidencia', 'automatico'];
  storeEvento = new MatTableDataSource();

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeEvento.filter = filterValue;
  }

  ngOnInit(): void {
    this.storeEvento.filterPredicate = (data: Evento, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.loaderService.show();
    axios.get(this.apiUrl + 'evento/all').then((response) => {
      if (response && response.data) {
        this.storeEvento.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }
}
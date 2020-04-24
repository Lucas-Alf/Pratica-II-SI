import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Evento } from '../evento';

export interface Incidencia {
  id: number;
  descricao: string
}
export interface Rotina {
  id: number;
  descricao: string
}

@Component({
  selector: 'app-evento-modal',
  templateUrl: './evento-modal.component.html',
  styleUrls: ['./evento-modal.component.css']
})
export class EventoModalComponent implements OnInit {
  apiUrl: string;
  codigoHabilitado: boolean;
  codigo: number;
  descricao: string;
  tipo: string;
  automatico: boolean;
  incidenciaId: number;
  rotinacalculoId: number;

  incidencias:Incidencia[];
  rotinas:Rotina[];
  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<EventoModalComponent>,
    private snackBar: MatSnackBar,
      private loaderService: LoaderService,
    private constant: ConstantsService
  ) { 
    this.apiUrl = this.constant.apiUrl;
    this.listarIncids();
    this.listarRotinas();
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Evento = { id: this.codigo, descricao: this.descricao, tipo: this.tipo, automatico: this.automatico, incidenciaId: { id: this.incidenciaId,descricao:'' }, rotinacalculoId: { id: this.rotinacalculoId,descricao:'' }};
    this.data.component.salvar(this.data.action, dados);
  }

  listarIncids(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'incidencia/all').then((response) => {
      if (response && response.data) {
        this.incidencias = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarRotinas(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'RotinaCalculo/all').then((response) => {
      if (response && response.data) {
        this.rotinas = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.codigo = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.tipo = this.data.info.tipo;
      this.automatico = this.data.info.automatico;
      this.incidenciaId = this.data.info.incidenciaId.id;
      this.rotinacalculoId = this.data.info.rotinacalculoId.id;
      this.codigoHabilitado = false;
    } else {
      this.codigoHabilitado = true;
    }
  }

}

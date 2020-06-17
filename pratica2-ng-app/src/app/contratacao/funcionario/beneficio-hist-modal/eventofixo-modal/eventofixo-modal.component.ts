import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EventoFixo } from '../../eventofixo';
import { Evento } from 'src/app/folhapagamento/evento/evento';
import { MatSnackBar } from '@angular/material/snack-bar';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';

@Component({
  selector: 'app-eventofixo-modal',
  templateUrl: './eventofixo-modal.component.html',
  styleUrls: ['./eventofixo-modal.component.css']
})
export class EventoFixoModalComponent implements OnInit {

  id: number;
  contratoMatricula: number;
  dataFinal: Date;
  dataInicial: Date;
  evento: number;
  referencia: number;
  valor: number;

  apiUrl: string;
  eventos: Evento[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    private snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<EventoFixoModalComponent>,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarEventos();
  }

  listarEventos(): void {
    axios.get(this.apiUrl + 'evento/all').then((response) => {
      if (response && response.data) {
        this.eventos = response.data;
      }
    }).catch((error) => {
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: EventoFixo = {
      id: this.id,
      contratoMatricula: this.contratoMatricula,
      dataFinal: this.dataFinal,
      dataInicial: this.dataInicial,
      evento: { id: this.evento, automatico: null, descricao: null, incidenciaId: null, incidenciasAtingidas: null, percentual: null, rotinacalculoId: null, tipo: null },
      referencia: this.referencia,
      valor: this.valor
    };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.contratoMatricula = this.data.info.contratoMatricula;
      this.dataFinal = this.data.info.dataFinal;
      this.dataInicial = this.data.info.dataInicial;
      if (this.data.info.evento) {
        this.evento = this.data.info.evento.id;
      }
      this.referencia = this.data.info.referencia;
      this.valor = this.data.info.valor;
    }
  }
}

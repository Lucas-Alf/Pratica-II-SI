import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { EventoVariavel } from '../eventoVariavel';
import { Contrato } from 'src/app/contratacao/funcionario/contrato';
import { Evento } from '../../evento/evento';
import axios from 'axios';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'app-evento-variavel-modal',
  templateUrl: './evento-variavel-modal.component.html',
  styleUrls: ['./evento-variavel-modal.component.css']
})
export class EventoVariavelModalComponent implements OnInit {
  apiUrl: string;
  id: number;
  contratoMatricula: number;
  dataTemp: Date;
  evento: number;
  referencia: number;
  valor: number;

  eventos: Evento[];
  contratos: Contrato[];


  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<EventoVariavelModalComponent>,
    private snackBar: MatSnackBar,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarEventos();
    this.listarContratos();
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: EventoVariavel = {
      id: this.id,
      contratoMatricula: { matricula: this.contratoMatricula, situacao: null, dataadmissao: null, datademissao: null, departamentoid: null, horastrabalho: null, pessoa: null, regimetrabalho: null },
      data: this.dataTemp,
      evento: { id: this.evento, automatico: null, descricao: null, incidenciaId: null, incidenciasAtingidas: null, percentual: null, rotinacalculoId: null, tipo: null },
      referencia: this.referencia,
      valor: this.valor
    };
    this.data.component.salvar(this.data.action, dados);
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

  listarContratos(): void {
    axios.get(this.apiUrl + 'contrato/all').then((response) => {
      if (response && response.data) {
        this.contratos = response.data;
      }
    }).catch((error) => {
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.contratoMatricula = this.data.info.contratoMatricula.matricula;
      this.dataTemp = this.data.info.data;
      this.evento = this.data.info.evento.id;
      this.referencia = this.data.info.referencia;
      this.valor = this.data.info.valor;
    }
  }
}

import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Evento } from '../evento';

@Component({
  selector: 'app-evento-modal',
  templateUrl: './evento-modal.component.html',
  styleUrls: ['./evento-modal.component.css']
})
export class EventoModalComponent implements OnInit {
  codigoHabilitado: boolean;
  codigo: number;
  descricao: string;
  tipo: string;
  automatico: boolean;
  incidenciaId: {
    id: number;
    descricao: string
  }
  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<EventoModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) {this.automatico = false }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Evento = { id: this.codigo, descricao: this.descricao ,tipo: this.tipo, automatico: this.automatico, incidenciaId: this.incidenciaId};
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.codigo = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.codigoHabilitado = false;
    } else {
      this.codigoHabilitado = true;
    }
  }

}

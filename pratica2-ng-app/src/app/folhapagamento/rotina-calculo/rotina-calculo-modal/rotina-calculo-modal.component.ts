import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { RotinaCalculo } from '../RotinaCalculo';

@Component({
  selector: 'app-rotina-calculo-modal',
  templateUrl: './rotina-calculo-modal.component.html',
  styleUrls: ['./rotina-calculo-modal.component.css']
})
export class RotinaCalculoModalComponent implements OnInit {
  codigoHabilitado: boolean;
  codigo: number;
  descricao: string;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<RotinaCalculoModalComponent>
  ) { }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: RotinaCalculo = { id: this.codigo, descricao: this.descricao };
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

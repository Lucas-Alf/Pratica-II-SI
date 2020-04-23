import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PeriodoCalculo } from '../PeriodoCalculo';

@Component({
  selector: 'app-periodo-calculo-modal',
  templateUrl: './periodo-calculo-modal.component.html',
  styleUrls: ['./periodo-calculo-modal.component.css']
})
export class PeriodoCalculoModalComponent implements OnInit {
  codigoHabilitado: boolean;
  id: number;
  dataInicial: Date;
  dataFinal: Date;
  execucao: number;

  execucoes;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<PeriodoCalculoModalComponent>
  ) { }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: PeriodoCalculo = { id: this.id, dataInicial: this.dataInicial, dataFinal: this.dataFinal, execucao: this.execucao };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    this.execucoes = [
      { id: 1, descricao: 'Mensal' },
      { id: 2, descricao: 'Férias' },
      { id: 3, descricao: 'Adt. Décimo Terceiro' },
      { id: 4, descricao: '13º Final' },
      { id: 5, descricao: 'Desligamento' },
      { id: 6, descricao: 'Folha Avulsa' },
      { id: 7, descricao: 'Adiantamento Quinzenal' }
    ];
    if (this.data.info) {
      this.id = this.data.info.id;
      this.dataInicial = this.data.info.dataInicial;
      this.dataFinal = this.data.info.dataFinal;
      this.execucao = this.data.info.execucao;
    }
  }
}

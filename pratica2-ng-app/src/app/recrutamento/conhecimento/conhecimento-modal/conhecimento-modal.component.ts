import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Conhecimento } from '../conhecimento';

@Component({
  selector: 'app-conhecimento-modal',
  templateUrl: './conhecimento-modal.component.html',
  styleUrls: ['./conhecimento-modal.component.css'],
})

export class ConhecimentoModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  nome: string;
  especializacao: string;
  formacao: string;

  formacoes: any = [
    { descricao: 'Ensino Fundamental Completo' },
    { descricao: 'Ensino Médio Completo' },
    { descricao: 'Ensino Superior Completo' },
    { descricao: 'Ensino Técnico' },
  ];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<ConhecimentoModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Conhecimento = { id: this.id, nome: this.nome, especializacao: this.especializacao, formacao: this.formacao };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.nome = this.data.info.nome;
      this.especializacao = this.data.info.especializacao;
      this.formacao = this.data.info.formacao;
    }
  }

}
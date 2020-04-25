import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { HabilidadeAtitude } from '../habilidadeAtitude';

@Component({
  selector: 'app-habilidadeAtitude-modal',
  templateUrl: './habilidadeAtitude-modal.component.html',
  styleUrls: ['./habilidadeAtitude-modal.component.css'],
})

export class HabilidadeAtitudeModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  descricao: string;
  tipo: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<HabilidadeAtitudeModalComponent>,
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
    const dados: HabilidadeAtitude = { id: this.id, descricao: this.descricao, tipo: this.tipo };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.tipo = this.data.info.tipo;
    }
  }

}
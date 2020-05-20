import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Beneficio } from '../beneficio';
import * as moment from 'moment'

@Component({
  selector: 'app-beneficio-modal',
  templateUrl: './beneficio-modal.component.html',
  styleUrls: ['./beneficio-modal.component.css'],
})

export class BeneficioModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  descricao: string;
  dataModal: Date;
  valor: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<BeneficioModalComponent>,
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
    const dados: Beneficio = { id: this.id, descricao: this.descricao, data: this.dataModal, valor: this.valor };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.dataModal = moment(this.data.info.data).toDate();
      this.valor = this.data.info.valor;
    }
  }

}
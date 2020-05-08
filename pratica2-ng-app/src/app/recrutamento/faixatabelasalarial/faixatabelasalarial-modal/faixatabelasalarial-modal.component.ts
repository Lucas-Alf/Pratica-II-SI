import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { FaixaTabelaSalarial } from '../faixatabelasalarial';
import { TabelaSalarial } from '../../tabelasalarial/tabelasalarial';

@Component({
  selector: 'app-faixatabelasalarial-modal',
  templateUrl: './faixatabelasalarial-modal.component.html',
  styleUrls: ['./faixatabelasalarial-modal.component.css'],
})

export class FaixaTabelaSalarialModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  nivel: number;
  percentual: number;
  tabelaSalarialId: number;

  tabelasalariais: TabelaSalarial[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<FaixaTabelaSalarialModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarTabelaSalarial();
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: FaixaTabelaSalarial = { id: this.id, nivel: this.nivel, percentual: this.percentual, tabelasalarial: { id: this.tabelaSalarialId, descricao: '', valorbase: 0 } };
    this.data.component.salvar(this.data.action, dados);
  }

  listarTabelaSalarial(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'TabelaSalarial/all').then((response) => {
      if (response && response.data) {
        this.tabelasalariais = response.data;
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
      this.id = this.data.info.id;
      this.nivel = this.data.info.nivel;
      this.percentual = this.data.info.percentual;
      this.tabelaSalarialId = this.data.info.tabelasalarial.id;
    }
  }

}
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Vaga } from '../vaga';

export interface Cargo {
  id: number;
  descricao: string;
  cboid: {
    id: string;
  }
}

@Component({
  selector: 'app-vaga-modal',
  templateUrl: './vaga-modal.component.html',
  styleUrls: ['./vaga-modal.component.css'],
})

export class VagaModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  descricao: string;
  quantidade: number;
  prazo: Date;
  tipo: string;
  cargoid: number;

  cargos: Cargo[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<VagaModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService,
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarCargo();
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Vaga = { id: this.id, descricao: this.descricao, quantidade: this.quantidade, prazo: this.prazo, tipo: this.tipo, cargoid: { id: this.cargoid, descricao: '' } };
    this.data.component.salvar(this.data.action, dados);
  }

  listarCargo(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'cargo/all').then((response) => {
      if (response && response.data) {
        this.cargos = response.data;
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
      this.descricao = this.data.info.descricao;
      this.quantidade = this.data.info.quantidade;
      this.prazo = this.data.info.prazo;
      this.tipo = this.data.info.tipo;
      this.cargoid = this.data.info.cargoid.id;
    }
  }

}
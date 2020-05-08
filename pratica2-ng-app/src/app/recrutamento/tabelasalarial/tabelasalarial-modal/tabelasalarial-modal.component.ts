import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { TabelaSalarial } from '../tabelasalarial';

@Component({
  selector: 'app-tabelasalarial-modal',
  templateUrl: './tabelasalarial-modal.component.html',
  styleUrls: ['./tabelasalarial-modal.component.css'],
})

export class TabelaSalarialModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  descricao: string;
  valorbase: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<TabelaSalarialModalComponent>,
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
    const dados: TabelaSalarial = { id: this.id, descricao: this.descricao, valorbase: this.valorbase };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.valorbase = this.data.info.valorbase;
    }
  }

}
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { VagaPessoa } from '../vagapessoa';


@Component({
  selector: 'app-recrutamentoInterno-modal',
  templateUrl: './recrutamentoInterno-modal.component.html',
  styleUrls: ['./recrutamentoInterno-modal.component.css'],
})

export class RecrutamentoInternoModalComponent implements OnInit {

  apiUrl: string;

  vagaid: number;
  descricao: string;
  cbo: string;
  cargo: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<RecrutamentoInternoModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  close(): void {
    this.dialogRef.close();
  }

  salvarRecrutInterno(): void {
    const dados: VagaPessoa = { id: 0, cpf: JSON.parse(localStorage.getItem('userData')).pessoa.cpf, vagaid: this.vagaid };
    this.data.component.salvar('Incluir', dados);
  }

  ngOnInit(): void {
    if (this.data) {
      this.vagaid = this.data.row.id;
      this.descricao = this.data.row.descricao;
      this.cbo = this.data.row.cargoid.cboid.id;
      this.cargo = this.data.row.cargoid.descricao;
    }
  }

}
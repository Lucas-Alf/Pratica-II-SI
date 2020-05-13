import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { VagaPessoa } from '../../recrutamentoInterno/vagapessoa';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Endereco } from 'src/app/contratacao/endereco/endereco';

@Component({
  selector: 'app-recrutamentoExterno-modal',
  templateUrl: './recrutamentoExterno-modal.component.html',
  styleUrls: ['./recrutamentoExterno-modal.component.css'],
})

export class RecrutamentoExternoModalComponent implements OnInit {

  apiUrl: string;

  vagaid: number;
  descricao: string;

  cpf: string;
  nome: string;
  sexo: string;
  datanascimento: Date;
  enderecoid: number;
  numero: number;

  enderecos: Endereco[];

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<RecrutamentoExternoModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService,
    private _formBuilder: FormBuilder
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarEndereco();
  }

  close(): void {
    this.dialogRef.close();
  }

  listarEndereco(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'endereco/all').then((response) => {
      if (response && response.data) {
        this.enderecos = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  /*salvarRecrutExterno(): void {
    const dados: VagaPessoa = { id: 0, cpf: JSON.parse(localStorage.getItem('userData')).pessoa.cpf, vagaid: this.vagaid };
    this.data.component.salvar('Incluir', dados);
  }*/

  ngOnInit(): void {
    if (this.data) {
      this.vagaid = this.data.row.id;
      /*this.descricao = this.data.row.descricao;
      this.cbo = this.data.row.cargoid.cboid.id;
      this.cargo = this.data.row.cargoid.descricao;*/
    }
    this.firstFormGroup = this._formBuilder.group({
      group1CPF: ['', Validators.required],
      group1Nome: ['', Validators.required],
      group1DataNascimento: ['', Validators.required],
      group1Sexo: ['', Validators.required],
      group1Endereco: ['', Validators.required],
      group1Numero: ['', Validators.required],
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

}
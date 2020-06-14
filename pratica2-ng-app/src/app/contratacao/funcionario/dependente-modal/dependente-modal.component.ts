import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Pessoa } from '../pessoa';
import { Endereco } from '../../endereco/endereco';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';

@Component({
  selector: 'app-dependente-modal',
  templateUrl: './dependente-modal.component.html',
  styleUrls: ['./dependente-modal.component.css']
})
export class DependenteModalComponent implements OnInit {
  apiUrl: string;

  cpf: string;
  nome: string;
  sexo: string;
  datanascimento: Date;
  enderecoid: number;
  rg: string;
  nomemae: string;



  enderecos: Endereco[];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef2: MatDialogRef<DependenteModalComponent>, private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService,
    private _formBuilder: FormBuilder
  ) { this.apiUrl = this.constant.apiUrl; this.listarEndereco(); }


  close(): void {
    this.dialogRef2.close();
    //this.data.component.teste();
    this.data.component.listarDependente();
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

  save(): void {
    //var testhis: Pessoa = this.data;
    //var teste = this.paisnascimentoid ? { id: this.paisnascimentoid, nome: "" } : null;
    const dados: Pessoa = {
      cpf: this.cpf,
      rg: this.rg,
      nome: this.nome,
      sexo: this.sexo,
      datanascimento: this.datanascimento,
      paisnascimentoid: null,
      telefonecelular: null,
      telefonefixo: null,
      pispasep: null,
      pisexpedicao: null,
      cnhnumero: null,
      cnhdata: null,
      chntipo: null,
      ctpsnumero: null,
      ctpsserie: null,
      ctpsuf: null,
      nomepai: null,
      nomemae: this.nomemae,
      tituloeleitornumero: null,
      tituloeleitoruf: null,
      tituloeleitorzona: null,
      tituloeleitorsecao: null,
      certificadoreservista: null,
      enderecoid: {
        id: this.enderecoid,
        logradouro: '',
        bairro: '',
        cep: '',
        cidadeid: null
      },
      email: null,
      numero: null,
      dependente: null
    };
    axios.post(this.constant.apiUrl + 'pessoa/' + this.data.action, dados).then((response) => {
      if (response && response.data) {  
        this.loaderService.hide();      
        this.close();
        this.data.component.listarDependentes();
        
      } else {
        this.loaderService.hide();
        this.snackBar.open('Ocorreu um erro ao salvar. 😬', null, { duration: 5000 });
      }
    }).catch((error) => {
      this.loaderService.hide();
      if (error.response) {
        console.error(error.response.data.message);
        this.snackBar.open(error.response.data.message, null, { duration: 5000 });
      } else {
        this.snackBar.open('Ocorreu um erro ao salvar. 😬', null, { duration: 5000 });
      }
    });
    //this.data.component.salvar(this.data.action, dados);
  }


  ngOnInit(): void {
    if (this.data.info) {
      this.cpf = this.data.info.cpf;
      this.nome = this.data.info.nome;
      this.sexo = this.data.info.sexo;
      this.rg = this.data.info.rg;
      this.nomemae = this.data.info.nomemae;
      this.datanascimento = this.data.info.datanascimento;
      this.enderecoid = this.data.info.enderecoid.id;
    }
  }

}

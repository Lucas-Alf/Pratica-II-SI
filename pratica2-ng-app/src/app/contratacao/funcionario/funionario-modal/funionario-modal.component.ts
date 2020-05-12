import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Pessoa } from '../pessoa';
import { Pais } from '../pais';
import { Endereco } from '../../endereco/endereco';

@Component({
  selector: 'app-funionario-modal',
  templateUrl: './funionario-modal.component.html',
  styleUrls: ['./funionario-modal.component.css']
})
export class FunionarioModalComponent implements OnInit {
  apiUrl: string;

  cpf: string;
  rg: string;
  nome: string;
  paisnascimentoid: number;
  telefonecelular: number;
  telefonefixo: number;
  pispasep: number;
  pisexpedicao: Date;
  cnhnumero: number;
  cnhdata: Date;
  chntipo: string;
  ctpsnumero: number;
  ctpsserie: number;
  ctpsuf: number;
  nomepai: string;
  nomemae: string;
  tituloeleitornumero: number;
  tituloeleitoruf: string;
  tituloeleitorzona: number;
  tituloeleitorsecao: string;
  certificadoreservista: number;
  enderecoid: number;
  email: string;
  numero: number;

  paises: Pais[];
  enderecos: Endereco[];


  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<FunionarioModalComponent>, private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) { this.apiUrl = this.constant.apiUrl; this.listarPais();this.listarEndereco(); }

  close(): void {
    this.dialogRef.close();
  }


  save(): void {
    var testhis: Pessoa = this.data;
    // const dados: Pessoa = {
    //   id: this.codigo,
    //   descricao: this.descricao,
    //   tipo: this.tipo,
    //   automatico: this.automatico,
    //   incidenciaId: {
    //     id: this.incidenciaId, descricao: ''
    //   },
    //   rotinacalculoId: {
    //     id: this.rotinacalculoId, descricao: ''
    //   },
    //   incidenciasAtingidas: this.incidenciasAtingidas.map((x) => ({ incidencia: x }))
    // };
    this.data.component.salvar(this.data.action, testhis);
  }


  listarPais(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'pais/all').then((response) => {
      if (response && response.data) {
        this.paises = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
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

  ngOnInit(): void {
    if (this.data.info) {
      this.cpf = this.data.info.cpf;
      this.nome = this.data.info.nome;
      this.paisnascimentoid = this.data.info.paisnascimentoid.id;
    }
  }

}

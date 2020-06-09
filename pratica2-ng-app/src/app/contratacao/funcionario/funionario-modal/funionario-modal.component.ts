import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Pessoa } from '../pessoa';
import { Pais } from '../pais';
import { Endereco } from '../../endereco/endereco';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { Departamento } from 'src/app/recrutamento/cargo/cargo-modal/cargo-modal.component';
import { Contrato } from '../contrato';
import { DependenteModalComponent } from '../dependente-modal/dependente-modal.component';
import { find } from 'rxjs/operators';

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
  sexo: string;
  datanascimento: Date;
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
  ctpsuf: string;
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

  matricula: number;
  situacao: string;
  dataadmissao: Date;
  regimetrabalho: string;
  horastrabalho: number;
  departamentoid: number;
  datademissao: Date;

  paises: Pais[];
  enderecos: Endereco[];
  departamentos: Departamento[];
  validador: FormGroup;
  dialogRef2: MatDialogRef<DependenteModalComponent, any>;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<FunionarioModalComponent>, private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService,
    private _formBuilder: FormBuilder,
    public dialog: MatDialog
  ) { this.apiUrl = this.constant.apiUrl; this.listarPais(); this.listarEndereco(); this.listarDepartamento(); }

  displayedColumns: string[] = ['select', 'situacao', 'matricula', 'dataadmissao', 'regimetrabalho', 'horastrabalho', 'departamentoid', 'datademissao'];
  storeContrato = new MatTableDataSource();
  selection = new SelectionModel<Contrato>();

  close(): void {
    this.dialogRef.close();
  }
  listarDepartamento(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'departamento/all').then((response) => {
      if (response && response.data) {
        this.departamentos = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarContrato(cpf): void {
    this.loaderService.show();//this.apiUrl + 'pessoa/delete/' + this.selection.selected[0].cpf
    // calculo/buscaPorContrato?matricula=
    axios.get(this.apiUrl + 'contrato/findByCpf?cpf=' + cpf).then((response) => {
      if (response && response.data) {
        this.storeContrato.data = response.data;
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
    var teste = this.paisnascimentoid ? { id: this.paisnascimentoid, nome: "" } : null;
    const dados: Pessoa = {
      cpf: this.cpf,
      rg: this.rg,
      nome: this.nome,
      sexo: this.sexo,
      datanascimento: this.datanascimento,
      paisnascimentoid: teste,
      telefonecelular: this.telefonecelular,
      telefonefixo: this.telefonefixo,
      pispasep: this.pispasep,
      pisexpedicao: this.pisexpedicao,
      cnhnumero: this.cnhnumero,
      cnhdata: this.cnhdata,
      chntipo: this.chntipo,
      ctpsnumero: this.ctpsnumero,
      ctpsserie: this.ctpsserie,
      ctpsuf: this.ctpsuf,
      nomepai: this.nomepai,
      nomemae: this.nomemae,
      tituloeleitornumero: this.tituloeleitornumero,
      tituloeleitoruf: this.tituloeleitoruf,
      tituloeleitorzona: this.tituloeleitorzona,
      tituloeleitorsecao: this.tituloeleitorsecao,
      certificadoreservista: this.certificadoreservista,
      enderecoid: {
        id: this.enderecoid,
        logradouro: '',
        bairro: '',
        cep: '',
        cidadeid: null
      },
      email: this.email,
      numero: this.numero,
    };
    this.data.component.salvar(this.data.action, dados);
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

  gravar(): void {
    var teste = this.paisnascimentoid ? { id: this.paisnascimentoid, nome: "" } : null;
    const pessoa: Pessoa = {
      cpf: this.cpf,
      rg: this.rg,
      nome: this.nome,
      sexo: this.sexo,
      datanascimento: this.datanascimento,
      paisnascimentoid: teste,
      telefonecelular: this.telefonecelular,
      telefonefixo: this.telefonefixo,
      pispasep: this.pispasep,
      pisexpedicao: this.pisexpedicao,
      cnhnumero: this.cnhnumero,
      cnhdata: this.cnhdata,
      chntipo: this.chntipo,
      ctpsnumero: this.ctpsnumero,
      ctpsserie: this.ctpsserie,
      ctpsuf: this.ctpsuf,
      nomepai: this.nomepai,
      nomemae: this.nomemae,
      tituloeleitornumero: this.tituloeleitornumero,
      tituloeleitoruf: this.tituloeleitoruf,
      tituloeleitorzona: this.tituloeleitorzona,
      tituloeleitorsecao: this.tituloeleitorsecao,
      certificadoreservista: this.certificadoreservista,
      enderecoid: {
        id: this.enderecoid,
        logradouro: '',
        bairro: '',
        cep: '',
        cidadeid: null
      },
      email: this.email,
      numero: this.numero,
    };
    const dados: Contrato = {
      matricula: this.matricula,
      situacao: parseInt(this.situacao),
      dataadmissao: this.dataadmissao,
      regimetrabalho: parseInt(this.regimetrabalho),
      horastrabalho: this.horastrabalho,
      datademissao: this.datademissao,
      departamentoid: { id: this.departamentoid, nome: '', descricao: '' },
      pessoa: pessoa,
    };
    this.salvarContrato(dados);
  }

  salvarContrato(data: Contrato): void {
    this.loaderService.show();
    axios.post(this.constant.apiUrl + 'contrato/' + (data.matricula ? 'Alterar' : 'Incluir'), data).then((response) => {
      if (response && response.data) {
        this.loaderService.hide();
        this.listarContrato(this.cpf);
        this.cancelar();
      } else {
        this.loaderService.hide();
        this.snackBar.open('Ocorreu um erro ao salvar o contrato. 😬', null, { duration: 5000 });
      }
    }).catch((error) => {
      this.loaderService.hide();
      if (error.response) {
        console.error(error.response.data.message);
        this.snackBar.open(error.response.data.message, null, { duration: 5000 });
      } else {
        this.snackBar.open('Ocorreu um erro ao salvar o contrato. 😬', null, { duration: 5000 });
      }
    });
  }
  alterar(): void {
    setTimeout(() => {
      var r = this.selection.selected[0];
      this.situacao = r.situacao.toString();
      this.dataadmissao = r.dataadmissao;
      this.regimetrabalho = r.regimetrabalho.toString();
      this.horastrabalho = r.horastrabalho;
      this.departamentoid = r.departamentoid.id;
      this.matricula = r.matricula;

      console.log('ALTEROU ' + this.matricula);
    }, 300);
  }
  cancelar(): void {
    this.situacao = null;
    this.dataadmissao = null;
    this.regimetrabalho = null;
    this.horastrabalho = null;
    this.departamentoid = null;
    this.datademissao = null;
    this.matricula = null;
    this.selection.clear();
    console.log('CANCELOU  ' + this.matricula);
  }
  incluirDependente(): void {
    this.dialogRef2 = this.dialog.open(DependenteModalComponent, { data: { action: 'Incluir', component: this } });
  }
  verificaDataDem():void{

  }
  ngOnInit(): void {
    console.log("SEXO " + this.sexo + " -" + this.data.info.sexo);
    if (this.data.info) {
      this.listarContrato(this.data.info.cpf);
      this.cpf = this.data.info.cpf;
      this.nome = this.data.info.nome;
      this.paisnascimentoid = this.data.info.paisnascimentoid ? this.data.info.paisnascimentoid.id : null;
      this.rg = this.data.info.rg;
      this.sexo = this.data.info.sexo;
      this.datanascimento = this.data.info.datanascimento;
      this.telefonecelular = this.data.info.telefonecelular;
      this.telefonefixo = this.data.info.telefonefixo;
      this.pispasep = this.data.info.pispasep;
      this.pisexpedicao = this.data.info.pisexpedicao;
      this.cnhnumero = this.data.info.cnhnumero;
      this.cnhdata = this.data.info.cnhdata;
      this.chntipo = this.data.info.chntipo;
      this.ctpsnumero = this.data.info.ctpsnumero;
      this.ctpsserie = this.data.info.ctpsserie;
      this.ctpsuf = this.data.info.ctpsuf;
      this.nomepai = this.data.info.nomepai;
      this.nomemae = this.data.info.nomemae;
      this.tituloeleitornumero = this.data.info.tituloeleitornumero;
      this.tituloeleitoruf = this.data.info.tituloeleitoruf;
      this.tituloeleitorzona = this.data.info.tituloeleitorzona;
      this.tituloeleitorsecao = this.data.info.tituloeleitorsecao;
      this.certificadoreservista = this.data.info.certificadoreservista;
      this.enderecoid = this.data.info.enderecoid.id;
      this.email = this.data.info.email;
      this.numero = this.data.info.numero;

      // this.validador = new FormGroup({
      //   cpfteste: new FormControl('', Validators.required)
      // });
      // this.validador = this._formBuilder.group({
      //   cpfteste: ['', Validators.required],
      //   nome: ['', Validators.required],
      //   enderecoid: ['', Validators.required],
      //   sexo: ['', Validators.required],
      // });
    }

  }

}

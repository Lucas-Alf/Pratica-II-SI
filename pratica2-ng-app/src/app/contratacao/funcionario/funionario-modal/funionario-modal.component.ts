import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
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
import { find, startWith, map } from 'rxjs/operators';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { Observable } from 'rxjs';
import { Dependente } from '../dependente';
import { CargoHistModalComponent } from '../cargo-hist-modal/cargo-hist-modal.component';
import { BeneficioHistModalComponent } from '../beneficio-hist-modal/beneficio-hist-modal.component';
import { Conhecimento } from 'src/app/recrutamento/conhecimento/conhecimento';
import { HabilidadeAtitude } from 'src/app/recrutamento/habilidadeAtitude/habilidadeAtitude';

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
  pispasep: string;
  pisexpedicao: Date;
  cnhnumero: string;
  cnhdata: Date;
  chntipo: string;
  ctpsnumero: string;
  ctpsserie: number;
  ctpsuf: string;
  nomepai: string;
  nomemae: string;
  tituloeleitornumero: string;
  tituloeleitoruf: string;
  tituloeleitorzona: number;
  tituloeleitorsecao: string;
  certificadoreservista: string;
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

  dependente: Pessoa;
  habilidadesAtitudes: HabilidadeAtitude[];
  paises: Pais[];
  enderecos: Endereco[];
  departamentos: Departamento[];
  //dependentes: Pessoa[];
  validador: FormGroup;
  dialogRef2: MatDialogRef<DependenteModalComponent, any>;
  dialogCargo: MatDialogRef<CargoHistModalComponent, any>;
  dialogBeneficio: MatDialogRef<BeneficioHistModalComponent, any>;
  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  DependentesCtrl = new FormControl();
  filteredDependentes: Observable<Dependente[]>;
  dependentes: any[] = [];
  pessoas: any[] = [];

  pessoaConhecimentos: any[] = [];
  removableConhecimentoPessoa = true;
  ConhecimentoPessoaCtrl = new FormControl();
  filteredConhecimentosPessoa: Observable<Conhecimento[]>;
  separatorKeysCodesConhecimentoPessoa: number[] = [ENTER, COMMA];
  conhecimentos: Conhecimento[];

  visiblePessoaHabilidadeAtitude = true;
  selectablePessoaHabilidadeAtitude = true;
  removablePessoaHabilidadeAtitude = true;
  separatorKeysCodesPessoaHabilidadeAtitude: number[] = [ENTER, COMMA];
  PessoaHabilidadeAtitudeCtrl = new FormControl();
  filteredPessoaHabilidadeAtitude: Observable<HabilidadeAtitude[]>;
  pessoaHabilidadesAtitudes: any[] = [];
  //conhecimentoId: number; //remover depois

  @ViewChild('fruitInputConhecimento') fruitInputConhecimento: ElementRef<HTMLInputElement>;
  @ViewChild('fruitInputHabilidadeAtitude') fruitInputHabilidadeAtitude: ElementRef<HTMLInputElement>;
  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<FunionarioModalComponent>, private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService,
    private _formBuilder: FormBuilder,
    public dialog: MatDialog
  ) { this.apiUrl = this.constant.apiUrl; this.listarPais(); this.listarEndereco(); this.listarDepartamento();
     this.listarDependentes();this.listarConhecimentosPessoa(), this.listarHabilidadesAtitudes() }

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
        //this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarDependentes(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'pessoa/findDepedente').then((response) => {
      if (response && response.data) {
        this.pessoas = response.data;
        this.filteredDependentes = this.DependentesCtrl.valueChanges.pipe(
          startWith(null),
          map((item: string | null) => item ? this._filter(item) : this.pessoas.slice()));
      }
      this.listarDependente();
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarDependente(): void {
    axios.get(this.apiUrl + 'dependente/findDepedentesCpf?cpf=' + (this.cpf || '0')).then((response) => {
      if (response && response.data) {
        this.dependentes = response.data.map((x) => (x.dependentecpf));

        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  private _filter(value: any): Pessoa[] {
    if (value && value.nome) {
      value = value.nome;
    }
    if (value) {
      const filterValue = value.toLowerCase();
      return this.pessoas.filter(item => item.nome.toLowerCase().includes(filterValue));
    }
  }
  listarContrato(cpf, mascaraLoad): void {
    // this.loaderService.show();//this.apiUrl + 'pessoa/delete/' + this.selection.selected[0].cpf
    // calculo/buscaPorContrato?matricula=
    axios.get(this.apiUrl + 'contrato/findByCpf?cpf=' + cpf).then((response) => {
      if (response && response.data) {
        this.storeContrato.data = response.data;
        if (mascaraLoad) this.loaderService.hide();
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
      ativo: true,
      dependente: this.dependentes.map((x) => ({ dependentecpf: x })),
      pessoaConhecimentos: this.pessoaConhecimentos.map((x) => ({ conhecimento: x })),
     // pessoaIdiomas: this.pessoaIdiomas.map((x) => ({ idioma: x })),
      pessoaHabilidadesAtitudes: this.pessoaHabilidadesAtitudes.map((x) => ({ habilidadeAtitude: x }))
    };
    this.data.component.salvar(this.data.action, dados);
  }


  listarPais(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'pais/all').then((response) => {
      if (response && response.data) {
        this.paises = response.data;
        // this.loaderService.hide();
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
        //this.loaderService.hide();
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
      ativo: true,
      dependente: this.dependentes.map((x) => ({ dependentecpf: x })),
      pessoaConhecimentos: this.pessoaConhecimentos.map((x) => ({ conhecimento: x })),
      // pessoaIdiomas: this.pessoaIdiomas.map((x) => ({ idioma: x })),
       pessoaHabilidadesAtitudes: this.pessoaHabilidadesAtitudes.map((x) => ({ habilidadeAtitude: x }))
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
        this.listarContrato(this.cpf, true);
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
  }
  verificaDataDem(): void {

  }
  incluirDependente(): void {
    this.dialogRef2 = this.dialog.open(DependenteModalComponent, { data: { action: 'Incluir', component: this } });
  }
  incluirCargo(): void {
    if (this.selection.selected.length > 0) {
      this.dialogCargo = this.dialog.open(CargoHistModalComponent, { data: { action: 'Incluir', component: this, info: this.selection.selected[0] } });
    } else {
      this.snackBar.open('Selecione um contrato para adicionar o cargo. 🤦‍♂️', null, { duration: 5000 });
    }
  }
  incluirBeneficio(): void {
    if (this.selection.selected.length > 0) {
      this.dialogBeneficio = this.dialog.open(BeneficioHistModalComponent, { data: { action: 'Incluir', component: this, info: this.selection.selected[0] } });
    } else {
      this.snackBar.open('Selecione um contrato para adicionar o beneficio. 🤦‍♂️', null, { duration: 5000 });
    }
  }
  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      // this.incidenciasAtingidas.push(value.trim());
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.DependentesCtrl.setValue(null);
  }

  remove(item: Pessoa): void {
    const index = this.dependentes.indexOf(item);

    if (index >= 0) {
      this.dependentes.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (!this.dependentes.includes(event.option.value)) {
      this.dependentes.push(event.option.value);
    }
    this.fruitInput.nativeElement.value = '';
    this.DependentesCtrl.setValue(null);
  }
  listarConhecimentosPessoa(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'conhecimento/all').then((response) => {
      if (response && response.data) {
        this.conhecimentos = response.data;
        // if (this.conhecimentoId) {
        //   this.filteredConhecimentosPessoa = this.ConhecimentoPessoaCtrl.valueChanges.pipe(
        //     startWith(null),
        //     map((item: string | null) => item ? this._filterPessoaConhecimento(item) : this.conhecimentos.filter(x => x.id != this.conhecimentoId).slice()));
        // } else {
          this.filteredConhecimentosPessoa = this.ConhecimentoPessoaCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterPessoaConhecimento(item) : this.conhecimentos.slice()));
        // }
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  private _filterPessoaConhecimento(value: any): Conhecimento[] {
    if (value && value.nome) {
      value = value.nome;
    }
    if (value) {
      const filterValue = value.toLowerCase();
      // if (this.conhecimentoId) {
      //   return this.conhecimentos.filter(item => item.id != this.conhecimentoId && item.nome.toLowerCase().includes(filterValue));
      // } else {
        return this.conhecimentos.filter(item => item.nome.toLowerCase().includes(filterValue));
    //  }
    }
  }
  addConhecimentoPessoa(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    if ((value || '').trim()) {
      // this.incidenciasAtingidas.push(value.trim());
    }
    if (input) {
      input.value = '';
    }
    this.ConhecimentoPessoaCtrl.setValue(null);
  }

  removePessoaConhecimento(item: Conhecimento): void {
    const index = this.pessoaConhecimentos.indexOf(item);

    if (index >= 0) {
      this.pessoaConhecimentos.splice(index, 1);
    }
  }

  selectedPessoaConhecimento(event: MatAutocompleteSelectedEvent): void {
    if (!this.pessoaConhecimentos.includes(event.option.value)) {
      this.pessoaConhecimentos.push(event.option.value);
    }
    this.fruitInputConhecimento.nativeElement.value = '';
    this.ConhecimentoPessoaCtrl.setValue(null);
  }

  
  // Habilidades
  listarHabilidadesAtitudes(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'habilidadeAtitude/all').then((response) => {
      if (response && response.data) {
        this.habilidadesAtitudes = response.data;
        // if (this.habilidadeId) {
        //   this.filteredPessoaHabilidadeAtitude = this.PessoaHabilidadeAtitudeCtrl.valueChanges.pipe(
        //     startWith(null),
        //     map((item: string | null) => item ? this._filterPessoaHabilidadeAtitude(item) : this.habilidadesAtitudes.filter(x => x.id != this.habilidadeId).slice()));
        // } else {
          this.filteredPessoaHabilidadeAtitude = this.PessoaHabilidadeAtitudeCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterPessoaHabilidadeAtitude(item) : this.habilidadesAtitudes.slice()));
       // }
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  addPessoaHabilidadeAtitude(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    if ((value || '').trim()) {
      // this.incidenciasAtingidas.push(value.trim());
    }
    if (input) {
      input.value = '';
    }
    this.PessoaHabilidadeAtitudeCtrl.setValue(null);
  }

  removePessoaHabilidadeAtitude(item: HabilidadeAtitude): void {
    const index = this.pessoaHabilidadesAtitudes.indexOf(item);
    if (index >= 0) {
      this.pessoaHabilidadesAtitudes.splice(index, 1);
    }
  }

  selectedPessoaHabilidadeAtitude(event: MatAutocompleteSelectedEvent): void {
    if (!this.pessoaHabilidadesAtitudes.includes(event.option.value)) {
      this.pessoaHabilidadesAtitudes.push(event.option.value);
    }
    this.fruitInputHabilidadeAtitude.nativeElement.value = '';
    this.PessoaHabilidadeAtitudeCtrl.setValue(null);
  }

  private _filterPessoaHabilidadeAtitude(value: any): HabilidadeAtitude[] {
    if (value && value.descricao) {
      value = value.descricao;
    }
    if (value) {
      const filterValue = value.toLowerCase();
      // if (this.habilidadeId) {
      //   return this.habilidadesAtitudes.filter(item => item.id != this.habilidadeId && item.descricao.toLowerCase().includes(filterValue));
      // } else {
        return this.habilidadesAtitudes.filter(item => item.descricao.toLowerCase().includes(filterValue));
    //  }
    }
  }
  ngOnInit(): void {
    if (this.data.info) {
      this.listarContrato(this.data.info.cpf, false);
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
      this.pessoaConhecimentos = this.data.info.pessoaConhecimentos.map(x=>x.conhecimento);
      this.pessoaHabilidadesAtitudes = this.data.info.pessoaHabilidadesAtitudes.map(x=>x.habilidadeAtitude);

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

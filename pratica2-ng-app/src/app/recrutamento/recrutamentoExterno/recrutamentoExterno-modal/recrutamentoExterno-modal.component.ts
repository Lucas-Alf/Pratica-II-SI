import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { VagaPessoa } from '../../recrutamentoInterno/vagapessoa';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Endereco } from 'src/app/contratacao/endereco/endereco';
import { Conhecimento } from '../../conhecimento/conhecimento';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { HabilidadeAtitude } from '../../habilidadeAtitude/habilidadeAtitude';
import { Pessoa } from 'src/app/contratacao/funcionario/pessoa';

export interface Idioma {
  id: number;
  descricao: string;
}

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
  telefone: number;
  datanascimento: Date;
  enderecoid: number;
  numero: number;
  experienciaprofissional: string;
  idiomaId: number;
  nivel: string;

  enderecos: Endereco[];

  conhecimentoId: number;

  visibleConhecimentoPessoa = true;
  selectableConhecimentoPessoa = true;
  removableConhecimentoPessoa = true;
  separatorKeysCodesConhecimentoPessoa: number[] = [ENTER, COMMA];
  ConhecimentoPessoaCtrl = new FormControl();
  filteredConhecimentosPessoa: Observable<Conhecimento[]>;
  pessoaConhecimentos: any[] = [];

  conhecimentos: Conhecimento[];

  visiblePessoaIdioma = true;
  selectablePessoaIdioma = true;
  removablePessoaIdioma = true;
  separatorKeysCodesPessoaIdioma: number[] = [ENTER, COMMA];
  PessoaIdiomaCtrl = new FormControl();
  filteredPessoaIdioma: Observable<Idioma[]>;
  pessoaIdiomas: any[] = [];

  idiomas: Idioma[];

  habilidadeId: number;

  visiblePessoaHabilidadeAtitude = true;
  selectablePessoaHabilidadeAtitude = true;
  removablePessoaHabilidadeAtitude = true;
  separatorKeysCodesPessoaHabilidadeAtitude: number[] = [ENTER, COMMA];
  PessoaHabilidadeAtitudeCtrl = new FormControl();
  filteredPessoaHabilidadeAtitude: Observable<HabilidadeAtitude[]>;
  pessoaHabilidadesAtitudes: any[] = [];

  habilidadesAtitudes: HabilidadeAtitude[];

  public isVisible: boolean = false;

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  @ViewChild('fruitInputConhecimento') fruitInputConhecimento: ElementRef<HTMLInputElement>;
  @ViewChild('fruitInputIdioma') fruitInputIdioma: ElementRef<HTMLInputElement>;
  @ViewChild('fruitInputHabilidadeAtitude') fruitInputHabilidadeAtitude: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

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
    this.listarConhecimentosPessoa();
    this.listarPessoaIdioma();
    this.listarHabilidadesAtitudes();
  }

  habilitarBotaoEnviar() {
      this.isVisible = !this.isVisible;
  }

  save(): void {
    const dados: VagaPessoa = {
      id: 0,
      cpf: {
        cpf: this.cpf,
        nome: this.nome,
        sexo: this.sexo,
        telefonecelular: this.telefone,
        datanascimento: this.datanascimento,
        numero: this.numero,
        enderecoid: {
          id: this.enderecoid,
        },
        pessoaIdiomas: this.pessoaIdiomas.map((x) => ({ idioma: x })),
        pessoaConhecimentos: this.pessoaConhecimentos.map((x) => ({ conhecimento: x })),
        pessoaHabilidadesAtitudes: this.pessoaHabilidadesAtitudes.map((x) => ({ habilidadeAtitude: x }))
      },
      vagaid: this.vagaid,
      experienciaprofissional: this.experienciaprofissional,
    };
    this.data.component.salvar('IncluirExterno', dados);
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

  // Conhecimentos Pessoas
  listarConhecimentosPessoa(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'conhecimento/all').then((response) => {
      if (response && response.data) {
        this.conhecimentos = response.data;
        if (this.conhecimentoId) {
          this.filteredConhecimentosPessoa = this.ConhecimentoPessoaCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterPessoaConhecimento(item) : this.conhecimentos.filter(x => x.id != this.conhecimentoId).slice()));
        } else {
          this.filteredConhecimentosPessoa = this.ConhecimentoPessoaCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterPessoaConhecimento(item) : this.conhecimentos.slice()));
        }
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
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

  private _filterPessoaConhecimento(value: any): Conhecimento[] {
    if (value && value.nome) {
      value = value.nome;
    }
    if (value) {
      const filterValue = value.toLowerCase();
      if (this.conhecimentoId) {
        return this.conhecimentos.filter(item => item.id != this.conhecimentoId && item.nome.toLowerCase().includes(filterValue));
      } else {
        return this.conhecimentos.filter(item => item.nome.toLowerCase().includes(filterValue));
      }
    }
  }

  // Pessoa Idioma
  listarPessoaIdioma(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'idioma/all').then((response) => {
      if (response && response.data) {
        debugger
        this.idiomas = response.data;
        if (this.idiomaId) {
          this.filteredPessoaIdioma = this.PessoaIdiomaCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterPessoaIdioma(item) : this.idiomas.filter(x => x.id != this.idiomaId).slice()));
        } else {
          this.filteredPessoaIdioma = this.PessoaIdiomaCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterPessoaIdioma(item) : this.idiomas.slice()));
        }
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  addPessoaIdioma(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    if ((value || '').trim()) {
      // this.incidenciasAtingidas.push(value.trim());
    }
    if (input) {
      input.value = '';
    }
    this.PessoaIdiomaCtrl.setValue(null);
  }

  removePessoaIdioma(item: Idioma): void {
    debugger
    const index = this.pessoaIdiomas.indexOf(item);

    if (index >= 0) {
      this.pessoaIdiomas.splice(index, 1);
    }
  }

  selectedPessoaIdioma(event: MatAutocompleteSelectedEvent): void {
    if (!this.pessoaIdiomas.includes(event.option.value)) {
      var achou = 0;
      for (let index = 0; index < this.pessoaIdiomas.length; index++) {
        var idioma = this.pessoaIdiomas[index].descricao;
        var novoIdioma = event.option.value.descricao;
        if (idioma == novoIdioma) {
          achou++;
        }
      }
      if (achou == 0) {
        this.pessoaIdiomas.push(event.option.value);
      }
    }
    this.fruitInputIdioma.nativeElement.value = '';
    this.PessoaIdiomaCtrl.setValue(null);
  }

  private _filterPessoaIdioma(value: any): Idioma[] {
    debugger
    if (value && value.descricao) {
      value = value.descricao;
    }
    if (value) {
      const filterValue = value.toLowerCase();
      if (this.idiomaId) {
        return this.idiomas.filter(item => item.id != this.idiomaId && item.descricao.toLowerCase().includes(filterValue));
      } else {
        return this.idiomas.filter(item => item.descricao.toLowerCase().includes(filterValue));
      }
    }
  }

  // Habilidades
  listarHabilidadesAtitudes(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'habilidadeAtitude/all').then((response) => {
      if (response && response.data) {
        this.habilidadesAtitudes = response.data;
        if (this.habilidadeId) {
          this.filteredPessoaHabilidadeAtitude = this.PessoaHabilidadeAtitudeCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterPessoaHabilidadeAtitude(item) : this.habilidadesAtitudes.filter(x => x.id != this.habilidadeId).slice()));
        } else {
          this.filteredPessoaHabilidadeAtitude = this.PessoaHabilidadeAtitudeCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterPessoaHabilidadeAtitude(item) : this.habilidadesAtitudes.slice()));
        }
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
      if (this.habilidadeId) {
        return this.habilidadesAtitudes.filter(item => item.id != this.habilidadeId && item.descricao.toLowerCase().includes(filterValue));
      } else {
        return this.habilidadesAtitudes.filter(item => item.descricao.toLowerCase().includes(filterValue));
      }
    }
  }

  ngOnInit(): void {
    if (this.data) {
      this.vagaid = this.data.row.id;
    }
    this.firstFormGroup = this._formBuilder.group({
      group1CPF: ['', Validators.required],
      group1Nome: ['', Validators.required],
      group1DataNascimento: ['', Validators.required],
      group1Sexo: ['', Validators.required],
      group1Telefone: ['', Validators.required],
      group1Endereco: ['', Validators.required],
      group1Numero: ['', Validators.required],
    });
    this.secondFormGroup = this._formBuilder.group({
      group2ExperienciaProfissional: ['', Validators.required],
    });
  }

}
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
  datanascimento: Date;
  enderecoid: number;
  numero: number;
  experienciaprofissional: string;
  idiomaid: number;
  nivel: string;

  enderecos: Endereco[];
  idiomas: Idioma[];

  // Conhecimento
  conhecimentoId: number;

  visibleConhecimentoPessoa = true;
  selectableConhecimentoPessoa = true;
  removableConhecimentoPessoa = true;
  separatorKeysCodesConhecimentoPessoa: number[] = [ENTER, COMMA];
  ConhecimentoPessoaCtrl = new FormControl();
  filteredConhecimentosPessoa: Observable<Conhecimento[]>;
  pessoaConhecimentos: any[] = [];

  conhecimentos: Conhecimento[];

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;
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
    this.listarIdioma();
    this.listarConhecimentosPessoa();
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

  listarIdioma(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'idioma/all').then((response) => {
      if (response && response.data) {
        this.idiomas = response.data;
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
    debugger
    if (!this.pessoaConhecimentos.includes(event.option.value)) {
      this.pessoaConhecimentos.push(event.option.value);
    }
    this.fruitInput.nativeElement.value = '';
    this.ConhecimentoPessoaCtrl.setValue(null);
  }

  private _filterPessoaConhecimento(value: any): Conhecimento[] {
    debugger
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
      group2ExperienciaProfissional: ['', Validators.required],
      group2Idioma: ['', Validators.required],
      group2Nivel: ['', Validators.required],
    });
  }

}
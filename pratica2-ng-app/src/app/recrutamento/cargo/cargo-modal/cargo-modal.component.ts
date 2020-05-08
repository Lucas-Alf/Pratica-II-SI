import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Cargo } from '../cargo';
import { MatChipInputEvent } from '@angular/material/chips';
import { FormControl } from '@angular/forms';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Conhecimento } from '../../conhecimento/conhecimento';
import { HabilidadeAtitude } from '../../habilidadeAtitude/habilidadeAtitude';

export interface Cbo {
  id: string;
  descricaosumaria: string;
}

export interface Departamento {
  id: string;
  nome: string;
  descricao: string;
}

@Component({
  selector: 'app-cargo-modal',
  templateUrl: './cargo-modal.component.html',
  styleUrls: ['./cargo-modal.component.css'],
})

export class CargoModalComponent implements OnInit {

  apiUrl: string;

  id: number;
  descricao: string;
  cboid: string;
  departamentoid: number;
  faixatabelasalarialid: number;

  cbos: Cbo[];
  departamentos: Departamento[];
  faixaTabelaSalarialList: any[];

  // Conhecimento
  conhecimentoId: number;

  visibleConhecimento = true;
  selectableConhecimento = true;
  removableConhecimento = true;
  separatorKeysCodesConhecimento: number[] = [ENTER, COMMA];
  ConhecimentoCtrl = new FormControl();
  filteredConhecimentos: Observable<Conhecimento[]>;
  cargoConhecimentos: any[] = [];

  conhecimentos: Conhecimento[];

  // Habilidade
  habilidadeId: number;

  visibleHabilidade = true;
  selectableHabilidade = true;
  removableHabilidade = true;
  separatorKeysCodesHabilidade: number[] = [ENTER, COMMA];
  HabilidadeCtrl = new FormControl();
  filteredHabilidades: Observable<HabilidadeAtitude[]>;
  cargoHabilidadeAtitudes: any[] = [];

  habilidades: HabilidadeAtitude[];

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('fruitInput2') fruitInput2: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<CargoModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarCbo();
    this.listarDepartamento();
    this.listarFaixaTabelaSalarial();
    this.listarConhecimentos();
    this.listarHabilidades();
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Cargo = {
      id: this.id, descricao: this.descricao,
      cboid: {
        id: this.cboid,
        descricaosumaria: ''
    },
    departamentoid: {
      id: this.departamentoid,
      nome: '',
      descricao: ''
    },
    faixatabelasalarial: {
      id: this.faixatabelasalarialid
      },
      cargoConhecimentos: this.cargoConhecimentos.map((x) => ({ conhecimento: x })),
      cargoHabilidadeAtitudes: this.cargoHabilidadeAtitudes.map((x) => ({ habilidadeatitude: x }))
    };
    this.data.component.salvar(this.data.action, dados);
  }

  listarCbo(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'cbo/all').then((response) => {
      if (response && response.data) {
        this.cbos = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
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

  listarFaixaTabelaSalarial(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'FaixaTabelaSalarial/all').then((response) => {
      if (response && response.data) {
        this.faixaTabelaSalarialList = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  // Conhecimentos
  listarConhecimentos(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'conhecimento/all').then((response) => {
      if (response && response.data) {
        this.conhecimentos = response.data;
        if (this.conhecimentoId) {
          this.filteredConhecimentos = this.ConhecimentoCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterCargoConhecimento(item) : this.conhecimentos.filter(x => x.id != this.conhecimentoId).slice()));
        } else {
          this.filteredConhecimentos = this.ConhecimentoCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filterCargoConhecimento(item) : this.conhecimentos.slice()));
        }
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  addCargoConhecimento(event: MatChipInputEvent): void {
    debugger
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      // this.incidenciasAtingidas.push(value.trim());
    }

    if (input) {
      input.value = '';
    }

    this.ConhecimentoCtrl.setValue(null);
  }

  removeCargoConhecimento(item: Conhecimento): void {
    const index = this.cargoConhecimentos.indexOf(item);

    if (index >= 0) {
      this.cargoConhecimentos.splice(index, 1);
    }
  }

  selectedCargoConhecimento(event: MatAutocompleteSelectedEvent): void {
    debugger
    if (!this.cargoConhecimentos.includes(event.option.value)) {
      this.cargoConhecimentos.push(event.option.value);
    }
    this.fruitInput.nativeElement.value = '';
    this.ConhecimentoCtrl.setValue(null);
  }

  private _filterCargoConhecimento(value: any): Conhecimento[] {
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

    // Habilidades
    listarHabilidades(): void {
      this.loaderService.show();
      axios.get(this.apiUrl + 'habilidadeAtitude/all').then((response) => {
        if (response && response.data) {
          debugger
          this.habilidades = response.data;
          if (this.habilidadeId) {
            this.filteredHabilidades = this.HabilidadeCtrl.valueChanges.pipe(
              startWith(null),
              map((item2: string | null) => item2 ? this._filterCargoHabilidade(item2) : this.habilidades.filter(x => x.id != this.habilidadeId).slice()));
          } else {
            this.filteredHabilidades = this.HabilidadeCtrl.valueChanges.pipe(
              startWith(null),
              map((item2: string | null) => item2 ? this._filterCargoHabilidade(item2) : this.habilidades.slice()));
          }
          this.loaderService.hide();
        }
      }).catch((error) => {
        this.loaderService.hide();
        console.log(error);
        this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
      });
    }
  
    addCargoHabilidade(event: MatChipInputEvent): void {
      debugger
      const input = event.input;
      const value = event.value;
  
      if ((value || '').trim()) {
        // this.incidenciasAtingidas.push(value.trim());
      }
  
      if (input) {
        input.value = '';
      }
  
      this.HabilidadeCtrl.setValue(null);
    }
  
    removeCargoHabilidade(item2: HabilidadeAtitude): void {
      const index = this.cargoHabilidadeAtitudes.indexOf(item2);
  
      if (index >= 0) {
        this.cargoHabilidadeAtitudes.splice(index, 1);
      }
    }
  
    selectedCargoHabilidade(event: MatAutocompleteSelectedEvent): void {
      debugger
      if (!this.cargoHabilidadeAtitudes.includes(event.option.value)) {
        this.cargoHabilidadeAtitudes.push(event.option.value);
      }
      this.fruitInput2.nativeElement.value = '';
      this.HabilidadeCtrl.setValue(null);
    }
  
    private _filterCargoHabilidade(value: any): HabilidadeAtitude[] {
      debugger
      if (value && value.descricao) {
        value = value.descricao;
      }
      if (value) {
        const filterValue = value.toLowerCase();
        if (this.habilidadeId) {
          return this.habilidades.filter(item2 => item2.id != this.habilidadeId && item2.descricao.toLowerCase().includes(filterValue));
        } else {
          return this.habilidades.filter(item2 => item2.descricao.toLowerCase().includes(filterValue));
        }
      }
    }

  ngOnInit(): void {
    if (this.data.info) {
      debugger
      this.id = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.cboid = this.data.info.cboid.id;
      this.departamentoid = this.data.info.departamentoid.id;
      this.faixatabelasalarialid = this.data.info.faixatabelasalarial.id;
      if (this.data.info.cargoConhecimentos) {
        this.cargoConhecimentos = this.data.info.cargoConhecimentos.map(x => x.conhecimento);
      } else {
        this.cargoConhecimentos = [];
      }
      if (this.data.info.cargoHabilidadeAtitudes) {
        this.cargoHabilidadeAtitudes = this.data.info.cargoHabilidadeAtitudes.map(x => x.habilidadeatitude);
      } else {
        this.cargoHabilidadeAtitudes = [];
      }
    }
  }

}
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

  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  ConhecimentoCtrl = new FormControl();
  filteredConhecimentos: Observable<Conhecimento[]>;
  cargoConhecimentos: any[] = [];

  conhecimentos: Conhecimento[];

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;
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
      cargoConhecimentos: this.cargoConhecimentos.map((x) => ({ conhecimento: x }))
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
            map((item: string | null) => item ? this._filter(item) : this.conhecimentos.filter(x => x.id != this.conhecimentoId).slice()));
        } else {
          this.filteredConhecimentos = this.ConhecimentoCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filter(item) : this.conhecimentos.slice()));
        }
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  add(event: MatChipInputEvent): void {
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

  remove(item: Conhecimento): void {
    const index = this.cargoConhecimentos.indexOf(item);

    if (index >= 0) {
      this.cargoConhecimentos.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (!this.cargoConhecimentos.includes(event.option.value)) {
      this.cargoConhecimentos.push(event.option.value);
    }
    this.fruitInput.nativeElement.value = '';
    this.ConhecimentoCtrl.setValue(null);
  }

  private _filter(value: any): Conhecimento[] {
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

  ngOnInit(): void {
    if (this.data.info) {
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
    }
  }

}
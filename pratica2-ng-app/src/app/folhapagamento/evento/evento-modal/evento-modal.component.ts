import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { Evento } from '../evento';
import { Incidencia } from '../../incidencia/incidencia';
import { RotinaCalculo } from '../../rotina-calculo/RotinaCalculo';
import { MatChipInputEvent } from '@angular/material/chips';
import { FormControl } from '@angular/forms';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-evento-modal',
  templateUrl: './evento-modal.component.html',
  styleUrls: ['./evento-modal.component.css']
})
export class EventoModalComponent implements OnInit {
  apiUrl: string;
  codigoHabilitado: boolean;
  codigo: number;
  descricao: string;
  tipo: string;
  automatico: boolean;
  incidenciaId: number;
  rotinacalculoId: number;
  percentual: number;


  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  IncidenciasCtrl = new FormControl();
  filteredIncidencias: Observable<Incidencia[]>;
  incidenciasAtingidas: any[] = [];

  incidencias: Incidencia[];
  rotinas: RotinaCalculo[];

  @ViewChild('fruitInput') fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<EventoModalComponent>,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    private constant: ConstantsService
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarIncidencias();
    this.listarRotinas();
  }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Evento = {
      id: this.codigo,
      descricao: this.descricao,
      tipo: this.tipo,
      automatico: this.automatico,
      percentual: this.percentual,
      incidenciaId: {
        id: this.incidenciaId, descricao: ''
      },
      rotinacalculoId: {
        id: this.rotinacalculoId, descricao: ''
      },
      incidenciasAtingidas: this.incidenciasAtingidas.map((x) => ({ incidencia: x }))
    };
    this.data.component.salvar(this.data.action, dados);
  }

  listarIncidencias(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'incidencia/all').then((response) => {
      if (response && response.data) {
        this.incidencias = response.data;
        if (this.incidenciaId) {
          this.filteredIncidencias = this.IncidenciasCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filter(item) : this.incidencias.filter(x => x.id != this.incidenciaId).slice()));
        } else {
          this.filteredIncidencias = this.IncidenciasCtrl.valueChanges.pipe(
            startWith(null),
            map((item: string | null) => item ? this._filter(item) : this.incidencias.slice()));
        }
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarRotinas(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'RotinaCalculo/all').then((response) => {
      if (response && response.data) {
        this.rotinas = response.data;
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

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.IncidenciasCtrl.setValue(null);
  }

  remove(item: Incidencia): void {
    const index = this.incidenciasAtingidas.indexOf(item);

    if (index >= 0) {
      this.incidenciasAtingidas.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (!this.incidenciasAtingidas.includes(event.option.value)) {
      this.incidenciasAtingidas.push(event.option.value);
    }
    this.fruitInput.nativeElement.value = '';
    this.IncidenciasCtrl.setValue(null);
  }

  private _filter(value: any): Incidencia[] {
    if (value && value.descricao) {
      value = value.descricao;
    }
    if (value) {
      const filterValue = value.toLowerCase();
      if (this.incidenciaId) {
        return this.incidencias.filter(item => item.id != this.incidenciaId && item.descricao.toLowerCase().includes(filterValue));
      } else {
        return this.incidencias.filter(item => item.descricao.toLowerCase().includes(filterValue));
      }
    }
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.codigo = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.tipo = this.data.info.tipo;
      this.percentual = this.data.info.percentual;
      this.automatico = this.data.info.automatico;
      this.incidenciaId = this.data.info.incidenciaId.id;
      this.rotinacalculoId = this.data.info.rotinacalculoId.id;
      this.codigoHabilitado = false;
      if (this.data.info.incidenciasAtingidas) {
        this.incidenciasAtingidas = this.data.info.incidenciasAtingidas.map(x => x.incidencia);
      } else {
        this.incidenciasAtingidas = [];
      }
    } else {
      this.codigoHabilitado = true;
    }
  }
}

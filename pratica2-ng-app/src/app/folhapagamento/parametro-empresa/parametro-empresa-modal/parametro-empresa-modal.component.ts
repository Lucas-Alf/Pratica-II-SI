import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ParametroEmpresa } from '../ParametroEmpresa';
import { Router } from '@angular/router';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import axios from 'axios';
import { Evento } from '../../evento/evento';
import { PeriodoCalculo } from '../../periodo-calculo/PeriodoCalculo';

@Component({
  selector: 'app-parametro-empresa-modal',
  templateUrl: './parametro-empresa-modal.component.html',
  styleUrls: ['./parametro-empresa-modal.component.css']
})
export class ParametroEmpresaModalComponent implements OnInit {

  apiUrl: string;
  eventos: Evento[];
  periodosDeCalculo: PeriodoCalculo[];

  periodocalculoId: number;
  eventosalarioId: number;
  eventoinssId: number;
  eventofgtsId: number;
  eventoirrfId: number;
  eventohoraextra50Id: number;
  eventohoraextra100Id: number;
  eventototaldescontosId: number;
  eventototalvencimentosId: number;
  eventototalliquidoId: number;

  constructor(
    private router: Router,
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<ParametroEmpresaModalComponent>,
    private loader: LoaderService
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarEventos();
    this.listarPeriodosDeCalculo();
  }

  close(): void {
    this.dialogRef.close();
    this.router.navigate(['/folhapagamento']);
  }

  save(): void {
    this.loader.show();
    const dados: ParametroEmpresa = {
      id: null,
      empresacnpj: null,
      periodocalculo: { id: this.periodocalculoId, dataFinal: null, dataInicial: null, execucao: null },
      eventosalario: {
        id: this.eventosalarioId,
        automatico: null,
        descricao: '',
        percentual: null,
        incidenciaId: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      },
      eventoinss: {
        id: this.eventoinssId,
        automatico: null,
        descricao: '',
        incidenciaId: null,
        percentual: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      },
      eventoirrf: {
        id: this.eventoirrfId,
        automatico: null,
        descricao: '',
        percentual: null,
        incidenciaId: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      },
      eventofgts: {
        id: this.eventofgtsId,
        automatico: null,
        descricao: '',
        percentual: null,
        incidenciaId: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      },
      eventohoraextra50: {
        id: this.eventohoraextra100Id,
        automatico: false,
        descricao: '',
        percentual: null,
        incidenciaId: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      },
      eventohoraextra100: {
        id: this.eventohoraextra100Id,
        automatico: null,
        descricao: '',
        percentual: null,
        incidenciaId: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      },
      eventototaldescontos: {
        id: this.eventototaldescontosId,
        automatico: null,
        descricao: '',
        percentual: null,
        incidenciaId: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      },
      eventototalvencimentos: {
        id: this.eventototalvencimentosId,
        automatico: null,
        descricao: '',
        percentual: null,
        incidenciaId: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      },
      eventototalliquido: {
        id: this.eventototalliquidoId,
        automatico: null,
        descricao: '',
        percentual: null,
        incidenciaId: null,
        incidenciasAtingidas: null,
        rotinacalculoId: null,
        tipo: null
      }
    };
    axios.post(this.apiUrl + 'parametroempresa/save', dados).then((response) => {
      if (response && response.data) {
        this.loader.hide();
        this.dialogRef.close();
        this.router.navigate(['/folhapagamento']);
      } else {
        this.snackBar.open('Ocorreu um erro ao salvar. 😬', null, { duration: 5000 });
      }
    }).catch((error) => {
      if (error.response) {
        this.loader.hide();
        console.error(error.response.data.message);
        this.snackBar.open(error.response.data.message, null, { duration: 5000 });
      } else {
        this.loader.hide();
        this.snackBar.open('Ocorreu um erro ao salvar. 😬', null, { duration: 5000 });
      }
    });
  }

  get(): void {
    this.loader.show();
    axios.get(this.apiUrl + 'parametroempresa/get').then((response) => {
      const dados = response.data;
      if (dados) {
        if (dados.periodocalculo) {
          this.periodocalculoId = dados.periodocalculo.id;
        }
        if (dados.eventosalario) {
          this.eventosalarioId = dados.eventosalario.id;
        }
        if (dados.eventoinss) {
          this.eventoinssId = dados.eventoinss.id;
        }
        if (dados.eventoirrf) {
          this.eventoirrfId = dados.eventoirrf.id;
        }
        if (dados.eventofgts) {
          this.eventofgtsId = dados.eventofgts.id;
        }
        if (dados.eventohoraextra50) {
          this.eventohoraextra50Id = dados.eventohoraextra50.id;
        }
        if (dados.eventohoraextra100) {
          this.eventohoraextra100Id = dados.eventohoraextra100.id;
        }
        if (dados.eventototaldescontos) {
          this.eventototaldescontosId = dados.eventototaldescontos.id;
        }
        if (dados.eventototalvencimentos) {
          this.eventototalvencimentosId = dados.eventototalvencimentos.id;
        }
        if (dados.eventototalliquido) {
          this.eventototalliquidoId = dados.eventototalliquido.id;
        }
      }
      this.loader.hide();
    }).catch((error) => {
      this.loader.hide();
      if (error.response) {
        console.error(error.response.data.message);
        this.snackBar.open(error.response.data.message, null, { duration: 5000 });
      } else {
        this.snackBar.open('Ocorreu um erro ao buscar os dados. 😬', null, { duration: 5000 });
      }
    });
  }

  listarEventos(): void {
    axios.get(this.apiUrl + 'evento/all').then((response) => {
      if (response && response.data) {
        this.eventos = response.data;
      }
    }).catch((error) => {
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarPeriodosDeCalculo(): void {
    axios.get(this.apiUrl + 'PeriodoCalculo/all').then((response) => {
      if (response && response.data) {
        this.periodosDeCalculo = response.data;
      }
    }).catch((error) => {
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  retornaExecucao(execucao: number): string {
    switch (execucao) {
      case 1:
        return 'Mensal';
      case 2:
        return 'Férias';
      case 3:
        return 'Adt. Décimo Terceiro';
      case 4:
        return '13º Final';
      case 5:
        return 'Desligamento';
      case 6:
        return 'Folha Avulsa';
      case 7:
        return 'Adiantamento Quinzenal';
      default:
        break;
    }
  }
  ngOnInit(): void {
    this.get();
  }
}

import { Component, OnInit, Inject, ViewChild, ElementRef } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { DatePipe } from '@angular/common';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { Observable } from 'rxjs';
import { map, startWith, filter } from 'rxjs/operators';
import { Endereco } from 'src/app/contratacao/endereco/endereco';
import { Idioma } from '../recrutamentoExterno/recrutamentoExterno-modal/recrutamentoExterno-modal.component';
import { Vaga } from '../vaga/vaga';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { HabilidadeAtitude } from '../habilidadeAtitude/habilidadeAtitude';
import { Conhecimento } from '../conhecimento/conhecimento';

export interface VagaPessoa {
  id: number;
  cpf: {
    cpf: string,
    nome: string,
    sexo: string,
    datanascimento: Date,
    numero: number,
    enderecoid: {
      id: number,
    },
    pessoaIdiomas: any[];
    pessoaConhecimentos: any[];
    pessoaHabilidadesAtitudes: any[];
  },
  vagaid: {
    id: number,
    descricao: string,
  },
  experienciaprofissional: string,
}

@Component({
  selector: 'app-selecaoCandidato',
  templateUrl: './selecaoCandidato.component.html',
  styleUrls: ['./selecaoCandidato.component.css'],
  providers: [DatePipe],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0', visibility: 'hidden' })),
      state('expanded', style({ height: '*', visibility: 'visible' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})

export class SelecaoCandidatoComponent implements OnInit {

  apiUrl: string;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public datepipe: DatePipe
  ) {
    this.apiUrl = this.constant.apiUrl;
    this.listarEndereco();
    this.listarPessoaIdioma();
    this.listarVaga();
    this.listarPessoaHabilidadeAtitude();
    this.listarPessoaConhecimento();
  }

  displayedColumns: string[] = ['cpf.cpf', 'cpf.nome', 'cpf.sexo', 'cpf.datanascimento', 'vagaid.descricao'];
  storeSelecaoCandidato = new MatTableDataSource();

  isExpansionDetailRow = (i: number, row: Object) => row.hasOwnProperty('detailRow');
  expandedElement: any;


  enderecos: Endereco[];
  idiomas: Idioma[];
  vagas: Vaga[];
  habilidadeAtitudes: HabilidadeAtitude[];
  conhecimentos: Conhecimento[];

  teste: VagaPessoa[];

  valorCPF: string;
  valorNome: string;
  valorSexo: string;
  valorVaga: string;
  valorIdioma: any[];
  valorHabilidadeAtitude: any[];
  valorConhecimento: any[];

  /*filterNome(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeSelecaoCandidato.filter = filterValue;

    this.storeSelecaoCandidato.filterPredicate = (data: VagaPessoa, filter) => {
      return !filter || data.cpf.nome.toLowerCase().includes(filter.toLowerCase());
    }
  }*/

  /*filterCpf(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeSelecaoCandidato.filter = filterValue;

    this.storeSelecaoCandidato.filterPredicate = (data: VagaPessoa, filter) => {
      return !filter || data.cpf.cpf.includes(filter);
    }
  }*/

  /*filterDataNascimento(filterValue: string, event: MatDatepickerInputEvent<Date>) {
    if (event.value != undefined) {
      filterValue = this.datepipe.transform(event.value, 'yyyy-MM-dd');
      this.storeSelecaoCandidato.filter = filterValue;
    }

    this.storeSelecaoCandidato.filterPredicate = (data: VagaPessoa, filter) => {
      return !filter || data.cpf.datanascimento.toString().includes(filter);
    }
  }*/

  /*filterSexo(event) {
    debugger
    var filterValue = event.value;
    this.storeSelecaoCandidato.filter = filterValue;

    this.storeSelecaoCandidato.filterPredicate = (data: VagaPessoa, filter) => {
      return !filter || data.cpf.sexo.includes(filter);
    }
  }*/

    filterIdioma(event) {
      var lista = event.value;
      if (lista.length == 0) {
        this.storeSelecaoCandidato.filter = '';
      } else {
        for (let index = 0; index < lista.length; index++) {
          this.storeSelecaoCandidato.filter = lista[index].toString();
        }
      }

      this.storeSelecaoCandidato.filterPredicate = (data: VagaPessoa, filter) => {
        return !filter || data.cpf.pessoaIdiomas.map((x)=> (x.idioma.id.toString())).includes(filter);
      }
    }

    /*filterVaga(event) {
      debugger
      this.storeSelecaoCandidato.filter = event.value.toString();
  
      this.storeSelecaoCandidato.filterPredicate = (data: VagaPessoa, filter) => {
        debugger
        return !filter || data.vagaid.id.toString().includes(filter);
      }
    }*/

    filterHabilidadeAtitude(event) {
      var lista = event.value;
      if (lista.length == 0) {
        this.storeSelecaoCandidato.filter = '';
      } else {
        for (let index = 0; index < lista.length; index++) {
          this.storeSelecaoCandidato.filter = lista[index].toString();
        }
      }

      this.storeSelecaoCandidato.filterPredicate = (data: VagaPessoa, filter) => {
        return !filter || data.cpf.pessoaHabilidadesAtitudes.map((x)=> (x.habilidadeAtitude.id.toString())).includes(filter);
      }
    }

    filterConhecimento(event) {
      debugger
      var lista = event.value;
      if (lista.length == 0) {
        this.storeSelecaoCandidato.filter = '';
      } else {
        for (let index = 0; index < lista.length; index++) {
          this.storeSelecaoCandidato.filter = lista[index].toString();
        }
      }

      this.storeSelecaoCandidato.filterPredicate = (data: VagaPessoa, filter) => {
        return !filter || data.cpf.pessoaConhecimentos.map((x)=> (x.conhecimento.id.toString())).includes(filter);
      }
    }

    pesquisar() {
      debugger

      if (this.valorCPF == undefined) {
        this.valorCPF = '';
      }

      if (this.valorNome == undefined) {
        this.valorNome = '';
      }

      if (this.valorSexo == undefined) {
        this.valorSexo = '';
      }

      if (this.valorVaga == undefined) {
        this.valorVaga = '';
      }

      var idioma = "";
      if (this.valorIdioma == undefined) {
        this.valorIdioma = [];
      } else {
        for (let index = 0; index < this.valorIdioma.length; index++) {
          if ((this.valorIdioma.length - 1) == index) {
            idioma += this.valorIdioma[index];
          } else {
            idioma += this.valorIdioma[index] + ",";
          }
        }
      }

      var habilidadeAtitude = "";
      if (this.valorHabilidadeAtitude == undefined) {
        this.valorHabilidadeAtitude = [];
      } else {
        for (let index = 0; index < this.valorHabilidadeAtitude.length; index++) {
          if ((this.valorHabilidadeAtitude.length - 1) == index) {
            habilidadeAtitude += this.valorHabilidadeAtitude[index];
          } else {
            habilidadeAtitude += this.valorHabilidadeAtitude[index] + ",";
          }
        }
      }

      var conhecimento = "";
      if (this.valorConhecimento == undefined) {
        this.valorConhecimento = [];
      } else {
        for (let index = 0; index < this.valorConhecimento.length; index++) {
          if ((this.valorConhecimento.length - 1) == index) {
            conhecimento += this.valorConhecimento[index];
          } else {
            conhecimento += this.valorConhecimento[index] + ",";
          }
        }
      }

      var params = {
        valorCPF: this.valorCPF,
        valorNome: this.valorNome,
        valorSexo: this.valorSexo,
        valorVaga: this.valorVaga,
        idioma: idioma,
        habilidadeAtitude: habilidadeAtitude,
        conhecimento: conhecimento,
      }

      this.loaderService.show();
      axios.get(this.apiUrl + 'vagapessoa/listarSelecao2', { params } ).then((response) => {
        if (response && response.data) {
          this.storeSelecaoCandidato.data = response.data;
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

  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'vagapessoa/listarSelecao').then((response) => {
      if (response && response.data) {
        this.storeSelecaoCandidato.data = response.data;
        //this.storeSelecaoCandidato = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarPessoaIdioma(): void {
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

  listarVaga(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'vaga/all').then((response) => {
      if (response && response.data) {
        this.vagas = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarPessoaHabilidadeAtitude(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'habilidadeAtitude/all').then((response) => {
      if (response && response.data) {
        this.habilidadeAtitudes = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  listarPessoaConhecimento(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'conhecimento/all').then((response) => {
      if (response && response.data) {
        this.conhecimentos = response.data
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.listar();
  }

}
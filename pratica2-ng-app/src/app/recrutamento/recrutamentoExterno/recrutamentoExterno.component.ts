import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

export interface Vaga {
  id: number;
  descricao: string;
  quantidade: number;
  prazo: Date;
  tipo: string;
  cargoid: {
    id: number;
    descricao: string;
    cboid: {
      id: string;
    },
    cargoConhecimentos: [{
      conhecimento: {
        nome: string;
        formacao: string;
      }
    }],
    cargoHabilidadeAtitudes: [{
      habilidadeatitude: {
        id: number;
        descricao: string;
        tipo: string;
      }
    }],
  },
  departamentoid: {
    descricao: string;
  }
}

@Component({
  selector: 'app-recrutamentoExterno',
  templateUrl: './recrutamentoExterno.component.html',
  styleUrls: ['./recrutamentoExterno.component.css']
})
export class RecrutamentoExternoComponent implements OnInit {

  apiUrl: string;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['id'];
  storeVagaExterno = new MatTableDataSource();
  selection = new SelectionModel<Vaga>();

  retornaConhecimentos(vaga: Vaga): string {
    var retorno = vaga.cargoid.cargoConhecimentos.map(x => x.conhecimento.nome).join(', ');
    if (retorno != "") {
      retorno = retorno + ".";
    }
    return retorno;
  }

  retornaHabilidades(vaga: Vaga): string {
    var retorno = vaga.cargoid.cargoHabilidadeAtitudes.filter(x=>x.habilidadeatitude.tipo == "Habilidade").map(x => x.habilidadeatitude.descricao).join(', ');
    if (retorno != "") {
      retorno = retorno + ".";
    }
    return retorno;
  }

  retornaAtitudes(vaga: Vaga): string {
    var retorno = vaga.cargoid.cargoHabilidadeAtitudes.filter(x=>x.habilidadeatitude.tipo == "Atitude").map(x => x.habilidadeatitude.descricao).join(', ');
    if (retorno != "") {
      retorno = retorno + ".";
    }
    return retorno;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeVagaExterno.filter = filterValue;
  }

  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'vaga/all').then((response) => {
      debugger
      if (response && response.data) {
        this.storeVagaExterno.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeVagaExterno.filterPredicate = (data: Vaga, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<Vaga>(allowMultiSelect, initialSelection);
  }

}

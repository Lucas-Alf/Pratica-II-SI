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
      //id: number;
      conhecimento: {
        //id: number;
        nome: string;
        //formacao: string;
      }
    }],
    cargoHabilidadeAtitudes: [{
      //id: number;
      habilidadeatitude: {
        id: number;
        descricao: string;
        tipo: string;
      }
    }],
  }
}

@Component({
  selector: 'app-recrutamentoInterno',
  templateUrl: './recrutamentoInterno.component.html',
  styleUrls: ['./recrutamentoInterno.component.css']
})
export class RecrutamentoInternoComponent implements OnInit {

  apiUrl: string;
  //dialogRef: MatDialogRef<ConhecimentoModalComponent, any>;
  constructor(
    private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog
  ) {
    this.apiUrl = this.constant.apiUrl;
  }

  displayedColumns: string[] = ['id'];
  storeVagaInterno = new MatTableDataSource();
  selection = new SelectionModel<Vaga>();

  retornaConhecimentos(vaga: Vaga): string {
    return vaga.cargoid.cargoConhecimentos.map(x => x.conhecimento.nome).join(', ') + '.';
  }

  teste: string;

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeVagaInterno.filter = filterValue;
  }

  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'vaga/all').then((response) => {
      debugger
      if (response && response.data) {
        this.storeVagaInterno.data = response.data;
        //this.teste = response.data.cargoid.cargoConhecimentos.map((x)=> (x.conhecimento.nome)).join(', ');
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  ngOnInit(): void {
    this.storeVagaInterno.filterPredicate = (data: Vaga, filter) => {
      return !filter || data.descricao.toLowerCase().includes(filter.toLowerCase());
    }

    //Preenche a tabela
    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<Vaga>(allowMultiSelect, initialSelection);
  }

}

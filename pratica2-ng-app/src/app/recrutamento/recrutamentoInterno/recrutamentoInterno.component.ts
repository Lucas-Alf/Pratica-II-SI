import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { RecrutamentoInternoModalComponent } from './recrutamentoInterno-modal/recrutamentoInterno-modal.component';
import { VagaPessoa } from './vagapessoa';

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
  selector: 'app-recrutamentoInterno',
  templateUrl: './recrutamentoInterno.component.html',
  styleUrls: ['./recrutamentoInterno.component.css']
})
export class RecrutamentoInternoComponent implements OnInit {

  apiUrl: string;
  dialogRef: MatDialogRef<RecrutamentoInternoModalComponent, any>;
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

  abrirModalRecrutInterno(row): void {
    this.dialogRef = this.dialog.open(RecrutamentoInternoModalComponent, { data: { row: row, component: this } });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeVagaInterno.filter = filterValue;
  }

  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'vaga/listarInterno').then((response) => {
      debugger
      if (response && response.data) {
        this.storeVagaInterno.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }

  salvar(action: string, data: VagaPessoa): void {
    debugger
    this.loaderService.show();
      axios.post(this.constant.apiUrl + 'vagapessoa/' + action, data).then((response) => {
        if (response && response.data) {
          this.loaderService.hide();
          this.listar();
          this.dialogRef.close();
        } else {
          this.loaderService.hide();
          this.snackBar.open('Ocorreu um erro ao salvar. 😬', null, { duration: 5000 });
        }
      }).catch((error) => {
        this.loaderService.hide();
        if (error.response) {
          console.error(error.response.data.message);
          this.snackBar.open(error.response.data.message, null, { duration: 5000 });
        } else {
          this.snackBar.open('Ocorreu um erro ao salvar. 😬', null, { duration: 5000 });
        }
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

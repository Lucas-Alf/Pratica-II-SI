import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { RecrutamentoExternoModalComponent } from './recrutamentoExterno-modal/recrutamentoExterno-modal.component';
import { VagaPessoa } from '../recrutamentoInterno/vagapessoa';

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
  dialogRef: MatDialogRef<RecrutamentoExternoModalComponent, any>;
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

  salvar(action: string, data: VagaPessoa): void {
    debugger
    if (data.cpf.cpf == undefined || data.cpf.cpf == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe o CPF.', null, { duration: 5000 });
    } else if (data.cpf.nome == undefined || data.cpf.nome == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe o Nome Completo.', null, { duration: 5000 });
    } else if (data.cpf.datanascimento == undefined || data.cpf.datanascimento == null) {
      this.loaderService.hide();
      this.snackBar.open('Informe a Data de Nascimento.', null, { duration: 5000 });
    } else if (data.cpf.sexo == undefined || data.cpf.sexo == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe o Sexo.', null, { duration: 5000 });
    } else if (data.cpf.telefonecelular == undefined || data.cpf.telefonecelular == null) {
      this.loaderService.hide();
      this.snackBar.open('Informe o Telefone.', null, { duration: 5000 });
    } else if (data.cpf.enderecoid.id == undefined || data.cpf.enderecoid.id == null) {
      this.loaderService.hide();
      this.snackBar.open('Informe o Endereço.', null, { duration: 5000 });
    } else if (data.cpf.numero == undefined || data.cpf.numero == null) {
      this.loaderService.hide();
      this.snackBar.open('Informe o Número.', null, { duration: 5000 });
    } else if (data.experienciaprofissional == undefined || data.experienciaprofissional == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe as Experiências Profissionais.', null, { duration: 5000 });
    } else {
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
  }

  retornaConhecimentos(vaga: Vaga): string {
    var retorno = vaga.cargoid.cargoConhecimentos.map(x => x.conhecimento.nome).join(', ');
    if (retorno != "") {
      retorno = retorno + ".";
    }
    return retorno;
  }

  retornaHabilidades(vaga: Vaga): string {
    var retorno = vaga.cargoid.cargoHabilidadeAtitudes.filter(x => x.habilidadeatitude.tipo == "Habilidade").map(x => x.habilidadeatitude.descricao).join(', ');
    if (retorno != "") {
      retorno = retorno + ".";
    }
    return retorno;
  }

  retornaAtitudes(vaga: Vaga): string {
    var retorno = vaga.cargoid.cargoHabilidadeAtitudes.filter(x => x.habilidadeatitude.tipo == "Atitude").map(x => x.habilidadeatitude.descricao).join(', ');
    if (retorno != "") {
      retorno = retorno + ".";
    }
    return retorno;
  }

  abrirModalRecrutExterno(row): void {
    this.dialogRef = this.dialog.open(RecrutamentoExternoModalComponent, { data: { row: row, component: this } });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeVagaExterno.filter = filterValue;
  }

  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'vaga/listar').then((response) => {
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

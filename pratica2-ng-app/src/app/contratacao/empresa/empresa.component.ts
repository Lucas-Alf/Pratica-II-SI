import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SelectionModel } from '@angular/cdk/collections';
import { LoaderService } from 'src/app/services/loader.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { EmpresaModalComponent } from './empresa-modal/empresa-modal.component';
import { Empresa } from './empresa';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css']
})
export class EmpresaComponent implements OnInit {
  apiUrl: string;
  dialogRef: MatDialogRef<EmpresaModalComponent, any>;

  constructor(private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog) { this.apiUrl = this.constant.apiUrl; }

  displayedColumns: string[] = ['select', 'cnpj', 'razaosocial', 'nomefantasia', 'endereco', 'telefone'];
  storeEmpresa = new MatTableDataSource();
  selection = new SelectionModel<Empresa>();

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeEmpresa.data.length;
    return numSelected == numRows;
  }

  ngOnInit(): void {
    this.storeEmpresa.filterPredicate = (data: Empresa, filter) => {
      return !filter || data.razaosocial.toLowerCase().includes(filter.toLowerCase());
    }

    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<Empresa>(allowMultiSelect, initialSelection);
  }
  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'empresa/all').then((response) => {
      if (response && response.data) {
        this.storeEmpresa.data = response.data;
        this.loaderService.hide();
      }
    }).catch((error) => {
      this.loaderService.hide();
      console.log(error);
      this.snackBar.open('Ocorreu um erro ao buscar os dados. 😭', null, { duration: 5000 });
    });
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.storeEmpresa.filter = filterValue;
  }
  incluir(): void {
    this.dialogRef = this.dialog.open(EmpresaModalComponent, { data: { action: 'Incluir', component: this } });
  }
  alterar(): void {
    if (this.selection.selected.length > 0) {
      const selection = this.selection.selected[0];
      this.dialogRef = this.dialog.open(EmpresaModalComponent, { data: { action: 'Alterar', component: this, info: selection } });
    } else {
      this.snackBar.open('Selecione um registro para alterar. 🤦‍♂️', null, { duration: 5000 });
    }
  }
  excluir(): void {
    if (this.selection.selected.length > 0) {
      this.loaderService.show();
      axios.delete(this.apiUrl + 'empresa/delete/' + this.selection.selected[0].cnpj).then((response) => {
        if (response && response.status === 200) {
          this.loaderService.hide();
          this.listar();
        } else {
          this.loaderService.hide();
          console.log('response -> ', response);
          this.snackBar.open('Ocorreu um erro ao excluir o registro. 🤔', null, { duration: 5000 });
        }
      }).catch((error) => {
        this.loaderService.hide();
        console.log(error);
        this.snackBar.open('Ocorreu um erro ao excluir o registro. 🤔', null, { duration: 5000 });
      });
    } else {
      this.snackBar.open('Selecione um registro para excluir. 🤦‍♂️', null, { duration: 5000 });
    }
  }

  salvar(action: string, data: Empresa): void {
    debugger
    this.loaderService.show();
    if (data.razaosocial == undefined || data.razaosocial == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe a Razão Social.', null, { duration: 5000 });
    } else if (data.nomefantasia == undefined || data.nomefantasia == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe o Nome Fantasia.', null, { duration: 5000 });
    } else if (data.cnpj == undefined || data.cnpj == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe o CNPJ.', null, { duration: 5000 });
    } else if (data.endereco == undefined || data.endereco == "") {
      this.loaderService.hide();
      this.snackBar.open('Informe o Endereço.', null, { duration: 5000 });
    } else {
      axios.post(this.constant.apiUrl + 'empresa/' + action, data).then((response) => {
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
}
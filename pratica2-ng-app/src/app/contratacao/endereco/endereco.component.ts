import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import axios from 'axios';
import { ConstantsService } from 'src/app/common/services/constants.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { LoaderService } from 'src/app/services/loader.service';
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { Endereco } from './endereco';

@Component({
  selector: 'app-endereco',
  templateUrl: './endereco.component.html',
  styleUrls: ['./endereco.component.css']
})
export class EnderecoComponent implements OnInit {

  apiUrl: string;
  //dialogRef: MatDialogRef<EnderecoModalComponent, any>;

  constructor(private constant: ConstantsService,
    private snackBar: MatSnackBar,
    private loaderService: LoaderService,
    public dialog: MatDialog) { this.apiUrl = this.constant.apiUrl; }

  displayedColumns: string[] = ['select', 'id', 'rua', 'numero', 'bairro', 'cep', 'cidade'];
  storeEndereco = new MatTableDataSource();
  selection = new SelectionModel<Endereco>();
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.storeEndereco.data.length;
    return numSelected == numRows;
  }
  
  ngOnInit(): void {
    this.storeEndereco.filterPredicate = (data: Endereco, filter) => {
      return !filter || data.cep.toLowerCase().includes(filter.toLowerCase());
    }

    this.listar();

    const initialSelection = [];
    const allowMultiSelect = false;
    this.selection = new SelectionModel<Endereco>(allowMultiSelect, initialSelection);
  }
  listar(): void {
    this.loaderService.show();
    axios.get(this.apiUrl + 'Endereco/all').then((response) => {
      if (response && response.data) {
        this.storeEndereco.data = response.data;
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
    this.storeEndereco.filter = filterValue;
  }

}

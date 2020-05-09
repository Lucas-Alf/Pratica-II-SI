import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MatDialog } from '@angular/material/dialog';
import { ParametroEmpresaModalComponent } from './parametro-empresa-modal/parametro-empresa-modal.component';

@Component({
  selector: 'app-parametro-empresa',
  templateUrl: './parametro-empresa.component.html',
  styleUrls: ['./parametro-empresa.component.css']
})
export class ParametroEmpresaComponent implements OnInit {

  dialogRef: MatDialogRef<ParametroEmpresaModalComponent, any>;

  constructor(
    public dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.dialogRef = this.dialog.open(ParametroEmpresaModalComponent, { disableClose: true });
  }
}


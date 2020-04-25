import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Empresa } from '../empresa';

@Component({
  selector: 'app-empresa-modal',
  templateUrl: './empresa-modal.component.html',
  styleUrls: ['./empresa-modal.component.css']
})
export class EmpresaModalComponent implements OnInit {
  cnpj: string;
  razaosocial: string;
  telefone: string;
  nomefantasia: string;
  endereco: string;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<EmpresaModalComponent>
  ) { }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Empresa = { cnpj: this.cnpj, razaosocial: this.razaosocial, telefone: this.telefone, nomefantasia: this.nomefantasia, endereco: this.endereco };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.cnpj = this.data.info.cnpj;
      this.razaosocial = this.data.info.razaosocial;
      this.telefone = this.data.info.telefone;
      this.nomefantasia = this.data.info.nomefantasia;
      this.endereco = this.data.info.endereco;
    }
  }

}

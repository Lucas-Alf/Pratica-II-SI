import { Component, OnInit, Inject} from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Nivel } from '../permissao';

@Component({
  selector: 'app-permissao-modal',
  templateUrl: './permissao-modal.component.html',
  styleUrls: ['./permissao-modal.component.css']
})
export class PermissaoModalComponent implements OnInit {
  codigoHabilitado: boolean;
  id: number;
  descricao: string;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<PermissaoModalComponent>
  ) { }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Nivel = { id: this.id, descricao: this.descricao };
    this.data.component.salvar(this.data.action, dados);
  }

  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.codigoHabilitado = false;
    } else {
      this.codigoHabilitado = true;
    }
  }
}

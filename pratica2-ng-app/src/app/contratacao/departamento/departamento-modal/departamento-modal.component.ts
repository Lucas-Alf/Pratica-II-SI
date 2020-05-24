import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Departamento } from '../departamento';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-departamento-modal',
  templateUrl: './departamento-modal.component.html',
  styleUrls: ['./departamento-modal.component.css']
})
export class DepartamentoModalComponent implements OnInit {
  id: number;
  nome: string;
  descricao: string;
  form: FormGroup;
  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<DepartamentoModalComponent>
  ) { }

  close(): void {
    this.dialogRef.close();
  }

  save(): void {
    const dados: Departamento = { id: this.id, descricao: this.descricao, nome: this.nome };
    this.data.component.salvar(this.data.action, dados);
  }


  ngOnInit(): void {
    if (this.data.info) {
      this.id = this.data.info.id;
      this.descricao = this.data.info.descricao;
      this.nome = this.data.info.nome;
    }
    this.form = new FormGroup(
      {
        descricao: new FormControl("", Validators.required),
        nome: new FormControl()
      }
    );
  }

}

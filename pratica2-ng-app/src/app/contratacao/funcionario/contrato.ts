import { Pessoa } from './pessoa';
import { Departamento } from 'src/app/recrutamento/cargo/cargo-modal/cargo-modal.component';

export interface Contrato {
  matricula: number;
  situacao: number;
  dataadmissao: Date;
  regimetrabalho: number;
  horastrabalho: number;
  pessoa: Pessoa;
  datademissao: Date;
  departamentoid: Departamento;
}

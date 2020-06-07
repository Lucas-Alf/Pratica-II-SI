import { Pessoa } from './pessoa';
import { Departamento } from '../departamento/departamento';

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

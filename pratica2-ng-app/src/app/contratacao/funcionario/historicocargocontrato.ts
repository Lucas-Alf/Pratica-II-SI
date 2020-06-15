import { Pessoa } from './pessoa';
import { Departamento } from '../departamento/departamento';
import { Contrato } from './contrato';
import { Cargo } from 'src/app/recrutamento/cargo/cargo';

export interface Historicocargocontrato {
  id: number;
  data: Date;
  matricula: Contrato;
  cargoid: Cargo;
}

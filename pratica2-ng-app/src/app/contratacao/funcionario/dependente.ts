import { Pessoa } from './pessoa';

export interface Dependente {
  id: number;
  pessoacpf: Pessoa;
  dependentecpf: Pessoa;
}

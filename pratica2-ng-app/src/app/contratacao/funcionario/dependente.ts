import { Pessoa } from './pessoa';

export interface Dependente {
  id: number;
  nome: string;
  pessoacpf: Pessoa;
  dependentecpf: Pessoa;
}

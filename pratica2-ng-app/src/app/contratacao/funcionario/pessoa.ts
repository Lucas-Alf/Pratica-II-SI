import { Endereco } from '../endereco/endereco';
import { Pais } from './pais';

export interface Pessoa {
  cpf: string;
  nome: string;
  rg: string;
  sexo: string;
  datanascimento: Date;
  paisnascimentoid?: Pais;
  telefonecelular: number;
  telefonefixo: number;
  pispasep: number;
  pisexpedicao: Date;
  cnhnumero: number;
  cnhdata: Date;
  chntipo: string;
  ctpsnumero: number;
  ctpsserie: number;
  ctpsuf: string;
  nomepai: string;
  nomemae: string;
  tituloeleitornumero: number;
  tituloeleitoruf: string;
  tituloeleitorzona: number;
  tituloeleitorsecao: string;
  certificadoreservista: number;
  enderecoid?: Endereco;
  email: string;
  numero: number;
  dependente: any[];
}

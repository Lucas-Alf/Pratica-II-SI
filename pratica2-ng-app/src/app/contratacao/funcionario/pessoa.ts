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
  pispasep: string;
  pisexpedicao: Date;
  cnhnumero: string;
  cnhdata: Date;
  chntipo: string;
  ctpsnumero: string;
  ctpsserie: number;
  ctpsuf: string;
  nomepai: string;
  nomemae: string;
  tituloeleitornumero: string;
  tituloeleitoruf: string;
  tituloeleitorzona: number;
  tituloeleitorsecao: string;
  certificadoreservista: string;
  enderecoid?: Endereco;
  email: string;
  numero: number;
  dependente: any[];
  pessoaConhecimentos: any[];
  pessoaHabilidadesAtitudes: any[];
  pessoaIdiomas: any[];
  ativo?: boolean;
}

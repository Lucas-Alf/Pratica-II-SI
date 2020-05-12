export interface Pessoa {
  cpf: string;
  nome: string;
  rg: string;
  sexo: string;
  datanascimento: Date;
  paisnascimentoid: {
    id: number;
    nome: string;
  };
  telefonecelular: number;
  telefonefixo: number;
  pispasep: number;
  pisexpedicao: Date;
  cnhnumero: number;
  cnhdata: Date;
  chntipo: string;
  ctpsnumero: number;
  ctpsserie: number;
  ctpsuf: number;
  nomepai: string;
  nomemae: string;
  tituloeleitornumero: number;
  tituloeleitoruf: string;
  tituloeleitorzona: number;
  tituloeleitorsecao: string;
  certificadoreservista: number;
  enderecoid: {
    id: number;
    rua: string;
    numero: number;
    bairro: string;
    cep: string;
    cidadeid: {
      id: number;
      nome: string;
      estadoid: {
        id: number;
        nome: string;
        sigla: string;
      }
    }
  };
  email: string;
  numero: number;
}

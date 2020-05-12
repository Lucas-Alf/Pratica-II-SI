export interface Endereco {
  id: number;
  logradouro: string;
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
}
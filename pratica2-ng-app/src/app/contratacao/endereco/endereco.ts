export interface Endereco {
  id: number;
  rua: string;
  numero: number;
  bairro: string;
  cep: string;
  cidadeid: {
    id: number;
    nome: string;
    estadoid: number;
  }
}
export interface Cargo {
  id: number;
  descricao: string;
  cboid: {
    id: string;
    descricaosumaria: string
  }
  departamentoid: {
    id: number;
    nome: string;
    descricao: string;
  }
}
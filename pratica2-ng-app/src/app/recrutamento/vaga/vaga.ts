export interface Vaga {
  id: number;
  descricao: string;
  quantidade: number;
  prazo: Date;
  tipo: string;
  cargoid: {
    id: number;
    descricao: string;
  }
  departamentoid: {
    id: number;
    descricao: string;
  }
}
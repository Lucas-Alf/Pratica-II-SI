export interface FaixaTabelaSalarial {
  id: number;
  nivel: number;
  percentual: number;
  tabelasalarial: {
    id: number;
    descricao: string;
    valorbase: number;
  }
}
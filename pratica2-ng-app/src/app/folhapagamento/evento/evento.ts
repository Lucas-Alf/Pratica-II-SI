
export interface Evento {
  id: number;
  descricao: string;
  tipo: string;
  automatico: boolean;
  incidenciaId: {
    id: number;
    descricao: string
  }
  
}

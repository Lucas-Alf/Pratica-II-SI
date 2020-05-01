import { Incidencia } from '../incidencia/incidencia';

export interface Evento {
  id: number;
  descricao: string;
  tipo: string;
  automatico: boolean;
  incidenciaId: {
    id: number;
    descricao: string
  };
  rotinacalculoId: {
    id: number;
    descricao: string
  };
  incidenciasAtingidas: Incidencia[];
}

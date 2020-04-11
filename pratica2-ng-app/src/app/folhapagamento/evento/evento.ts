import { Incidencia } from '../incidencia/incidencia';

export interface Evento {
    id: number;
    descricao: string;
    tipo: string;
    incidencia: Incidencia
    automatico: boolean
  }
  
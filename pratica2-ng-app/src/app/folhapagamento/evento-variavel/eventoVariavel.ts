import { Contrato } from 'src/app/contratacao/funcionario/contrato';
import { Evento } from '../evento/evento';

export interface EventoVariavel {
  id: number;
  contratoMatricula: Contrato;
  evento: Evento;
  valor: number;
  data: Date;
  referencia: number;
}

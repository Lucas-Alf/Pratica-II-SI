import { PeriodoCalculo } from '../periodo-calculo/PeriodoCalculo';
import { Evento } from '../evento/evento';

export interface ParametroEmpresa {
  id: number;
  empresacnpj: string;
  periodocalculo: PeriodoCalculo;
  eventosalario: Evento;
  eventoinss: Evento;
  eventoirrf: Evento;
  eventofgts: Evento;
  eventohoraextra50: Evento;
  eventohoraextra100: Evento;
  eventototaldescontos: Evento;
  eventototalvencimentos: Evento;
  eventototalliquido: Evento;
}

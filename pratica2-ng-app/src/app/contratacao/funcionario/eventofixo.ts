import { Evento } from 'src/app/folhapagamento/evento/evento';

export interface EventoFixo {
    id: number;
    contratoMatricula: number;
    evento: Evento;
    valor: number;
    dataInicial: Date;
    dataFinal: Date;
    referencia: number;
}

package com.setrem.pratica2api.model;

public class CacheFolha {
    private Evento EventoSalarioBase;
    private Evento EventoInss;
    private Evento EventoIrrf;
    private Evento EventoFgts;
    private Evento EventoTotalVencimentos;
    private Evento EventoTotalDescontos;
    private Evento EventoTotalLiquido;

    public Evento getEventoInss() {
        return EventoInss;
    }

    public void setEventoInss(Evento eventoInss) {
        EventoInss = eventoInss;
    }

    public Evento getEventoIrrf() {
        return EventoIrrf;
    }

    public void setEventoIrrf(Evento eventoIrrf) {
        EventoIrrf = eventoIrrf;
    }

    public Evento getEventoFgts() {
        return EventoFgts;
    }

    public void setEventoFgts(Evento eventoFgts) {
        EventoFgts = eventoFgts;
    }

    public Evento getEventoTotalVencimentos() {
        return EventoTotalVencimentos;
    }

    public void setEventoTotalVencimentos(Evento eventoTotalVencimentos) {
        EventoTotalVencimentos = eventoTotalVencimentos;
    }

    public Evento getEventoTotalDescontos() {
        return EventoTotalDescontos;
    }

    public void setEventoTotalDescontos(Evento eventoTotalDescontos) {
        EventoTotalDescontos = eventoTotalDescontos;
    }

    public Evento getEventoTotalLiquido() {
        return EventoTotalLiquido;
    }

    public void setEventoTotalLiquido(Evento eventoTotalLiquido) {
        EventoTotalLiquido = eventoTotalLiquido;
    }

    public Evento getEventoSalarioBase() {
        return EventoSalarioBase;
    }

    public void setEventoSalarioBase(Evento eventoSalarioBase) {
        EventoSalarioBase = eventoSalarioBase;
    }
}
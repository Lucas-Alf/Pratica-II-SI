package com.setrem.pratica2api.model;

import java.util.List;

public class CacheFolha {
    private Evento EventoSalarioBase;
    private Evento EventoInss;
    private Evento EventoIrrf;
    private Evento EventoFgts;
    private Evento EventoTotalVencimentos;
    private Evento EventoTotalDescontos;
    private Evento EventoTotalLiquido;
    private Evento EventoHoraExtra100;
    private Evento EventoHoraExtra50;
    private PeriodoCalculo PeriodoCalculo;
    private List<Evento> EventosAutomaticos;

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

    public Evento getEventoHoraExtra100() {
        return EventoHoraExtra100;
    }

    public void setEventoHoraExtra100(Evento eventoHoraExtra100) {
        EventoHoraExtra100 = eventoHoraExtra100;
    }

    public Evento getEventoHoraExtra50() {
        return EventoHoraExtra50;
    }

    public void setEventoHoraExtra50(Evento eventoHoraExtra50) {
        EventoHoraExtra50 = eventoHoraExtra50;
    }

    public List<Evento> getEventosAutomaticos() {
        return EventosAutomaticos;
    }

    public void setEventosAutomaticos(List<Evento> eventosAutomaticos) {
        EventosAutomaticos = eventosAutomaticos;
    }

    public PeriodoCalculo getPeriodoCalculo() {
        return PeriodoCalculo;
    }

    public void setPeriodoCalculo(PeriodoCalculo periodoCalculo) {
        PeriodoCalculo = periodoCalculo;
    }
}
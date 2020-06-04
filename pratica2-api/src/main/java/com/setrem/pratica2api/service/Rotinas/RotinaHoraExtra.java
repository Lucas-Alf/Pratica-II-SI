package com.setrem.pratica2api.service.Rotinas;

import java.util.List;
import java.util.stream.Collectors;

import com.setrem.pratica2api.model.Contrato;
import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;

public class RotinaHoraExtra {
    public List<IncidenciaDTO> Calcula(EventoCalculoDTO evento, List<IncidenciaDTO> incidencias, Contrato contrato) {
        for (int incidenciaAtingida : evento.getIncidenciasAtingidas()) {
            if (incidencias.stream().filter(x -> x.getId() == incidenciaAtingida).collect(Collectors.toList())
                    .size() == 0) {
                var incidenciaTemp = new IncidenciaDTO();
                incidenciaTemp.setId(incidenciaAtingida);
                incidenciaTemp.setValor(0);
                incidencias.add(incidenciaTemp);
            }
            double valorIncidencia = 0;
            for (IncidenciaDTO incidencia : incidencias) {
                if (incidencia.getId() == evento.getIncidenciaid()) {
                    valorIncidencia += incidencia.getValor();
                }
            }

            double valorHora = valorIncidencia / contrato.getHorastrabalho();
            double valorHoraComBonus = valorHora * (evento.getPercentual() / 100);
            double valorHoraExtra = valorHoraComBonus * evento.getReferencia();

            // Arredonda casas decimais
            String result = String.format("%.2f", valorHoraExtra).replace(',', '.');
            valorHoraExtra = Double.parseDouble(result);
            evento.setValor(valorHoraExtra);

            for (IncidenciaDTO incidencia : incidencias) {
                if (incidencia.getId() == incidenciaAtingida) {
                    Double valorIncidenciaAtingida = incidencia.getValor();
                    incidencia.setValor(valorIncidenciaAtingida + valorHoraExtra);
                }
            }
        }
        return incidencias;
    }
}
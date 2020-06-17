package com.setrem.pratica2api.service.Rotinas;

import java.util.List;
import java.util.stream.Collectors;

import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;

public class RotinaPorcentagem {
    public List<IncidenciaDTO> Calcula(EventoCalculoDTO evento, List<IncidenciaDTO> incidencias) {
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

            double resultado = valorIncidencia * (evento.getReferencia() / 100.0);

            // Arredonda casas decimais
            String result = String.format("%.2f", resultado).replace(',', '.');
            resultado = Double.parseDouble(result);
            evento.setValor(resultado);
            evento.setReferencia(evento.getReferencia());

            for (IncidenciaDTO incidencia : incidencias) {
                if (incidencia.getId() == incidenciaAtingida) {
                    Double valorIncidenciaAtingida = incidencia.getValor();
                    incidencia.setValor(valorIncidenciaAtingida + resultado);
                }
            }
        }
        return incidencias;
    }
}
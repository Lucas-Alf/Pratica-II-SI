package com.setrem.pratica2api.service.Rotinas;

import java.util.List;
import java.util.stream.Collectors;

import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;

public class RotinaFGTS {
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
            // FGTS = 8% do Salário
            double valorFGTS = valorIncidencia * 0.08;

            // Arredonda casas decimais
            String result = String.format("%.2f", valorFGTS).replace(',', '.');
            valorFGTS = Double.parseDouble(result);
            evento.setValor(valorFGTS);
            evento.setReferencia(8.0);

            for (IncidenciaDTO incidencia : incidencias) {
                if (incidencia.getId() == incidenciaAtingida) {
                    Double valorIncidenciaAtingida = incidencia.getValor();
                    incidencia.setValor(valorIncidenciaAtingida + valorFGTS);
                }
            }
        }
        return incidencias;
    }
}
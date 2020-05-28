package com.setrem.pratica2api.service.Rotinas;

import java.util.List;
import java.util.stream.Collectors;

import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;

public class RotinaValorFixo {
    public List<IncidenciaDTO> Calcula(EventoCalculoDTO evento, List<IncidenciaDTO> incidencias) {
        for (int incidenciaAtingida : evento.getIncidenciasAtingidas()) {
            if (incidencias.stream().filter(x -> x.getId() == incidenciaAtingida).collect(Collectors.toList())
                    .size() > 0) {

                IncidenciaDTO incidenciaEncontrada = incidencias.stream().filter(x -> x.getId() == incidenciaAtingida)
                        .collect(Collectors.toList()).get(0);

                incidencias.remove(incidenciaEncontrada);
                if (evento.getTipo() == "V") {
                    incidenciaEncontrada.setValor(incidenciaEncontrada.getValor() + evento.getValor());
                } else {
                    incidenciaEncontrada.setValor(incidenciaEncontrada.getValor() - evento.getValor());
                }
                incidencias.add(incidenciaEncontrada);
            } else {
                var novaIncidencia = new IncidenciaDTO();
                novaIncidencia.setId(incidenciaAtingida);
                novaIncidencia.setValor(evento.getValor());
                incidencias.add(novaIncidencia);
            }
        }
        return incidencias;
    }
}
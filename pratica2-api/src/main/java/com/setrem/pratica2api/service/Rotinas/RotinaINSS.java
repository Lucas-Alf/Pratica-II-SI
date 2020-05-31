package com.setrem.pratica2api.service.Rotinas;

import java.util.List;
import java.util.stream.Collectors;

import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;

public class RotinaINSS {
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

            double valorINSS = 0;
            double valorAtualFaixa = valorIncidencia;

            // Teto limite
            if (valorIncidencia >= 6101.06) {
                valorINSS = 713.09;
            } else {
                // Calculo 1º Faixa
                var valorBaseFaixa = 1045;
                valorAtualFaixa -= valorBaseFaixa;
                var valorFaixa = valorBaseFaixa * (7.5 / 100);
                valorINSS += valorFaixa;

                // Calculo 2º Faixa
                var valorBaseFaixa2 = 2089.60 - 1045.01;
                if (valorAtualFaixa >= valorBaseFaixa2) {
                    valorAtualFaixa -= valorBaseFaixa2;
                    var valorFaixa2 = valorBaseFaixa2 * (9.0 / 100);
                    valorINSS += valorFaixa2;
                }

                // Calculo 3º Faixa
                var valorBaseFaixa3 = 3134.40 - 2089.61;
                if (valorAtualFaixa >= valorBaseFaixa3) {
                    valorAtualFaixa -= valorBaseFaixa3;
                    var valorFaixa3 = valorBaseFaixa3 * (12.0 / 100);
                    valorINSS += valorFaixa3;
                }

                // Calculo 4º Faixa
                if (valorAtualFaixa >= 0) {
                    var valorFaixa4 = valorAtualFaixa * (14.0 / 100);
                    valorINSS += valorFaixa4;
                }

                //Arredonda casas decimais
                String result = String.format("%.2f", valorINSS).replace(',', '.');
                valorINSS = Double.parseDouble(result);
            }

            evento.setValor(valorINSS);

            for (IncidenciaDTO incidencia : incidencias) {
                if (incidencia.getId() == incidenciaAtingida) {
                    Double valorIncidenciaAtingida = incidencia.getValor();
                    incidencia.setValor(valorIncidenciaAtingida + valorINSS);
                }
            }
        }
        return incidencias;
    }
}
package com.setrem.pratica2api.service.Rotinas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;

public class RotinaIRRF {
    public List<IncidenciaDTO> Calcula(Connection connection, Integer contrato, EventoCalculoDTO evento,
            List<IncidenciaDTO> incidencias) throws SQLException {
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

            PreparedStatement statement = connection.prepareStatement(
                    "select count(dependente.pessoacpf) as total from dependente inner join pessoa on pessoa.cpf = dependente.dependentecpf where pessoacpf =(select cpf from contrato where matricula = ?) and extract(year from age(CAST(now() AS date),pessoa.datanascimento)) <= 21");
            statement.setInt(1, contrato);
            ResultSet rs = statement.executeQuery();
            rs.next();
            var totalDependentes = Double.parseDouble(rs.getString("total"));
            for (int i = 0; i < totalDependentes; i++) {
                valorIncidencia -= 189.59;
            }

            double valorIRRF = 0;
            double valorMinimo = 1903.98;
            if (valorIncidencia > valorMinimo) {
                if (valorIncidencia > valorMinimo && valorIncidencia <= 2826.65) {
                    valorIRRF = (valorIncidencia * 7.5 / 100) - 142.80;
                    evento.setReferencia(7.5);
                } else if (valorIncidencia >= 2826.66 && valorIncidencia <= 3751.05) {
                    valorIRRF = (valorIncidencia * 15.0 / 100) - 354.80;
                    evento.setReferencia(15.0);
                } else if (valorIncidencia >= 3751.06 && valorIncidencia <= 4664.68) {
                    valorIRRF = (valorIncidencia * 22.5 / 100) - 636.13;
                    evento.setReferencia(22.5);
                } else if (valorIncidencia > 4664.68) {
                    valorIRRF = (valorIncidencia * 27.5 / 100) - 869.36;
                    evento.setReferencia(27.5);
                }
                // Arredonda casas decimais
                String result = String.format("%.2f", valorIRRF).replace(',', '.');
                valorIRRF = Double.parseDouble(result);
            }

            if (valorIRRF < 0) {
                valorIRRF = 0;
            }

            evento.setValor(valorIRRF);

            for (IncidenciaDTO incidencia : incidencias) {
                if (incidencia.getId() == incidenciaAtingida) {
                    Double valorIncidenciaAtingida = incidencia.getValor();
                    incidencia.setValor(valorIncidenciaAtingida + valorIRRF);
                }
            }

        }
        return incidencias;
    }
}
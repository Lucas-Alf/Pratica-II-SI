package com.setrem.pratica2api.service.Rotinas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.setrem.pratica2api.model.CacheFolha;
import com.setrem.pratica2api.model.Contrato;
import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;

public class RotinaSalario {
    public List<IncidenciaDTO> Calcula(Connection conexao, CacheFolha cache, EventoCalculoDTO evento, Contrato contrato,
            List<IncidenciaDTO> incidencias) throws SQLException {

        PreparedStatement statement = conexao.prepareStatement(
                "select ((percentual/100) * valorbase) as salario from faixatabelasalarial inner join tabelasalarial on tabelasalarial.id = faixatabelasalarial.tabelasalarialid where faixatabelasalarial.id = ( select faixatabelasalarialid from cargo where id = ( select cargoid from historicocargocontrato where matricula = ? and data <= cast(? as date) order by data desc, id desc limit 1) )");
        statement.setInt(1, contrato.getMatricula());
        statement.setString(2, cache.getPeriodoCalculo().getDataFinal().toString());
        ResultSet rs = statement.executeQuery();
        rs.next();
        var salario = Double.parseDouble(rs.getString("salario"));
        evento.setValor(salario);
        evento.setReferencia(contrato.getHorastrabalho());

        for (int incidenciaAtingida : evento.getIncidenciasAtingidas()) {
            if (incidencias.stream().filter(x -> x.getId() == incidenciaAtingida).collect(Collectors.toList())
                    .size() == 0) {
                var incidenciaTemp = new IncidenciaDTO();
                incidenciaTemp.setId(incidenciaAtingida);
                incidenciaTemp.setValor(0);
                incidencias.add(incidenciaTemp);
            }
            for (IncidenciaDTO incidencia : incidencias) {
                if (incidencia.getId() == incidenciaAtingida) {
                    Double valor = incidencia.getValor();
                    incidencia.setValor(valor + salario);
                }
            }
        }
        return incidencias;
    }
}
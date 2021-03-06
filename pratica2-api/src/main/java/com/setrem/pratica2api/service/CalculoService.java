package com.setrem.pratica2api.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.setrem.pratica2api.model.CacheFolha;
import com.setrem.pratica2api.model.Calculo;
import com.setrem.pratica2api.model.Contrato;
import com.setrem.pratica2api.model.Evento;
import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;
import com.setrem.pratica2api.model.Recibo;
import com.setrem.pratica2api.repository.CalculoRepository;
import com.setrem.pratica2api.repository.ContratoRepository;
import com.setrem.pratica2api.repository.EventoRepository;
import com.setrem.pratica2api.repository.ParametroEmpresaRepository;
import com.setrem.pratica2api.repository.ReciboRepository;
import com.setrem.pratica2api.service.Rotinas.RotinaFGTS;
import com.setrem.pratica2api.service.Rotinas.RotinaHoraExtra;
import com.setrem.pratica2api.service.Rotinas.RotinaINSS;
import com.setrem.pratica2api.service.Rotinas.RotinaIRRF;
import com.setrem.pratica2api.service.Rotinas.RotinaPorcentagem;
import com.setrem.pratica2api.service.Rotinas.RotinaSalario;
import com.setrem.pratica2api.service.Rotinas.RotinaValorFixo;

import org.springframework.stereotype.Service;

@Service
public class CalculoService {
    private CalculoRepository CalculoRepository;
    private ParametroEmpresaRepository ParametroEmpresaRepository;
    private EventoRepository EventoRepository;
    private ContratoRepository ContratoRepository;
    private ReciboRepository ReciboRepository;

    public CalculoService(CalculoRepository calculoRepository, ParametroEmpresaRepository parametroEmpresaRepository,
            EventoRepository eventoRepository, ContratoRepository contratoRepository,
            ReciboRepository reciboRepository) {
        this.CalculoRepository = calculoRepository;
        this.ParametroEmpresaRepository = parametroEmpresaRepository;
        this.EventoRepository = eventoRepository;
        this.ContratoRepository = contratoRepository;
        this.ReciboRepository = reciboRepository;
    }

    public void Calcular(Integer matricula) throws Exception {
        List<Integer> contratos = new ArrayList<Integer>();
        if (matricula == null) {
            ContratoRepository.RetornaParaFolhaDePagamento().forEach((x) -> {
                contratos.add(x.getMatricula());
            });
        } else {
            contratos.add(matricula);
        }

        var cache = criaCache();
        SessionFactory sessionFactory = new SessionFactory();
        Connection conexao = sessionFactory.OpenConnection();
        for (Integer contratoMatricula : contratos) {
            var contrato = ContratoRepository.findById(contratoMatricula).get();
            List<EventoCalculoDTO> eventosCalculo = buscaEventosContrato(conexao, contratoMatricula,
                    cache.getPeriodoCalculo().getDataInicial(), cache.getPeriodoCalculo().getDataFinal());

            List<IncidenciaDTO> incidencias = new ArrayList<IncidenciaDTO>();
            for (EventoCalculoDTO evento : eventosCalculo) {
                switch (evento.getRotinacalculoid()) {
                    case 1:
                        incidencias = new RotinaSalario().Calcula(conexao, cache, evento, contrato, incidencias);
                        break;
                    case 2:
                        incidencias = new RotinaIRRF().Calcula(conexao, contratoMatricula, evento, incidencias);
                        break;
                    case 3:
                        incidencias = new RotinaINSS().Calcula(evento, incidencias);
                        break;
                    case 4:
                        incidencias = new RotinaFGTS().Calcula(evento, incidencias);
                        break;
                    case 5:
                        incidencias = new RotinaValorFixo().Calcula(evento, incidencias);
                        break;
                    case 6:
                        incidencias = new RotinaHoraExtra().Calcula(evento, incidencias, contrato);
                        break;
                    case 7:
                        incidencias = new RotinaPorcentagem().Calcula(evento, incidencias);
                        break;
                    default:
                        throw new Exception("Rotina não implementada.");
                }
            }

            // Exclui Recibos e Cálculos Antigos
            var excluirStatement = conexao.createStatement();
            excluirStatement.executeUpdate("DELETE FROM recibo WHERE contratomatricula = " + contratoMatricula
                    + " AND \"data\" BETWEEN CAST('" + cache.getPeriodoCalculo().getDataInicial().toString()
                    + "' AS date) AND CAST('" + cache.getPeriodoCalculo().getDataFinal().toString()
                    + "' AS date) AND execucao = " + cache.getPeriodoCalculo().getExecucao());

            // Salva o recibo
            var contratoRecibo = new Contrato();
            contratoRecibo.setMatricula(contratoMatricula);
            var recibo = new Recibo();
            recibo.setContrato(contratoRecibo);
            recibo.setExecucao(cache.getPeriodoCalculo().getExecucao());
            recibo.setData(LocalDate.now());
            recibo.setDatapagamento(LocalDate.now());
            ReciboRepository.save(recibo);

            // Salva os cálculos do recibo
            for (EventoCalculoDTO evento : eventosCalculo) {
                var calculo = new Calculo();
                var eventoCalculo = new Evento();
                eventoCalculo.setId(evento.getId());
                calculo.setEvento(eventoCalculo);
                calculo.setRecibo(recibo);
                double valorCalculo = 0;

                // Soma total de vencimentos
                if (evento.getId() == cache.getEventoTotalVencimentos().getId()) {
                    double totalVencimentos = incidencias.stream()
                            .filter(x -> x.getId() == cache.getEventoTotalVencimentos().getIncidenciaId().getId())
                            .findFirst().get().getValor();
                    evento.setValor(totalVencimentos);
                    valorCalculo = evento.getValor();
                } else

                // Soma total de descontos
                if (evento.getId() == cache.getEventoTotalDescontos().getId()) {
                    double totalDescontos = incidencias.stream()
                            .filter(x -> x.getId() == cache.getEventoTotalDescontos().getIncidenciaId().getId())
                            .findFirst().get().getValor();
                    evento.setValor(totalDescontos);
                    valorCalculo = evento.getValor();
                } else

                // Total líquido
                if (evento.getId() == cache.getEventoTotalLiquido().getId()) {
                    double totalVencimentos = incidencias.stream()
                            .filter(x -> x.getId() == cache.getEventoTotalVencimentos().getIncidenciaId().getId())
                            .findFirst().get().getValor();
                    double totalDescontos = incidencias.stream()
                            .filter(x -> x.getId() == cache.getEventoTotalDescontos().getIncidenciaId().getId())
                            .findFirst().get().getValor();
                    evento.setValor(totalVencimentos - totalDescontos);
                    valorCalculo = evento.getValor();
                } else {
                    // Demais eventos
                    valorCalculo = evento.getValor();
                }
                calculo.setValor(valorCalculo);
                calculo.setReferencia(evento.getReferencia());
                CalculoRepository.save(calculo);
            }
        }
        sessionFactory.CloseConnection(conexao);
    }

    public CacheFolha criaCache() throws Exception {
        var parametrosEmpresa = ParametroEmpresaRepository.findById(1);
        if (!parametrosEmpresa.isPresent()) {
            throw new Exception("Empresa não possui parametros configurados.");
        }

        CacheFolha cache = new CacheFolha();
        cache.setEventoSalarioBase(parametrosEmpresa.get().getEventosalario());
        cache.setEventoFgts(parametrosEmpresa.get().getEventofgts());
        cache.setEventoInss(parametrosEmpresa.get().getEventoinss());
        cache.setEventoIrrf(parametrosEmpresa.get().getEventoirrf());
        cache.setEventoTotalDescontos(parametrosEmpresa.get().getEventototaldescontos());
        cache.setEventoTotalVencimentos(parametrosEmpresa.get().getEventototalvencimentos());
        cache.setEventoTotalLiquido(parametrosEmpresa.get().getEventototalliquido());
        cache.setEventoHoraExtra100(parametrosEmpresa.get().getEventohoraextra100());
        cache.setEventoHoraExtra50(parametrosEmpresa.get().getEventohoraextra50());
        cache.setPeriodoCalculo(parametrosEmpresa.get().getPeriodocalculo());
        cache.setEventosAutomaticos(EventoRepository.buscaEventosAutomaticos());
        return cache;
    }

    public List<Calculo> findByContrato(int matricula) {
        return CalculoRepository.findByContrato(matricula);
    }

    public List<EventoCalculoDTO> buscaEventosContrato(Connection conexao, int contrato, LocalDate dataInicial,
            LocalDate dataFinal) throws SQLException {
        PreparedStatement statement = conexao.prepareStatement(
                "SELECT * FROM( SELECT id, descricao, tipo, incidenciaid, automatico, rotinacalculoid, 0 AS valor, coalesce(percentual,0) as percentual, 0 AS referencia FROM evento WHERE automatico = TRUE UNION SELECT evento.id, evento.descricao, evento.tipo, evento.incidenciaid, FALSE AS automatico, evento.rotinacalculoid, coalesce(valor, 0) as valor, coalesce(evento.percentual,0) as percentual, coalesce(eventofixo.referencia,0) as referencia FROM eventofixo INNER JOIN evento ON evento.id = eventofixo.eventoid WHERE contratomatricula = ? AND (datainicial <= CAST(? AS date) AND (datafinal IS NULL OR datafinal >= CAST(? AS date))) UNION SELECT evento.id, evento.descricao, evento.tipo, evento.incidenciaid, FALSE AS automatico, evento.rotinacalculoid, coalesce(valor,0) as valor, coalesce(evento.percentual,0) as percentual, coalesce(referencia,0) as referencia FROM eventovariavel INNER JOIN evento ON evento.id = eventovariavel.eventoid WHERE contratomatricula = ? AND DATA BETWEEN CAST(? AS date) AND CAST(? AS date)) AS eventoscontrato ORDER BY eventoscontrato.tipo DESC, eventoscontrato.id ASC");
        statement.setInt(1, contrato);
        statement.setString(2, dataInicial.toString());
        statement.setString(3, dataFinal.toString());
        statement.setInt(4, contrato);
        statement.setString(5, dataInicial.toString());
        statement.setString(6, dataFinal.toString());
        ResultSet rs = statement.executeQuery();
        List<EventoCalculoDTO> eventos = new ArrayList<EventoCalculoDTO>();
        while (rs.next()) {
            EventoCalculoDTO evento = new EventoCalculoDTO();
            evento.setId(Integer.parseInt(rs.getString("id")));
            evento.setDescricao(rs.getString("descricao"));
            evento.setTipo(rs.getString("tipo"));
            evento.setIncidenciaid(Integer.parseInt(rs.getString("incidenciaid")));
            evento.setAutomatico(Boolean.parseBoolean(rs.getString("automatico")));
            evento.setValor(Double.parseDouble(rs.getString("valor")));
            evento.setRotinacalculoid(Integer.parseInt(rs.getString("rotinacalculoid")));
            evento.setPercentual(Double.parseDouble(rs.getString("percentual")));
            evento.setReferencia(Double.parseDouble(rs.getString("referencia")));

            PreparedStatement statementIncidenciasAtingidas = conexao.prepareStatement(
                    "select * from incidencia where id in (select incidenciaid from incidenciaevento where incidenciaevento.eventoid = ?)");
            statementIncidenciasAtingidas.setInt(1, evento.getId());
            ResultSet rsIncidenciasAtingidas = statementIncidenciasAtingidas.executeQuery();
            var listaIncidenciasAtingidas = new ArrayList<Integer>();
            while (rsIncidenciasAtingidas.next()) {
                listaIncidenciasAtingidas.add(Integer.parseInt(rsIncidenciasAtingidas.getString("id")));
            }
            evento.setIncidenciasAtingidas(listaIncidenciasAtingidas);
            eventos.add(evento);
        }
        return eventos;
    }
}

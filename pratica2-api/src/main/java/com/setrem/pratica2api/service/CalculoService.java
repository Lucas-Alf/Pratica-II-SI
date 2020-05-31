package com.setrem.pratica2api.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.setrem.pratica2api.model.CacheFolha;
import com.setrem.pratica2api.model.Calculo;
import com.setrem.pratica2api.model.Contrato;
import com.setrem.pratica2api.model.Evento;
import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.Incidencia;
import com.setrem.pratica2api.model.IncidenciaDTO;
import com.setrem.pratica2api.model.Recibo;
import com.setrem.pratica2api.repository.CalculoRepository;
import com.setrem.pratica2api.repository.ContratoRepository;
import com.setrem.pratica2api.repository.EventoFixoRepository;
import com.setrem.pratica2api.repository.EventoRepository;
import com.setrem.pratica2api.repository.EventoVariavelRepository;
import com.setrem.pratica2api.repository.ParametroEmpresaRepository;
import com.setrem.pratica2api.repository.ReciboRepository;
import com.setrem.pratica2api.service.Rotinas.RotinaFGTS;
import com.setrem.pratica2api.service.Rotinas.RotinaINSS;
import com.setrem.pratica2api.service.Rotinas.RotinaIRRF;
import com.setrem.pratica2api.service.Rotinas.RotinaSalario;
import com.setrem.pratica2api.service.Rotinas.RotinaValorFixo;

import org.springframework.stereotype.Service;

@Service
public class CalculoService {
    private CalculoRepository CalculoRepository;
    private ParametroEmpresaRepository ParametroEmpresaRepository;
    private EventoRepository EventoRepository;
    private EventoFixoRepository EventoFixoRepository;
    private EventoVariavelRepository EventoVariavelRepository;
    private ContratoRepository ContratoRepository;
    private ReciboRepository ReciboRepository;

    public CalculoService(CalculoRepository calculoRepository, ParametroEmpresaRepository parametroEmpresaRepository,
            EventoRepository eventoRepository, EventoFixoRepository eventoFixoRepository,
            EventoVariavelRepository eventoVariavelRepository, ContratoRepository contratoRepository,
            ReciboRepository reciboRepository) {
        this.CalculoRepository = calculoRepository;
        this.ParametroEmpresaRepository = parametroEmpresaRepository;
        this.EventoRepository = eventoRepository;
        this.EventoFixoRepository = eventoFixoRepository;
        this.EventoVariavelRepository = eventoVariavelRepository;
        this.ContratoRepository = contratoRepository;
        this.ReciboRepository = reciboRepository;
    }

    public void Calcular(Integer matricula) throws Exception {
        List<Integer> contratos = new ArrayList<Integer>();
        if (matricula == null) {
            ContratoRepository.findAll().forEach((x) -> {
                contratos.add(x.getMatricula());
            });
        } else {
            contratos.add(matricula);
        }

        var cache = criaCache();
        Connection conexao = openConnection();
        for (Integer contrato : contratos) {

            List<EventoCalculoDTO> eventosCalculo = buscaEventosContrato(conexao, contrato,
                    cache.getPeriodoCalculo().getDataInicial(), cache.getPeriodoCalculo().getDataFinal());

            List<IncidenciaDTO> incidencias = new ArrayList<IncidenciaDTO>();
            for (EventoCalculoDTO evento : eventosCalculo) {
                switch (evento.getRotinacalculoid()) {
                    case 1:
                        incidencias = new RotinaSalario().Calcula(conexao, cache, evento, contrato, incidencias);
                        break;
                    case 2:
                        incidencias = new RotinaIRRF().Calcula(conexao, contrato, evento, incidencias);
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
                    default:
                        throw new Exception("Rotina não implementada.");
                }
            }
            // Salva o recibo
            var contratoRecibo = new Contrato();
            contratoRecibo.setMatricula(contrato);
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
                CalculoRepository.save(calculo);
            }
        }
        closeConnection(conexao);
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

    private Connection openConnection() {
        try {
            String url = "jdbc:postgresql://ec2-174-129-33-147.compute-1.amazonaws.com:5432/d7k37oahur5ovg";
            String usuario = "gidmcpeqjmjkfo";
            String senha = "8a34bc37e8bd2458c6ee70af09a074ca1f571f740d8394790de98eda83127a29";
            return DriverManager.getConnection(url, usuario, senha);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private void closeConnection(Connection conexao) {
        try {
            conexao.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public List<EventoCalculoDTO> buscaEventosContrato(Connection conexao, int contrato, LocalDate dataInicial,
            LocalDate dataFinal) throws SQLException {
        PreparedStatement statement = conexao.prepareStatement(
                "select * from( select id, descricao, tipo, incidenciaid, automatico, rotinacalculoid, 0 as valor from evento where automatico = true union select evento.id, evento.descricao, evento.tipo, evento.rotinacalculoid, false as automatico, evento.rotinacalculoid, valor from eventofixo inner join evento on evento.id = eventofixo.eventoid where contratomatricula = ? and (datainicial <= cast(? as date) and (datafinal is null or datafinal >= cast(? as date))) union select evento.id, evento.descricao, evento.tipo, evento.rotinacalculoid, false as automatico, evento.rotinacalculoid, valor from eventovariavel inner join evento on evento.id = eventovariavel.eventoid where contratomatricula = ? and data between cast(? as date) and cast(? as date)) as eventoscontrato order by eventoscontrato.tipo desc, eventoscontrato.id asc");
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

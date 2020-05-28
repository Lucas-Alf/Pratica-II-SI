package com.setrem.pratica2api.service;

import java.util.ArrayList;
import java.util.List;

import com.setrem.pratica2api.model.CacheFolha;
import com.setrem.pratica2api.model.Calculo;
import com.setrem.pratica2api.model.EventoCalculoDTO;
import com.setrem.pratica2api.model.IncidenciaDTO;
import com.setrem.pratica2api.repository.CalculoRepository;
import com.setrem.pratica2api.repository.ContratoRepository;
import com.setrem.pratica2api.repository.EventoFixoRepository;
import com.setrem.pratica2api.repository.EventoRepository;
import com.setrem.pratica2api.repository.EventoVariavelRepository;
import com.setrem.pratica2api.repository.ParametroEmpresaRepository;
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

    public CalculoService(CalculoRepository calculoRepository, ParametroEmpresaRepository parametroEmpresaRepository,
            EventoRepository eventoRepository, EventoFixoRepository eventoFixoRepository,
            EventoVariavelRepository eventoVariavelRepository, ContratoRepository contratoRepository) {
        this.CalculoRepository = calculoRepository;
        this.ParametroEmpresaRepository = parametroEmpresaRepository;
        this.EventoRepository = eventoRepository;
        this.EventoFixoRepository = eventoFixoRepository;
        this.EventoVariavelRepository = eventoVariavelRepository;
        this.ContratoRepository = contratoRepository;
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
        for (Integer contrato : contratos) {
            List<EventoCalculoDTO> eventosCalculo = EventoRepository.buscaEventosContrato(contrato,
                    cache.getPeriodoCalculo().getDataInicial(), cache.getPeriodoCalculo().getDataFinal());

            List<IncidenciaDTO> incidencias = new ArrayList<IncidenciaDTO>();
            for (EventoCalculoDTO evento : eventosCalculo) {
                switch (evento.getRotinacalculoid()) {
                    case 1:
                        incidencias = new RotinaValorFixo().Calcula(evento, incidencias);
                        break;
                    case 5:
                        incidencias = new RotinaValorFixo().Calcula(evento, incidencias);
                        break;
                    default:
                        throw new Exception("Rotina não implementada.");
                }
            }
        }
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
}

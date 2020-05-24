package com.setrem.pratica2api.service;

import java.util.ArrayList;
import java.util.List;

import com.setrem.pratica2api.model.CacheFolha;
import com.setrem.pratica2api.model.ParametroEmpresa;
import com.setrem.pratica2api.repository.CalculoRepository;
import com.setrem.pratica2api.repository.ParametroEmpresaRepository;

public class CalculoService {
    private CalculoRepository CalculoRepository;
    private ParametroEmpresaRepository ParametroEmpresaRepository;

    public CalculoService(CalculoRepository calculoRepository, ParametroEmpresaRepository parametroEmpresaRepository) {
        this.CalculoRepository = calculoRepository;
        this.ParametroEmpresaRepository = parametroEmpresaRepository;
    }

    public void Calcular(Integer matricula) throws Exception {
        List<Integer> contratos = new ArrayList<Integer>();
        if (matricula == null) {

        } else {
            contratos.add(matricula);
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
        // cache.setEventoTotalDescontos(parametrosEmpresa.get().getEvent());
        return cache;
    }
}

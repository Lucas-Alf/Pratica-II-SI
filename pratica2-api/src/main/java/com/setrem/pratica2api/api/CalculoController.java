package com.setrem.pratica2api.api;

import org.springframework.web.bind.annotation.*;

import com.setrem.pratica2api.model.Calculo;
import com.setrem.pratica2api.repository.CalculoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/calculo")
@CrossOrigin
public class CalculoController {
    private CalculoRepository calculoRepository;

    public CalculoController(CalculoRepository calculoRepository) {
        this.calculoRepository = calculoRepository;
    }

    @GetMapping("/buscaPorContrato")
    public List<Calculo> buscaPorContrato(int matricula) {
        var calculos = this.calculoRepository.findByContrato(matricula);
        return calculos;
    }

    @PostMapping("/calcular")
    public List<Calculo> calcular(int matricula) {
        var calculos = this.calculoRepository.findByContrato(matricula);
        return calculos;
    }
}
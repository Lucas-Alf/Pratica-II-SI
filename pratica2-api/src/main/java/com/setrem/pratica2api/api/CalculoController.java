package com.setrem.pratica2api.api;

import org.springframework.web.bind.annotation.*;

import com.setrem.pratica2api.model.Calculo;
import com.setrem.pratica2api.repository.CalculoRepository;
import com.setrem.pratica2api.service.CalculoService;

import java.util.List;

@RestController
@RequestMapping("/api/calculo")
@CrossOrigin
public class CalculoController {
    private CalculoService CalculoService;

    public CalculoController(CalculoService calculoService) {
        this.CalculoService = calculoService;
    }

    @GetMapping("/buscaPorContrato")
    public List<Calculo> buscaPorContrato(int matricula) {
        var calculos = this.CalculoService.findByContrato(matricula);
        return calculos;
    }

    @GetMapping("/calcular")
    public void calcular(int matricula) throws Exception {
        this.CalculoService.Calcular(matricula);
    }
}
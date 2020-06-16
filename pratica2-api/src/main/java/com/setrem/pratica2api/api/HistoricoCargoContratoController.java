package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.HistoricoCargoContrato;
import com.setrem.pratica2api.repository.HistoricoCargoContratoRepository;

@RestController
@RequestMapping("/api/historicocargocontrato")
@CrossOrigin
public class HistoricoCargoContratoController {
    private HistoricoCargoContratoRepository historicoCargoContratoRepository;

    public HistoricoCargoContratoController(HistoricoCargoContratoRepository historicoCargoContratoRepository) {
        this.historicoCargoContratoRepository = historicoCargoContratoRepository;
    }

    @GetMapping("/all")
    public List<HistoricoCargoContrato> all() {
        var cargoconhecimentos = this.historicoCargoContratoRepository.findAll();
        return cargoconhecimentos;
    }

    @GetMapping("/allMatricula")
    public List<HistoricoCargoContrato> allMatricula(int matricula) {
        var cargoconhecimentos = this.historicoCargoContratoRepository.findByMatricula(matricula);
        return cargoconhecimentos;
    }

    @PostMapping("/Incluir")
    public HistoricoCargoContrato save(@RequestBody HistoricoCargoContrato data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.historicoCargoContratoRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.historicoCargoContratoRepository.deleteById(id);
    }

}
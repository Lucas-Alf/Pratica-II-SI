package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.IncidenciaEvento;
import com.setrem.pratica2api.repository.IncidenciaEventoRepository;

@RestController
@RequestMapping("/api/incidenciaevento")
@CrossOrigin
public class IncidenciaEventoController {
    private IncidenciaEventoRepository incidenciaeventoRepository;

    public IncidenciaEventoController(IncidenciaEventoRepository incidenciaeventoRepository) {
        this.incidenciaeventoRepository = incidenciaeventoRepository;
    }

    @GetMapping("/all")
    public List<IncidenciaEvento> all() {
        var incidenciaeventos = this.incidenciaeventoRepository.findAll();
        return incidenciaeventos;
    }

    @PostMapping
    public IncidenciaEvento save(@RequestBody IncidenciaEvento data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.incidenciaeventoRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.incidenciaeventoRepository.deleteById(id);
    }

}
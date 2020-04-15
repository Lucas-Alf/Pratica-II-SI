package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import javax.websocket.server.PathParam;

import java.util.List;

import com.setrem.pratica2api.model.Incidencia;
import com.setrem.pratica2api.repository.IncidenciaRepository;

@RestController
@RequestMapping("/api/incidencia")
@CrossOrigin
public class IncidenciaController {
    private IncidenciaRepository incidenciaRepository;

    public IncidenciaController(IncidenciaRepository incidenciaRepository) {
        this.incidenciaRepository = incidenciaRepository;
    }

    @GetMapping("/all")
    public List<Incidencia> all() {
        var incidencias = this.incidenciaRepository.findAll();
        return incidencias;
    }

    @PostMapping("/save")
    public Incidencia save(@RequestBody Incidencia data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.incidenciaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.incidenciaRepository.deleteById(id);
    }
}
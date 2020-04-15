package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Evento;
import com.setrem.pratica2api.repository.EventoRepository;

@RestController
@RequestMapping("/api/evento")
@CrossOrigin
public class EventoController {
    private EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @GetMapping("/all")
    public List<Evento> all() {
        var eventos = this.eventoRepository.findAll();
        return eventos;
    }

    @PostMapping
    public Evento save(@RequestBody Evento data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.eventoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.eventoRepository.deleteById(id);
    }

}
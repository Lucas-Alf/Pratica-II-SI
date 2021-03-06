package com.setrem.pratica2api.api;

import java.util.List;

import javax.validation.ValidationException;

import com.setrem.pratica2api.model.EventoVariavel;
import com.setrem.pratica2api.repository.EventoVariavelRepository;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventovariavel")
@CrossOrigin
public class EventoVariavelController {
    private EventoVariavelRepository EventoVariavelRepository;

    public EventoVariavelController(EventoVariavelRepository eventoVariavelRepository) {
        this.EventoVariavelRepository = eventoVariavelRepository;
    }

    @GetMapping("/all")
    public List<EventoVariavel> all() {
        var eventos = this.EventoVariavelRepository.findAllByOrderByIdAsc();
        return eventos;
    }

    @PostMapping("/Incluir")
    public EventoVariavel add(@RequestBody EventoVariavel data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.EventoVariavelRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public EventoVariavel update(@RequestBody EventoVariavel data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.EventoVariavelRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.EventoVariavelRepository.deleteById(id);
    }
}
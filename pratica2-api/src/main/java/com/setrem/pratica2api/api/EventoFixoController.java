package com.setrem.pratica2api.api;

import java.util.List;

import javax.validation.ValidationException;

import com.setrem.pratica2api.model.EventoFixo;
import com.setrem.pratica2api.repository.EventoFixoRepository;

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
import com.setrem.pratica2api.repository.ContratoRepository;
import com.setrem.pratica2api.repository.EventoRepository;

@RestController
@RequestMapping("/api/eventofixo")
@CrossOrigin
public class EventoFixoController {
    private EventoFixoRepository EventoFixoRepository;

    public EventoFixoController(EventoFixoRepository eventoFixoRepository,
            ContratoRepository contratoRepository, EventoRepository eventoRepository) {
        this.EventoFixoRepository = eventoFixoRepository;
    }

    @GetMapping("/all")
    public List<EventoFixo> all() {
        var eventos = this.EventoFixoRepository.findAllByOrderByIdAsc();
        return eventos;
    }

    @PostMapping("/Incluir")
    public EventoFixo add(@RequestBody EventoFixo data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.EventoFixoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public EventoFixo update(@RequestBody EventoFixo data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.EventoFixoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.EventoFixoRepository.deleteById(id);
    }
}
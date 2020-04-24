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

    @PostMapping("/Incluir")
    public Evento add(@RequestBody Evento data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.eventoRepository.findById(data.getId());
        if (jaExistente.isPresent()) {
            throw new Exception("Já existe um evento com este código.");
        } else {
            data = this.eventoRepository.save(data);
            return data;
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Evento update(@RequestBody Evento data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.eventoRepository.findById(data.getId());
        if (!jaExistente.isPresent()) {
            throw new Exception("Evento com código " + data.getId() + " não encontrado.");
        } else {
            jaExistente.get().setDescricao(data.getDescricao());
            jaExistente.get().setTipo(data.getTipo());
            jaExistente.get().setIncidenciaId(data.getIncidenciaId());
            this.eventoRepository.save(jaExistente.get());
            return jaExistente.get();
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.eventoRepository.deleteById(id);
    }

}
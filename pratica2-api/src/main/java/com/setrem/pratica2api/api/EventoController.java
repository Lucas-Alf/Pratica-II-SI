package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.setrem.pratica2api.model.Evento;
import com.setrem.pratica2api.model.Incidencia;
import com.setrem.pratica2api.model.IncidenciaEvento;
import com.setrem.pratica2api.repository.EventoRepository;
import com.setrem.pratica2api.repository.IncidenciaEventoRepository;

@RestController
@RequestMapping("/api/evento")
@CrossOrigin
public class EventoController {
    private EventoRepository eventoRepository;
    private IncidenciaEventoRepository incidenciasEventoRepository;

    public EventoController(EventoRepository eventoRepository, IncidenciaEventoRepository incidenciaEventoRepository) {
        this.eventoRepository = eventoRepository;
        this.incidenciasEventoRepository = incidenciaEventoRepository;
    }

    @GetMapping("/all")
    public List<Evento> all() {
        var eventos = this.eventoRepository.findAllByOrderByIdAsc();
        for (Evento evento : eventos) {
            var incidenciasEvento = incidenciasEventoRepository.findByEventoId(evento.getId());
            if (incidenciasEvento.size() > 0) {
                evento.setIncidenciasAtingidas(
                        incidenciasEvento.stream().map(x -> x.getIncidencia()).toArray(Incidencia[]::new));
            }
        }
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
            var incidenciasAtingidasTemp = data.getIncidenciasAtingidas();
            data = this.eventoRepository.save(data);
            for (Incidencia incidencia : incidenciasAtingidasTemp) {
                var incidenciaEvento = new IncidenciaEvento();
                incidenciaEvento.setIncidencia(incidencia);
                incidenciaEvento.setEvento(data);
                incidenciasEventoRepository.save(incidenciaEvento);
            }
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
            this.eventoRepository.save(data);
            incidenciasEventoRepository.deleteByEventoId(data.getId());
            for (Incidencia incidencia : data.getIncidenciasAtingidas()) {
                var incidenciaEvento = new IncidenciaEvento();
                incidenciaEvento.setIncidencia(incidencia);
                incidenciaEvento.setEvento(data);
                incidenciasEventoRepository.save(incidenciaEvento);
            }
            return data;
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        incidenciasEventoRepository.deleteByEventoId(id);
        this.eventoRepository.deleteById(id);
    }
}
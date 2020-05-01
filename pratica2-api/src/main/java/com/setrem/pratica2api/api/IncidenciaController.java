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
        var incidencias = this.incidenciaRepository.findAllByOrderByIdAsc();
        return incidencias;
    }

    @PostMapping("/Incluir")
    public Incidencia add(@RequestBody Incidencia data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.incidenciaRepository.findById(data.getId());
        if (jaExistente.isPresent()) {
            throw new Exception("Já existe uma incidencia com este código.");
        } else {
            data = this.incidenciaRepository.save(data);
            return data;
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Incidencia update(@RequestBody Incidencia data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.incidenciaRepository.findById(data.getId());
        if (!jaExistente.isPresent()) {
            throw new Exception("Incidencia com código " + data.getId() + " não encontrada.");
        } else {
            jaExistente.get().setDescricao(data.getDescricao());
            this.incidenciaRepository.save(jaExistente.get());
            return jaExistente.get();
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.incidenciaRepository.deleteById(id);
    }
}
package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

import com.setrem.pratica2api.model.Nivel;
import com.setrem.pratica2api.repository.NivelRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;



@RestController
@RequestMapping("/api/nivel")
@CrossOrigin
public class NivelController {
    private NivelRepository nivelRepository;

    public NivelController(NivelRepository nivelRepository) {
        this.nivelRepository = nivelRepository;
    }

    @GetMapping("/all")
    public List<Nivel> all() {
        var niveis = this.nivelRepository.findAllByOrderByIdAsc();
        return niveis;
    }

    @PostMapping("/Incluir")
    public Nivel add(@RequestBody Nivel data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.nivelRepository.findById(data.getId());
        if (jaExistente.isPresent()) {
            throw new Exception("Já existe um nível com este código.");
        } else {
            data = this.nivelRepository.save(data);
            return data;
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Nivel update(@RequestBody Nivel data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.nivelRepository.findById(data.getId());
        if (!jaExistente.isPresent()) {
            throw new Exception("Evento com código " + data.getId() + " não encontrado.");
        } else {
            data = this.nivelRepository.save(data);
            return data;
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.nivelRepository.deleteById(id);
    }
}
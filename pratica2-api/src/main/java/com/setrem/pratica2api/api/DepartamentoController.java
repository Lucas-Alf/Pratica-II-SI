package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Departamento;
import com.setrem.pratica2api.repository.DepartamentoRepository;;

@RestController
@RequestMapping("/api/departamento")
@CrossOrigin
public class DepartamentoController {
    private DepartamentoRepository DepartamentoRepository;

    public DepartamentoController(DepartamentoRepository DepartamentoRepository) {
        this.DepartamentoRepository = DepartamentoRepository;
    }

    @GetMapping("/all")
    public List<Departamento> all() {
        var departamentos = this.DepartamentoRepository.findAll();
        return departamentos;
    }

    @PostMapping
    public Departamento save(@RequestBody Departamento data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.DepartamentoRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.DepartamentoRepository.deleteById(id);
    }

}
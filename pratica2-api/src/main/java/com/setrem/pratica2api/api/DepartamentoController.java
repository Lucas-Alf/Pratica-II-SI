package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Departamento;
import com.setrem.pratica2api.repository.DepartamentoRepository;

@RestController
@RequestMapping("/api/departamento")
@CrossOrigin
public class DepartamentoController {
    private DepartamentoRepository DepartamentoRepository;
    private Departamento dep = new Departamento();

    public DepartamentoController(DepartamentoRepository DepartamentoRepository) {
        this.DepartamentoRepository = DepartamentoRepository;
    }

    @GetMapping("/all")
    public List<Departamento> all() {
        var departamentos = this.DepartamentoRepository.findAll();
        return departamentos;
    }

    @PostMapping("/Incluir")
    public Departamento add(@RequestBody Departamento data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.DepartamentoRepository.maxIdDepartamento();
        this.dep.setId(id);
        data = this.DepartamentoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Departamento update(@RequestBody Departamento data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
            this.DepartamentoRepository.save(data);
            return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.DepartamentoRepository.deleteById(id);
    }

}
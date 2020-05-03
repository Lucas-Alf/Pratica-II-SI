package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.ParametroEmpresa;
import com.setrem.pratica2api.repository.ParametroEmpresaRepository;;

@RestController
@RequestMapping("/api/parametroempresa")
@CrossOrigin
public class ParametroEmpresaController {
    private ParametroEmpresaRepository ParametroEmpresaRepository;

    public ParametroEmpresaController(ParametroEmpresaRepository ParametroEmpresaRepository) {
        this.ParametroEmpresaRepository = ParametroEmpresaRepository;
    }

    @GetMapping("/all")
    public List<ParametroEmpresa> all() {
        var parametroEmpresas = this.ParametroEmpresaRepository.findAll();
        return parametroEmpresas;
    }

    @PostMapping
    public ParametroEmpresa save(@RequestBody ParametroEmpresa data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.ParametroEmpresaRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        this.ParametroEmpresaRepository.deleteById(id);
    }
}

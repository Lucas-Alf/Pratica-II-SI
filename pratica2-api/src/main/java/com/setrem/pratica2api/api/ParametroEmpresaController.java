package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import com.setrem.pratica2api.model.ParametroEmpresa;
import com.setrem.pratica2api.repository.EmpresaRepository;
import com.setrem.pratica2api.repository.ParametroEmpresaRepository;;

@RestController
@RequestMapping("/api/parametroempresa")
@CrossOrigin
public class ParametroEmpresaController {
    private ParametroEmpresaRepository ParametroEmpresaRepository;
    private EmpresaRepository EmpresaRepository;

    public ParametroEmpresaController(ParametroEmpresaRepository parametroEmpresaRepository,
            EmpresaRepository empresaRepository) {
        this.ParametroEmpresaRepository = parametroEmpresaRepository;
        this.EmpresaRepository = empresaRepository;
    }

    @GetMapping("/all")
    public List<ParametroEmpresa> all() {
        var parametroEmpresas = this.ParametroEmpresaRepository.findAll();
        return parametroEmpresas;
    }

    @GetMapping("/get")
    public Optional<ParametroEmpresa> get() {
        var parametroEmpresas = this.ParametroEmpresaRepository.findById(1);
        return parametroEmpresas;
    }

    @PostMapping("/save")
    public ParametroEmpresa save(@RequestBody ParametroEmpresa data, BindingResult bindingResult) {
        data.setId(1);
        data.setEmpresacnpj(EmpresaRepository.findAll().get(0).getCnpj());
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

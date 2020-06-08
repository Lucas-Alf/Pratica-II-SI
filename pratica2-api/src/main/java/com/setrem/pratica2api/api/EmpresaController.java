package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Empresa;
import com.setrem.pratica2api.repository.EmpresaRepository;;

@RestController
@RequestMapping("/api/empresa")
@CrossOrigin
public class EmpresaController {
    private EmpresaRepository EmpresaRepository;
    private Empresa emp = new Empresa();

    public EmpresaController(EmpresaRepository EmpresaRepository) {
        this.EmpresaRepository = EmpresaRepository;
    }

    @GetMapping("/all") // Teste: http://localhost:8080/api/empresa/all
    public List<Empresa> all() {
        var empresas = this.EmpresaRepository.findAll();
        return empresas;
    }

    @PostMapping("/Incluir")
    public Empresa add(@RequestBody Empresa data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.EmpresaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Empresa update(@RequestBody Empresa data, BindingResult bindingResult) throws Exception {
        Empresa empresa = this.EmpresaRepository.findAll().get(0);
        if (empresa != null) data.setCnpj(empresa.getCnpj());
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.EmpresaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        this.EmpresaRepository.deleteById(id);
    }
}

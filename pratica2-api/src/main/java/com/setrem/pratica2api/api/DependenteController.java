package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Dependente;
import com.setrem.pratica2api.repository.DependenteRepository;

@RestController
@RequestMapping("/api/dependente")
@CrossOrigin
public class DependenteController {
    private DependenteRepository DependenteRepository;
    private Dependente dep = new Dependente();

    public DependenteController(DependenteRepository DependenteRepository) {
        this.DependenteRepository = DependenteRepository;
    }

    @GetMapping("/findDepedentesCpf")
    public List<Dependente> all(String cpf) {
        var de = this.DependenteRepository.findDepedentesCpf(cpf);
        return de;
    }
    @GetMapping("/all") 
    public List<Dependente> all() {
        return this.DependenteRepository.findAll();
    }
    @PostMapping("/Incluir")
    public Dependente add(@RequestBody Dependente data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.DependenteRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Dependente update(@RequestBody Dependente data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
            this.DependenteRepository.save(data);
            return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.DependenteRepository.deleteById(id);
    }

}
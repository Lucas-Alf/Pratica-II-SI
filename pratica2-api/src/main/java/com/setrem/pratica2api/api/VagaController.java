package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Vaga;
import com.setrem.pratica2api.repository.VagaRepository;;

@RestController
@RequestMapping("/api/vaga")
@CrossOrigin
public class VagaController {
    private VagaRepository VagaRepository;

    public VagaController(VagaRepository VagaRepository) {
        this.VagaRepository = VagaRepository;
    }

    @GetMapping("/all")
    public List<Vaga> all() {
        var vagas = this.VagaRepository.findAll();
        return vagas;
    }

    @PostMapping
    public Vaga save(@RequestBody Vaga data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.VagaRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.VagaRepository.deleteById(id);
    }

}
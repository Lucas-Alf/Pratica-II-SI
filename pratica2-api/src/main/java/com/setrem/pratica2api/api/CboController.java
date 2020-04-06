package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Cbo;
import com.setrem.pratica2api.repository.CboRepository;;

@RestController
@RequestMapping("/api/cbo")
@CrossOrigin
public class CboController {
    private CboRepository CboRepository;

    public CboController(CboRepository CboRepository) {
        this.CboRepository = CboRepository;
    }

    @GetMapping("/all")
    public List<Cbo> all() {
        var cbos = this.CboRepository.findAll();
        return cbos;
    }

    @PostMapping
    public Cbo save(@RequestBody Cbo data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.CboRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.CboRepository.deleteById(id);
    }
}

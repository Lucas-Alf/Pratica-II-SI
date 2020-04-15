package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Conhecimento;
import com.setrem.pratica2api.repository.ConhecimentoRepository;;

@RestController
@RequestMapping("/api/conhecimento")
@CrossOrigin
public class ConhecimentoController {
    private ConhecimentoRepository ConhecimentoRepository;

    public ConhecimentoController(ConhecimentoRepository ConhecimentoRepository) {
        this.ConhecimentoRepository = ConhecimentoRepository;
    }

    @GetMapping("/all")
    public List<Conhecimento> all() {
        var conhecimentos = this.ConhecimentoRepository.findAll();
        return conhecimentos;
    }

    @PostMapping
    public Conhecimento save(@RequestBody Conhecimento data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.ConhecimentoRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.ConhecimentoRepository.deleteById(id);
    }

}
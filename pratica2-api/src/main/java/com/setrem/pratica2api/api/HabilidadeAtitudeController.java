package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.HabilidadeAtitude;
import com.setrem.pratica2api.repository.HabilidadeAtitudeRepository;;

@RestController
@RequestMapping("/api/habilidadeatitude")
@CrossOrigin
public class HabilidadeAtitudeController {
    private HabilidadeAtitudeRepository HabilidadeAtitudeRepository;

    public HabilidadeAtitudeController(HabilidadeAtitudeRepository HabilidadeAtitudeRepository) {
        this.HabilidadeAtitudeRepository = HabilidadeAtitudeRepository;
    }

    @GetMapping("/all")
    public List<HabilidadeAtitude> all() {
        var habilidadeAtitudes = this.HabilidadeAtitudeRepository.findAll();
        return habilidadeAtitudes;
    }

    @PostMapping
    public HabilidadeAtitude save(@RequestBody HabilidadeAtitude data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.HabilidadeAtitudeRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        this.HabilidadeAtitudeRepository.deleteById(id);
    }

}
package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

import java.util.List;

import com.setrem.pratica2api.model.FaixaTabelaSalarial;
import com.setrem.pratica2api.repository.FaixaTabelaSalarialRepository;

@RestController
@RequestMapping("/api/FaixaTabelaSalarial")
@CrossOrigin
public class FaixaTabelaSalarialController {
    private FaixaTabelaSalarialRepository FaixaTabelaSalarialRepository;

    public FaixaTabelaSalarialController(FaixaTabelaSalarialRepository FaixaTabelaSalarialRepository) {
        this.FaixaTabelaSalarialRepository = FaixaTabelaSalarialRepository;
    }

    @GetMapping("/all")
    public List<FaixaTabelaSalarial> all() {
        var faixaTabelaSalarials = this.FaixaTabelaSalarialRepository.findAllByOrderByIdAsc();
        return faixaTabelaSalarials;
    }

    @PostMapping("/Incluir")
    public FaixaTabelaSalarial add(@RequestBody FaixaTabelaSalarial data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.FaixaTabelaSalarialRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public FaixaTabelaSalarial update(@RequestBody FaixaTabelaSalarial data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.FaixaTabelaSalarialRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.FaixaTabelaSalarialRepository.deleteById(id);
    }
}
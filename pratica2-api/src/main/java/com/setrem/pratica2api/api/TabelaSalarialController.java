package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

import java.util.List;

import com.setrem.pratica2api.model.TabelaSalarial;
import com.setrem.pratica2api.repository.TabelaSalarialRepository;

@RestController
@RequestMapping("/api/TabelaSalarial")
@CrossOrigin
public class TabelaSalarialController {
    private TabelaSalarialRepository TabelaSalarialRepository;

    public TabelaSalarialController(TabelaSalarialRepository TabelaSalarialRepository) {
        this.TabelaSalarialRepository = TabelaSalarialRepository;
    }

    @GetMapping("/all")
    public List<TabelaSalarial> all() {
        var TabelaSalarials = this.TabelaSalarialRepository.findAllByOrderByIdAsc();
        return TabelaSalarials;
    }

    @PostMapping("/Incluir")
    public TabelaSalarial add(@RequestBody TabelaSalarial data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.TabelaSalarialRepository.maxIdTabelaSalarial();
        data.setId(id);
        data = this.TabelaSalarialRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public TabelaSalarial update(@RequestBody TabelaSalarial data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.TabelaSalarialRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.TabelaSalarialRepository.deleteById(id);
    }
}
package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

import java.util.List;

import com.setrem.pratica2api.model.PeriodoCalculo;
import com.setrem.pratica2api.repository.PeriodoCalculoRepository;

@RestController
@RequestMapping("/api/PeriodoCalculo")
@CrossOrigin
public class PeriodoCalculoController {
    private PeriodoCalculoRepository PeriodoCalculoRepository;

    public PeriodoCalculoController(PeriodoCalculoRepository PeriodoCalculoRepository) {
        this.PeriodoCalculoRepository = PeriodoCalculoRepository;
    }

    @GetMapping("/all")
    public List<PeriodoCalculo> all() {
        var PeriodoCalculos = this.PeriodoCalculoRepository.ListaOrdenada();
        return PeriodoCalculos;
    }

    @PostMapping("/Incluir")
    public PeriodoCalculo add(@RequestBody PeriodoCalculo data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.PeriodoCalculoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public PeriodoCalculo update(@RequestBody PeriodoCalculo data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.PeriodoCalculoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.PeriodoCalculoRepository.deleteById(id);
    }
}
package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Beneficio;
import com.setrem.pratica2api.repository.BeneficioRepository;;

@RestController
@RequestMapping("/api/beneficio")
@CrossOrigin
public class BeneficioController {
    private BeneficioRepository BeneficioRepository;
    private Beneficio beneficio = new Beneficio();

    public BeneficioController(BeneficioRepository BeneficioRepository) {
        this.BeneficioRepository = BeneficioRepository;
    }

    @GetMapping("/all")
    public List<Beneficio> all() {
        return this.BeneficioRepository.findAll();
    }

    @PostMapping("/Incluir")
    public Beneficio add(@RequestBody Beneficio data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.BeneficioRepository.maxIdBeneficio();
        data.setId(id);
        data = this.BeneficioRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Beneficio update(@RequestBody Beneficio data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.BeneficioRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.BeneficioRepository.deleteById(id);
    }

}
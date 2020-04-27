package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.ValidationException;
import java.util.List;
import com.setrem.pratica2api.model.Pais;
import com.setrem.pratica2api.repository.PaisRepository;

@RestController
@RequestMapping("/api/pais")
@CrossOrigin
public class PaisController {
    private PaisRepository PaisRepository;
    private Pais pais = new Pais();

    public PaisController(PaisRepository PaisRepository) {
        this.PaisRepository = PaisRepository;
    }

    @GetMapping("/all")
    public List<Pais> all() {
        return this.PaisRepository.findAll();
    }

    @PostMapping("/Incluir")
    public Pais add(@RequestBody Pais data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.PaisRepository.maxIdPais();
        this.pais.setId(id);
        data = this.PaisRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Pais update(@RequestBody Pais data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.PaisRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.PaisRepository.deleteById(id);
    }
}
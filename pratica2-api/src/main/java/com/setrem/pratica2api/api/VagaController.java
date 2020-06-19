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
        return this.VagaRepository.findAll();
    }

    @GetMapping("/listar")
    public List<Vaga> listar() {
        return this.VagaRepository.lista();
    }

    @GetMapping("/listarInterno")
    public List<Vaga> listarInterno() {
        return this.VagaRepository.listaInterno();
    }

    @PostMapping("/Incluir")
    public Vaga add(@RequestBody Vaga data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.VagaRepository.maxIdVaga();
        data.setId(id);
        data = this.VagaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Vaga update(@RequestBody Vaga data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.VagaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.VagaRepository.deleteById(id);
    }

}
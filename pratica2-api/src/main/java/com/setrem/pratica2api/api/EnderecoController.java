package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Endereco;
import com.setrem.pratica2api.repository.EnderecoRepository;

@RestController
@RequestMapping("/api/Endereco")
@CrossOrigin
public class EnderecoController {
    private EnderecoRepository EnderecoRepository;
    private Endereco dep = new Endereco();

    public EnderecoController(EnderecoRepository EnderecoRepository) {
        this.EnderecoRepository = EnderecoRepository;
    }

    @GetMapping("/all")
    public List<Endereco> all() {
        var Enderecos = this.EnderecoRepository.findAll();
        return Enderecos;
    }

    @PostMapping("/Incluir")
    public Endereco add(@RequestBody Endereco data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        // int id = this.EnderecoRepository.maxIdEndereco();
        // this.dep.setId(id);
        data = this.EnderecoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Endereco update(@RequestBody Endereco data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
            this.EnderecoRepository.save(data);
            return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.EnderecoRepository.deleteById(id);
    }

}
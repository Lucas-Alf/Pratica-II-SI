package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import javax.websocket.server.PathParam;

import java.util.List;

import com.setrem.pratica2api.model.PessoaIdioma;
import com.setrem.pratica2api.repository.PessoaIdiomaRepository;

@RestController
@RequestMapping("/api/pessoaidioma")
@CrossOrigin
public class PessoaIdiomaController {
    private PessoaIdiomaRepository PessoaIdiomaRepository;
    private PessoaIdioma pessoaIdioma = new PessoaIdioma();

    public PessoaIdiomaController(PessoaIdiomaRepository PessoaIdiomaRepository) {
        this.PessoaIdiomaRepository = PessoaIdiomaRepository;
    }

    @GetMapping("/all")
    public List<PessoaIdioma> all() {
        return this.PessoaIdiomaRepository.findAll();
    }

    @PostMapping("/Incluir")
    public PessoaIdioma add(@RequestBody PessoaIdioma data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.PessoaIdiomaRepository.maxIdPessoaIdioma();
        data.setId(id);
        this.PessoaIdiomaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public PessoaIdioma update(@RequestBody PessoaIdioma data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.PessoaIdiomaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.PessoaIdiomaRepository.deleteById(id);
    }

}
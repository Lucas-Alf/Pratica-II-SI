package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.ValidationException;
import java.util.List;
import com.setrem.pratica2api.model.Pessoa;
import com.setrem.pratica2api.repository.PessoaRepository;;

@RestController
@RequestMapping("/api/pessoa")
@CrossOrigin
public class PessoaController {
    private PessoaRepository PessoaRepository;
    private Pessoa pes = new Pessoa();

    public PessoaController(PessoaRepository PessoaRepository) {
        this.PessoaRepository = PessoaRepository;
    }

    @GetMapping("/all") // Teste: http://localhost:8080/api/empresa/all
    public List<Pessoa> all() {
        var empresas = this.PessoaRepository.findAll();
        return empresas;
    }

    @PostMapping("/Incluir")
    public Pessoa add(@RequestBody Pessoa data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        System.out.println(data.getPaisnascimentoid());
        data = this.PessoaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Pessoa update(@RequestBody Pessoa data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.PessoaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        this.PessoaRepository.deleteById(id);
    }
}

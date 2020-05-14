package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.PessoaConhecimento;
import com.setrem.pratica2api.repository.PessoaConhecimentoRepository;

@RestController
@RequestMapping("/api/pessoaconhecimento")
@CrossOrigin
public class PessoaConhecimentoController {
    private PessoaConhecimentoRepository PessoaConhecimentoRepository;

    public PessoaConhecimentoController(PessoaConhecimentoRepository PessoaConhecimentoRepository) {
        this.PessoaConhecimentoRepository = PessoaConhecimentoRepository;
    }

    @GetMapping("/all")
    public List<PessoaConhecimento> all() {
        return this.PessoaConhecimentoRepository.findAll();
    }

    @PostMapping
    public PessoaConhecimento save(@RequestBody PessoaConhecimento data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.PessoaConhecimentoRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        this.PessoaConhecimentoRepository.deleteById(id);
    }

}
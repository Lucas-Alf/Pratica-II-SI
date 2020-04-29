package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Cidade;
import com.setrem.pratica2api.repository.CidadeRepository;

@RestController
@RequestMapping("/api/cidade")
@CrossOrigin
public class CidadeController {
    private CidadeRepository CidadeRepository;
    private Cidade cidade = new Cidade();

    public CidadeController(CidadeRepository CidadeRepository) {
        this.CidadeRepository = CidadeRepository;
    }

    @GetMapping("/all")
    public List<Cidade> all() {
        var teste = this.CidadeRepository.findAll();
        return teste;
    }

}
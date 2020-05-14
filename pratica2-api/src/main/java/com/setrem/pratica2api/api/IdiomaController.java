package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Idioma;
import com.setrem.pratica2api.repository.IdiomaRepository;;

@RestController
@RequestMapping("/api/idioma")
@CrossOrigin
public class IdiomaController {
    private IdiomaRepository IdiomaRepository;

    public IdiomaController(IdiomaRepository IdiomaRepository) {
        this.IdiomaRepository = IdiomaRepository;
    }

    @GetMapping("/all")
    public List<Idioma> all() {
        return this.IdiomaRepository.findAll();
    }
    
}
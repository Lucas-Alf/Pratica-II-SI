package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.HabilidadeAtitude;
import com.setrem.pratica2api.repository.HabilidadeAtitudeRepository;;

@RestController
@RequestMapping("/api/habilidadeAtitude")
@CrossOrigin
public class HabilidadeAtitudeController {
    private HabilidadeAtitudeRepository habilidadeAtitudeRepository;
    private HabilidadeAtitude habilidadeAtitude = new HabilidadeAtitude();

    public HabilidadeAtitudeController(HabilidadeAtitudeRepository habilidadeAtitudeRepository) {
        this.habilidadeAtitudeRepository = habilidadeAtitudeRepository;
    }

    @GetMapping("/all")
    public List<HabilidadeAtitude> all() {
        return this.habilidadeAtitudeRepository.findAll();
    }
    
    @PostMapping("/Incluir")
    public HabilidadeAtitude add(@RequestBody HabilidadeAtitude data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.habilidadeAtitudeRepository.maxIdHabilidadeAtitude();
        this.habilidadeAtitude.setId(id);
        data = this.habilidadeAtitudeRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public HabilidadeAtitude update(@RequestBody HabilidadeAtitude data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
            this.habilidadeAtitudeRepository.save(data);
            return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.habilidadeAtitudeRepository.deleteById(id);
    }

}
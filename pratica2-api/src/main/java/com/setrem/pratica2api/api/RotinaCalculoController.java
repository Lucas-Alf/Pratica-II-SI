package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

import java.util.List;

import com.setrem.pratica2api.model.RotinaCalculo;
import com.setrem.pratica2api.repository.RotinaCalculoRepository;

@RestController
@RequestMapping("/api/RotinaCalculo")
@CrossOrigin
public class RotinaCalculoController {
    private RotinaCalculoRepository RotinaCalculoRepository;

    public RotinaCalculoController(RotinaCalculoRepository RotinaCalculoRepository) {
        this.RotinaCalculoRepository = RotinaCalculoRepository;
    }

    @GetMapping("/all")
    public List<RotinaCalculo> all() {
        var RotinaCalculos = this.RotinaCalculoRepository.findAll();
        return RotinaCalculos;
    }

    @PostMapping("/Incluir")
    public RotinaCalculo add(@RequestBody RotinaCalculo data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.RotinaCalculoRepository.findById(data.getId());
        if (jaExistente.isPresent()) {
            throw new Exception("Já existe uma Rotina de Cálculo com este código.");
        } else {
            data = this.RotinaCalculoRepository.save(data);
            return data;
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public RotinaCalculo update(@RequestBody RotinaCalculo data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.RotinaCalculoRepository.findById(data.getId());
        if (!jaExistente.isPresent()) {
            throw new Exception("RotinaCalculo com código " + data.getId() + " não encontrada.");
        } else {
            jaExistente.get().setDescricao(data.getDescricao());
            this.RotinaCalculoRepository.save(jaExistente.get());
            return jaExistente.get();
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.RotinaCalculoRepository.deleteById(id);
    }
}
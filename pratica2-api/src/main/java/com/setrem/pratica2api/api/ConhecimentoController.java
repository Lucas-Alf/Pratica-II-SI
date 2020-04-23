package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Conhecimento;
import com.setrem.pratica2api.repository.ConhecimentoRepository;;

@RestController
@RequestMapping("/api/conhecimento")
@CrossOrigin
public class ConhecimentoController {
    private ConhecimentoRepository ConhecimentoRepository;
    private Conhecimento conhecimento = new Conhecimento();

    public ConhecimentoController(ConhecimentoRepository ConhecimentoRepository) {
        this.ConhecimentoRepository = ConhecimentoRepository;
    }

    @GetMapping("/all")
    public List<Conhecimento> all() {
        return this.ConhecimentoRepository.findAll();
    }

    @PostMapping("/Incluir")
    public Conhecimento add(@RequestBody Conhecimento data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.ConhecimentoRepository.maxIdConhecimento();
        this.conhecimento.setId(id);
        data = this.ConhecimentoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Conhecimento update(@RequestBody Conhecimento data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        var jaExistente = this.ConhecimentoRepository.findById(data.getId());
        if (!jaExistente.isPresent()) {
            throw new Exception("Conhecimento com código " + data.getId() + " não encontrada.");
        } else {
            jaExistente.get().setNome(data.getNome());
            this.ConhecimentoRepository.save(jaExistente.get());
            return jaExistente.get();
        }
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.ConhecimentoRepository.deleteById(id);
    }

}
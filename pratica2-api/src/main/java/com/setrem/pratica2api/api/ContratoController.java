package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Contrato;
import com.setrem.pratica2api.repository.ContratoRepository;

@RestController
@RequestMapping("/api/contrato")
@CrossOrigin
public class ContratoController {
    private ContratoRepository ContratoRepository;

    public ContratoController(ContratoRepository ContratoRepository) {
        this.ContratoRepository = ContratoRepository;
    }

    @GetMapping("/all")
    public List<Contrato> all() {
        return this.ContratoRepository.findAll();
    }

    @GetMapping("/listagemFolhaPagamento")
    public List<Contrato> listagemFolhaPagamento() {
        return this.ContratoRepository.RetornaParaFolhaDePagamento();
    }

    @GetMapping("/findByCpf")
    public List<Contrato> findByCpf(String cpf) {
        return this.ContratoRepository.findByCpf(cpf);
    }

    @PostMapping("/Incluir")
    public Contrato add(@RequestBody Contrato data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        data = this.ContratoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Contrato update(@RequestBody Contrato data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        this.ContratoRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.ContratoRepository.deleteById(id);
    }

}
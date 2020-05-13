package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import com.setrem.pratica2api.model.Vaga;
import com.setrem.pratica2api.model.VagaPessoa;
import com.setrem.pratica2api.repository.VagaPessoaRepository;
import com.setrem.pratica2api.repository.VagaRepository;

@RestController
@RequestMapping("/api/vagapessoa")
@CrossOrigin
public class VagaPessoaController {
    private VagaPessoaRepository VagaPessoaRepository;
    private VagaRepository VagaRepository;
    //private Vaga vaga = new Vaga();

    public VagaPessoaController(VagaPessoaRepository VagaPessoaRepository, VagaRepository VagaRepository) {
        this.VagaPessoaRepository = VagaPessoaRepository;
        this.VagaRepository = VagaRepository;
    }

    @GetMapping("/all")
    public List<VagaPessoa> all() {
        return this.VagaPessoaRepository.findAll();
    }

    @PostMapping("/Incluir")
    public VagaPessoa add(@RequestBody VagaPessoa data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        int id = this.VagaPessoaRepository.maxIdVagaPessoa();
        data.setId(id);
        data = this.VagaPessoaRepository.save(data);

        Optional<Vaga> lista = this.VagaRepository.findById(data.getVagaid().getId());
        Vaga vaga = lista.get();
        int qtd = vaga.getQuantidade() - 1;
        vaga.setQuantidade(qtd);
        this.VagaRepository.save(vaga);
        return data;
    }

}
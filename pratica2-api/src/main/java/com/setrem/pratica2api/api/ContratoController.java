package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Contrato;
import com.setrem.pratica2api.model.Dependente;
import com.setrem.pratica2api.model.PessoaConhecimento;
import com.setrem.pratica2api.model.PessoaHabilidadesAtitudes;
import com.setrem.pratica2api.model.PessoaIdioma;
import com.setrem.pratica2api.repository.ContratoRepository;
import com.setrem.pratica2api.repository.PessoaRepository;

@RestController
@RequestMapping("/api/contrato")
@CrossOrigin
public class ContratoController {
    private ContratoRepository ContratoRepository;
    private PessoaRepository PessoaRepository;

    public ContratoController(ContratoRepository ContratoRepository, PessoaRepository PessoaRepository) {
        this.ContratoRepository = ContratoRepository;
        this.PessoaRepository = PessoaRepository;
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
        if (data.getPessoa().getDependente() != null)
            for (Dependente dep : data.getPessoa().getDependente()) {
                dep.setPessoacpf(data.getPessoa());
            }

        if (data.getPessoa().getPessoaIdiomas() != null)
            for (PessoaIdioma pessoaIdioma : data.getPessoa().getPessoaIdiomas()) {
                pessoaIdioma.setCpf(data.getPessoa());
            }

        if (data.getPessoa().getPessoaConhecimentos() != null)
            for (PessoaConhecimento pessoaConhecimento : data.getPessoa().getPessoaConhecimentos()) {
                pessoaConhecimento.setCpf(data.getPessoa());
            }

        if (data.getPessoa().getPessoaHabilidadesAtitudes() != null)
            for (PessoaHabilidadesAtitudes pessoaHabilidadesAtitudes : data.getPessoa().getPessoaHabilidadesAtitudes()) {
                pessoaHabilidadesAtitudes.setCpf(data.getPessoa());
            }
        PessoaRepository.save(data.getPessoa());
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
package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.ValidationException;
import java.util.List;

import com.setrem.pratica2api.model.Dependente;
import com.setrem.pratica2api.model.Pessoa;
import com.setrem.pratica2api.repository.DependenteRepository;
import com.setrem.pratica2api.repository.PessoaRepository;;

@RestController
@RequestMapping("/api/pessoa")
@CrossOrigin
public class PessoaController {
    private PessoaRepository PessoaRepository;
    private DependenteRepository DependenteRepository;

    public PessoaController(PessoaRepository PessoaRepository, DependenteRepository DependenteRepository) {
        this.PessoaRepository = PessoaRepository;
        this.DependenteRepository = DependenteRepository;
    }

    @GetMapping("/all") // Teste: http://localhost:8080/api/empresa/all
    public List<Pessoa> all() {
        var empresas = this.PessoaRepository.findAll();
        return empresas;
    }

     @GetMapping("/allAtivo") // Teste: http://localhost:8080/api/empresa/all
    public List<Pessoa> allAtivo() {
        var empresas = this.PessoaRepository.allAtivo();
        return empresas;
    }
    @GetMapping("/findDepedente") // Teste: http://localhost:8080/api/empresa/all
    public List<Pessoa> findDepedente() {
        var dependentes = this.PessoaRepository.findDepedentes();
        return dependentes;
    }

    @GetMapping("/findDepedentesCpf") 
    public List<Pessoa> findDepedentesCpf(String cpf) {
        var dependentes = this.PessoaRepository.findDepedentesCpf(cpf);
        return dependentes;
    }
    
    @GetMapping("/contratos") //
    public List<Pessoa> contratos() {
        var empresas = this.PessoaRepository.findAll();
        return empresas;
    }

    @PostMapping("/Incluir")
    public Pessoa add(@RequestBody Pessoa data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        DependenteRepository.deleteByPessoaCpf(data.getCpf());
        for (Dependente  dep : data.getDependente()) {
            dep.setPessoacpf(data);
        }
        data = this.PessoaRepository.save(data);
        return data;
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/Alterar")
    public Pessoa update(@RequestBody Pessoa data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        DependenteRepository.deleteByPessoaCpf(data.getCpf());
        for (Dependente  dep : data.getDependente()) {
            dep.setPessoacpf(data);
        }
        this.PessoaRepository.save(data);
        return data;
    }

    // @CrossOrigin(origins = "*", methods = { RequestMethod.PUT })
    // @PutMapping("/inativa")
    // public Pessoa inativa(@RequestBody Pessoa data, BindingResult bindingResult) throws Exception {
    //     if (bindingResult.hasErrors()) {
    //         throw new ValidationException();
    //     }
    //     this.PessoaRepository.desativaPessoa(data.getCpf());
    //     return data;
    // }
    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/inativa/{id}")
    public void inativa(@PathVariable String id) {
        this.PessoaRepository.desativaPessoa(id);
    }

    @CrossOrigin(origins = "*", methods = { RequestMethod.DELETE })
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
       this.PessoaRepository.deleteById(id);
    }
}

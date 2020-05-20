package com.setrem.pratica2api.api;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

import com.setrem.pratica2api.model.PessoaConhecimento;
import com.setrem.pratica2api.model.PessoaHabilidadesAtitudes;
import com.setrem.pratica2api.model.PessoaIdioma;
import com.setrem.pratica2api.model.Vaga;
import com.setrem.pratica2api.model.VagaPessoa;
import com.setrem.pratica2api.repository.PessoaConhecimentoRepository;
import com.setrem.pratica2api.repository.PessoaHabilidadesAtitudesRepository;
import com.setrem.pratica2api.repository.PessoaIdiomaRepository;
import com.setrem.pratica2api.repository.PessoaRepository;
import com.setrem.pratica2api.repository.VagaPessoaRepository;
import com.setrem.pratica2api.repository.VagaRepository;

@RestController
@RequestMapping("/api/vagapessoa")
@CrossOrigin
public class VagaPessoaController {
    private VagaPessoaRepository VagaPessoaRepository;
    private VagaRepository VagaRepository;
    private PessoaRepository PessoaRepository;
    private PessoaIdiomaRepository PessoaIdiomaRepository;
    private PessoaConhecimentoRepository PessoaConhecimentoRepository;
    private PessoaHabilidadesAtitudesRepository PessoaHabilidadesAtitudesRepository;

    public VagaPessoaController(VagaPessoaRepository VagaPessoaRepository, VagaRepository VagaRepository, PessoaRepository PessoaRepository,
    PessoaIdiomaRepository PessoaIdiomaRepository, PessoaConhecimentoRepository PessoaConhecimentoRepository, PessoaHabilidadesAtitudesRepository PessoaHabilidadesAtitudesRepository) {
        this.VagaPessoaRepository = VagaPessoaRepository;
        this.VagaRepository = VagaRepository;
        this.PessoaRepository = PessoaRepository;
        this.PessoaIdiomaRepository = PessoaIdiomaRepository;
        this.PessoaConhecimentoRepository = PessoaConhecimentoRepository;
        this.PessoaHabilidadesAtitudesRepository = PessoaHabilidadesAtitudesRepository;
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

    @PostMapping("/IncluirExterno")
    public VagaPessoa addExterno(@RequestBody VagaPessoa data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        //this.PessoaRepository.save(data.getCpf());

        PessoaIdiomaRepository.deleteByCpf(data.getCpf().getCpf());
        for (PessoaIdioma pessoaIdioma : data.getCpf().getPessoaIdiomas()) {
            pessoaIdioma.setCpf(data.getCpf());
        }

        PessoaConhecimentoRepository.deleteByCpf(data.getCpf().getCpf());
        for (PessoaConhecimento pessoaConhecimento : data.getCpf().getPessoaConhecimentos()) {
            pessoaConhecimento.setCpf(data.getCpf());
        }

        PessoaHabilidadesAtitudesRepository.deleteByCpf(data.getCpf().getCpf());
        for (PessoaHabilidadesAtitudes pessoaHabilidadesAtitudes : data.getCpf().getPessoaHabilidadesAtitudes()) {
            pessoaHabilidadesAtitudes.setCpf(data.getCpf());
        }

        this.PessoaRepository.save(data.getCpf());

        int id = this.VagaPessoaRepository.maxIdVagaPessoa();
        data.setId(id);
        this.VagaPessoaRepository.save(data);

        Optional<Vaga> lista = this.VagaRepository.findById(data.getVagaid().getId());
        Vaga vaga = lista.get();
        int qtd = vaga.getQuantidade() - 1;
        vaga.setQuantidade(qtd);
        this.VagaRepository.save(vaga);
        return data;
    }

}
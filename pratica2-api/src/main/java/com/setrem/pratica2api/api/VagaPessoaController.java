package com.setrem.pratica2api.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;

import javax.validation.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.setrem.pratica2api.model.Calculo;
import com.setrem.pratica2api.repository.CalculoRepository;
import com.setrem.pratica2api.service.CalculoService;
import com.setrem.pratica2api.service.SessionFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.setrem.pratica2api.service.SessionFactory;

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

    @GetMapping("/listarSelecao")
    public List<VagaPessoa> listarSelecao(/*List<String> idioma*/) {
        //String filtros = " and c.idiomaid = 2 ";
        //String filtros = "";
        return this.VagaPessoaRepository.ListarSelecao();
    }

    @GetMapping("/listarSelecao2")
    public List<VagaPessoa> listarSelecao2(String valorCPF, String valorNome, String valorSexo, String valorVaga,
    String idioma, String habilidadeAtitude, String conhecimento) {
        List<Integer> filtroIdioma = Arrays.asList();
        if (idioma != "") {
            List<String> filtroI = new ArrayList<String>(Arrays.asList(idioma.split(",")));
            filtroIdioma = filtroI.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        }

        List<Integer> filtroHabilidadeAtitude = Arrays.asList();
        if (habilidadeAtitude != "") {
            List<String> filtroHA = new ArrayList<String>(Arrays.asList(habilidadeAtitude.split(",")));
            filtroHabilidadeAtitude = filtroHA.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        }

        List<Integer> filtroConhecimento = Arrays.asList();
        if (conhecimento != "") {
            List<String> filtroC = new ArrayList<String>(Arrays.asList(conhecimento.split(",")));
            filtroConhecimento = filtroC.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        }

        if (filtroIdioma.size() != 0 && filtroHabilidadeAtitude.size() == 0 && filtroConhecimento.size() == 0) {
            return this.VagaPessoaRepository.ListarSelecao_Idioma(valorCPF, valorNome, valorSexo, valorVaga, filtroIdioma);
        } else if (filtroIdioma.size() == 0 && filtroHabilidadeAtitude.size() != 0 && filtroConhecimento.size() == 0) {
            return this.VagaPessoaRepository.ListarSelecao_HabilidadeAtitude(valorCPF, valorNome, valorSexo, valorVaga, filtroHabilidadeAtitude);
        } else if (filtroIdioma.size() == 0 && filtroHabilidadeAtitude.size() == 0 && filtroConhecimento.size() != 0) {
            return this.VagaPessoaRepository.ListarSelecao_Conhecimento(valorCPF, valorNome, valorSexo, valorVaga, filtroConhecimento);
        } else if (filtroIdioma.size() != 0 && filtroHabilidadeAtitude.size() != 0 && filtroConhecimento.size() != 0) {
            return this.VagaPessoaRepository.ListarSelecao_TodosFiltros(valorCPF, valorNome, valorSexo, valorVaga, filtroIdioma, filtroHabilidadeAtitude, filtroConhecimento);
        } else {
            return this.VagaPessoaRepository.ListarSelecaoTodos(valorCPF, valorNome, valorSexo, valorVaga);
        }
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
        data.getCpf().setAtivo(true);
        this.VagaPessoaRepository.save(data);

        Optional<Vaga> lista = this.VagaRepository.findById(data.getVagaid().getId());
        Vaga vaga = lista.get();
        int qtd = vaga.getQuantidade() - 1;
        vaga.setQuantidade(qtd);
        this.VagaRepository.save(vaga);
        return data;
    }

    @GetMapping("/imprimir")
    public ResponseEntity<byte[]> relatorio(String valorCPF, String valorNome, String valorSexo, String valorVaga,
    String idioma, String habilidadeAtitude, String conhecimento) {
        String sql = "";
        if (valorCPF != "") {
            sql += " and b.cpf like '%" + valorCPF + "%'";
        }
        if (valorNome != "") {
            sql += " and b.nome like '%" + valorNome + "%'";
        }
        if (valorSexo != "") {
            sql += " and b.sexo like '%" + valorSexo + "%'";
        }
        if (valorVaga != "") {
            sql += " and a.vagaid = " + valorVaga;
        }
        if (idioma != "") {
            sql += " and exists (select idiomaid from pessoaidioma where cpf = b.cpf and idiomaid in (" + idioma + ")) ";
        }
        if (habilidadeAtitude != "") {
            sql += " and exists (select habilidadesatitudesid from pessoahabilidadesatitudes where cpf = b.cpf and habilidadesatitudesid in (" + habilidadeAtitude + ")) ";
        }
        if (conhecimento != "") {
            sql += " and exists (select conhecimentoid from pessoaconhecimento where cpf = b.cpf and conhecimentoid in (" + conhecimento + ")) ";
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
        parametros.put("sql", sql);

        byte[] reportBytes = null;
        SessionFactory sessionFactory = new SessionFactory();
        Connection conexao = sessionFactory.OpenConnection();
        try {
            InputStream io = this.getClass().getResourceAsStream("/relatorios/SelecaoCandidatos.jasper");
            JasperPrint Jp = JasperFillManager.fillReport(io, parametros,conexao);
            reportBytes = JasperExportManager.exportReportToPdf(Jp);
        } catch (JRException ex) {
            return ResponseEntity.noContent().build();
        }
        sessionFactory.CloseConnection(conexao);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(reportBytes);
    }

}
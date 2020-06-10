package com.setrem.pratica2api.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

@RestController
@RequestMapping("/api/calculo")
@CrossOrigin
public class CalculoController {
    private CalculoService CalculoService;

    public CalculoController(CalculoService calculoService) {
        this.CalculoService = calculoService;
    }

    @GetMapping("/buscaPorContrato")
    public List<Calculo> buscaPorContrato(int matricula) {
        var calculos = this.CalculoService.findByContrato(matricula);
        return calculos;
    }

    @GetMapping("/calcular")
    public void calcular(int matricula) throws Exception {
        this.CalculoService.Calcular(matricula);
    }

    @GetMapping("/imprimir")
    public ResponseEntity<byte[]> relatorio(int matricula) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
        parametros.put("Matricula", matricula);

        byte[] reportBytes = null;
        SessionFactory sessionFactory = new SessionFactory();
        Connection conexao = sessionFactory.OpenConnection();
        try {
            InputStream io = this.getClass().getResourceAsStream("/relatorios/DemonstrativoPag.jasper");
            JasperPrint Jp = JasperFillManager.fillReport(io, parametros,conexao);
            reportBytes = JasperExportManager.exportReportToPdf(Jp);
        } catch (JRException ex) {
            return ResponseEntity.noContent().build();
        }
        sessionFactory.CloseConnection(conexao);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(reportBytes);
    }
}
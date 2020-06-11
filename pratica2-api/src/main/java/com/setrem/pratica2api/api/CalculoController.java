package com.setrem.pratica2api.api;

import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.ImageIcon;

import com.setrem.pratica2api.model.Calculo;
import com.setrem.pratica2api.service.CalculoService;
import com.setrem.pratica2api.service.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/api/calculo")
@CrossOrigin
public class CalculoController {

    private CalculoService CalculoService;

    @Autowired
    private ResourceLoader resourceLoader;

    public CalculoController(CalculoService calculoService) {
        this.CalculoService = calculoService;
    }

    @GetMapping("/buscaPorContrato")
    public List<Calculo> buscaPorContrato(int matricula) {
        var calculos = this.CalculoService.findByContrato(matricula);
        return calculos;
    }

    @GetMapping("/calcular")
    public void calcular(String matricula) throws Exception {
        if (matricula.equals("null") || matricula == null || matricula.equals("")) {
            this.CalculoService.Calcular(null);
        } else {
            this.CalculoService.Calcular(Integer.parseInt(matricula));
        }
    }

    @GetMapping("/imprimir")
    public ResponseEntity<byte[]> relatorio(int matricula) throws IOException {
        Resource logoResource = resourceLoader.getResource("classpath:/META-INF/reports/ColoridoOriginal.jpg");
        InputStream is = logoResource.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        Image logo = new ImageIcon(buffer.toByteArray()).getImage();

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
        parametros.put("Matricula", matricula);
        parametros.put("LOGO_EMPRESA", logo);

        byte[] reportBytes = null;
        SessionFactory sessionFactory = new SessionFactory();
        Connection conexao = sessionFactory.OpenConnection();
        try {
            InputStream io = this.getClass().getResourceAsStream("/META-INF/reports/DemonstrativoPag.jasper");
            JasperPrint Jp = JasperFillManager.fillReport(io, parametros, conexao);
            reportBytes = JasperExportManager.exportReportToPdf(Jp);
        } catch (JRException ex) {
            return ResponseEntity.noContent().build();
        }
        sessionFactory.CloseConnection(conexao);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(reportBytes);
    }
}
package br.unicesumar.time05.relatorios;

import br.unicesumar.time05.usuario.sessaousuario.SessaoUsuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Relatorio {

    @Autowired
    private DataSource dataSource;

    @Autowired
    SessaoUsuario sessaoUsuario;

    public String gerarRelatorio(String nomeXmlJasper, formatoRelatorio formatoRelatorio, Map<String, Object> params) {

        try {
            URL reportResource = getClass().getClassLoader().getResource("./relatorios/" + nomeXmlJasper);
            JasperReport report = JasperCompileManager.compileReport(reportResource.getFile());
            JasperPrint print = JasperFillManager.fillReport(report, params, dataSource.getConnection());

            File path = new File("src/main/webapp/rels/" + sessaoUsuario.getUsuario().getIdusuario());
            path.mkdirs();
            for (File arquivo : path.listFiles()) {
                arquivo.delete();
            }

            String resultado = "";

            switch (formatoRelatorio) {
                case EXCEL: {
                    File fileExcel = new File(path + "/" + UUID.randomUUID().toString() + ".xls");
                    FileOutputStream fos = new FileOutputStream(fileExcel);

                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fos);
                    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                    exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
                    exporter.exportReport();
                    resultado = fileExcel.getName();
                }
                break;

                case PDF: {
                    File filePdf = new File(path + "/" + UUID.randomUUID().toString() + ".pdf");
                    JasperExportManager.exportReportToPdfFile(print, filePdf.getAbsolutePath());
                    resultado = filePdf.getName();
                }
                break;
            }
            System.out.println("Impresso Relatório: " + resultado + " pelo usuário :" + sessaoUsuario.getUsuario().getIdusuario() + " - " + sessaoUsuario.getUsuario().getNome());
            return "/rels/" + sessaoUsuario.getUsuario().getIdusuario() + "/" + resultado;
        } catch (JRException | SQLException | FileNotFoundException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
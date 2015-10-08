package br.unicesumar.time05.indigena;

import classesbase.ControllerBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/indigena")
public class IndigenaController extends ControllerBase<CriarIndigena, Long, IndigenaService> {
    
    @Autowired
    private DataSource dataSource;

    @Override
    public Indigena getObjeto(@PathVariable Long aId) {
        Indigena i = (Indigena) service.getObjeto(aId);
        if (new File("src/main/webapp/fotos/users/" + i.getCodigoAssindi()+ ".jpg").exists()) 
            i.setImgSrc("src/main/webapp/fotos/users/" + i.getCodigoAssindi()+ ".jpg");
        return i;
    }
    
    @RequestMapping(value = "/rel", method = RequestMethod.GET)
    public void verifcarDescricao(HttpServletResponse response) throws JRException, SQLException, IOException {
        // compilacao do JRXML
        URL reportResource = getClass().getClassLoader().getResource("./relatorios/teste01.jrxml");
        JasperReport report = JasperCompileManager.compileReport(reportResource.getFile());  
      
        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), dataSource.getConnection());  
        
        File pastaUsuario = new File(System.getProperty("user.dir"));
        File pdf = new File(pastaUsuario, String.format("relatorioPessoas_%s.pdf", System.nanoTime()));
        System.out.println("Imprimindo arquivo " + pdf);
        JasperExportManager.exportReportToPdfFile(print, pdf.getAbsolutePath());
        
        response.setHeader("Content-Type", "application/pdf");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(IOUtils.toByteArray(new FileInputStream(pdf)));
        outputStream.flush();
        outputStream.close();
        
        System.out.println("Relatório gerado.");

// ******************************************************************
        //Javascript no retorno para o front PDF
//          function gerarRelatorioSuccess(response) {
//            printReport(response.data);
//          } 
//
//          function printReport(data) {
//            var file = new Blob([ data ], {
//              type : 'application/pdf'
//            });
//
//            var url = URL.createObjectURL(file);
//            window.open(url).print();
//         }
// ******************************************************************
//          function gerarRelatorioSuccess(response) {
//    printReport(response.data);
//  } 
//
//  function printReport(data) {
//    var file = new Blob([ data ], {
//      type : 'text/html'
//    });
//
//    var url = URL.createObjectURL(file);
//    window.open(url).print();
// }
 // ******************************************************************      
    }

}

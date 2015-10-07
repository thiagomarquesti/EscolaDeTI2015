package br.unicesumar.time05.indigena;

import classesbase.ControllerBase;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/indigena")
public class IndigenaController extends ControllerBase<CriarIndigena, Long, IndigenaService> {

    @Override
    public Indigena getObjeto(@PathVariable Long aId) {
        Indigena i = (Indigena) service.getObjeto(aId);
        if (new File("src/main/webapp/fotos/users/" + i.getCodigoAssindi()+ ".jpg").exists()) 
            i.setImgSrc("src/main/webapp/fotos/users/" + i.getCodigoAssindi()+ ".jpg");
        return i;
    }
    
    @RequestMapping(value = "/rel", method = RequestMethod.GET)
    public void verifcarDescricao() throws JRException {
        
        //System.out.println("Gerando relatório...");
        //UsuarioDAO usuarioDAO = new UsuarioDAO();
        //List listaUs = usuarioDAO.listaTodos();
        String str = "br\\unicesumar\\time05\\relatorios\\teste02.jrxml";
        //JasperReport xml = JasperCompileManager.compileReport(str);
        //JasperReport pathjrxml = JasperCompileManager.compileReport("br/unicesumar/time05/relatorios/teste02.jrxml");
        //JasperPrint printReport = JasperFillManager.fillReport(pathjrxml, null, new JRBeanCollectionDataSource(listaUs));
        //JasperExportManager.exportReportToPdfFile(printReport, "relatorio/reportex.pdf");
        //System.out.println("Relatorio gerado");
                
        List lista = new ArrayList();
        //lista.add();
        
        String caminho = "src/main/java/relatorios/teste01.jrxml";
        // compilacao do JRXML 
        JasperReport report = JasperCompileManager.compileReport(caminho);  
        // preenchimento do relatorio, note que o metodo recebe 3 parametros: 
        // 1 - o relatorio 
        // 
        // 2 - um Map, com parametros que sao passados ao relatorio 
        // no momento do preenchimento. No nosso caso eh null, pois nao 
        // estamos usando nenhum parametro 
        // 
        // 3 - o data source. Note que nao devemos passar a lista diretamente, 
        // e sim "transformar" em um data source utilizando a classe 
        // JRBeanCollectionDataSource 
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));  
        
        // exportacao do relatorio para outro formato, no caso PDF 
        JasperExportManager.exportReportToPdfFile(print, "src/main/java/relatorios/RelatorioPessoas.pdf");  
        
        System.out.println("Relatório gerado.");
 
    }

}

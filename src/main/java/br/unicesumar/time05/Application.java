package br.unicesumar.time05;

import br.unicesumar.time05.indigena.Indigena;
import br.unicesumar.time05.indigena.IndigenaController;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Map;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
    
@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.unicesumar.time05")
@EnableAspectJAutoProxy(proxyTargetClass = false)
@Import(value = {WebConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
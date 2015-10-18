package br.unicesumar.time05.indigena;

import classesbase.ControllerBase;
import java.io.File;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    
    
}

package br.unicesumar.time05.indigena;

import classesbase.ControllerBase;
import java.io.File;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/indigena")
public class IndigenaController extends ControllerBase<CriarIndigena, Long, IndigenaService> {

    @Override
    public Indigena getObjeto(@PathVariable Long aId) {
        Indigena i = (Indigena) service.getObjeto(aId);
        if (new File("src/main/webapp/fotos/indios/" + i.getCodigoAssindi()+ ".jpg").exists()) 
            i.setImgSrc("/fotos/indios/" + i.getCodigoAssindi()+ ".jpg");
        else
            i.setImgSrc("/fotos/default.png");
        return i;
    }

}

package br.unicesumar.time05.indigena;

import classesBase.ControllerBase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/indigena")
public class IndigenaController extends ControllerBase<CriarIndigena, Long, IndigenaService> {

    @Override
    public Indigena getObjeto(@PathVariable Long aId) {
        return (Indigena) service.getObjeto(aId);
    }
}

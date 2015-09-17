package br.unicesumar.time05.familia;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/familia")
public class FamiliaController extends ControllerBase<Familia, Long, FamiliaService>{
    
}

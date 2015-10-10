package br.unicesumar.time05.estadiafamilia;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estadia")
public class EstadiaController extends ControllerBase<Estadia, Long, EstadiaService>{
    
}

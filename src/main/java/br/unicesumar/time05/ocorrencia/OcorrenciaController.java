package br.unicesumar.time05.ocorrencia;


import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ocorrencia")
public class OcorrenciaController extends ControllerBase<Ocorrencia, Long, OcorrenciaService>{
    
}

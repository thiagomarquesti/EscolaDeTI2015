package br.unicesumar.time05.visita;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/visita")
public class VisitaController extends ControllerBase<Visita, Long, VisitaService>{
}
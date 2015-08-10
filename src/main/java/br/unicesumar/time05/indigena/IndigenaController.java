package br.unicesumar.time05.indigena;

import classesBase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/indigena")
public class IndigenaController extends ControllerBase<Indigena, Long, IndigenaService> {

}

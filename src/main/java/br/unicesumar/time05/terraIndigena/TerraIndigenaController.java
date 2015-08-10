package br.unicesumar.time05.terraIndigena;

import classesBase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/terraIndigena")
public class TerraIndigenaController extends ControllerBase<TerraIndigena, Long, TerraIndigenaService> {
}

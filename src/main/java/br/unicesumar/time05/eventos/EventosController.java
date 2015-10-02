package br.unicesumar.time05.eventos;

import classesbase.ControllerBase;
import classesbase.ServiceBase;
import java.io.Serializable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/eventos")
public class EventosController extends ControllerBase<Eventos, Long, EventosService> {
    
    
}

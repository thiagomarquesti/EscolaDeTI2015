package br.unicesumar.time05.cidade;

import classesBase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cidade")
public class CidadeController extends ControllerBase<Cidade, Long, CidadeService>{

    @RequestMapping(value = "/cidadePorEstado/{aEstadoId}")
    public List<Map<String, Object>> cidadePorEstado(@PathVariable Long aEstadoId){
        return service.cidadePorEstado(aEstadoId);
    }
    
}

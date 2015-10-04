package br.unicesumar.time05.familia;

import classesbase.ControllerBase;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/familia")
public class FamiliaController extends ControllerBase<Familia, Long, FamiliaService>{
    
    @RequestMapping(value = "/quantidadeintegrantes/{aFamiliaId}", method = RequestMethod.GET)
    public Map<String, Object> getQuantidadeIntegrantes(@PathVariable Long aFamiliaId) {
        return service.getQuantidadeIntegrantesFamilia(aFamiliaId);
    }
}

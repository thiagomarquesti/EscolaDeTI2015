package br.unicesumar.time05.perfildeacesso;

import classesBase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/perfildeacesso")
public class PerfilDeAcessoController extends ControllerBase<PerfilDeAcesso, Long, PerfilDeAcessoService>{
    
    @RequestMapping(value = "/itensdeacesso/{aId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getItensDeAcessoPorPerfilDeAcessoID(@PathVariable Long aId){
        return service.getItensDeAcessoPorPerfilDeAcessoID(aId);
    }
    
}

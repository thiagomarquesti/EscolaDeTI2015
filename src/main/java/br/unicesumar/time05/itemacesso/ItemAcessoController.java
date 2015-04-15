package br.unicesumar.time05.itemacesso;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemacesso")
public class ItemAcessoController {
    @Autowired
    private ItemAcessoService service;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getItensAcesso(){
        return service.getItensAcesso();
    }
    
    @RequestMapping(value = "/{aSuperiorId}",method = RequestMethod.GET)
    public List<Map<String, Object>> getItensAcessoPorSuperior(@PathVariable Long aSuperiorId ){
        return service.getItensAcessoPorSuperior(aSuperiorId);
    }
}

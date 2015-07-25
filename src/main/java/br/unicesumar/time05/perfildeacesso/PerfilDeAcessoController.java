package br.unicesumar.time05.perfildeacesso;

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
public class PerfilDeAcessoController {
    
    @Autowired
    private PerfilDeAcessoService service;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getPerfisDeAcesso(){
        return service.getPerfisDeAcesso();
    }
    
    @RequestMapping(value = "/{aIdPerfilDeAcesso}", method = RequestMethod.GET)
    public Map<String, Object> getPerfilDeAcesso(@PathVariable Long aIdPerfilDeAcesso){
        return service.getPerfilDeAcesso(aIdPerfilDeAcesso);
    }
    
    @RequestMapping(value = "/itensdeacesso/{aIdPerfilDeAcesso}", method = RequestMethod.GET)
    public List<Map<String, Object>> getItensDeAcessoPorPerfilDeAcessoID(@PathVariable Long aIdPerfilDeAcesso){
        return service.getItensDeAcessoPorPerfilDeAcessoID(aIdPerfilDeAcesso);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void salvarPerfilDeAcesso(@RequestBody PerfilDeAcesso aPerfilDeAcesso){
        service.salvarPerfilDeAcesso(aPerfilDeAcesso);
    }
    
    @RequestMapping(value = "/{aIdPerfilDeAcesso}", method = RequestMethod.DELETE)
    public void removerPerfilDeAcesso(@PathVariable Long aIdPerfilDeAcesso){
        service.removerPerfilDeAcesso(aIdPerfilDeAcesso);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void alterarPerfilDeAcesso(@RequestBody PerfilDeAcesso aPerfilDeAcesso){
        System.out.println(aPerfilDeAcesso.toString());
        service.alterarPerfilDeAcesso(aPerfilDeAcesso);
    }
}

package br.unicesumar.time05.perfildeacesso;

import java.util.List;
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
    public List<PerfilDeAcesso> getPerfisDeAcesso(){
        return service.getPerfisDeAcesso();
    }
    
    @RequestMapping(value = "/{aId}", method = RequestMethod.GET)
    public PerfilDeAcesso getPerfilDeAcesso(@PathVariable Long aId){
        return service.getPerfilDeAcesso(aId);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void salvarPerfilDeAcesso(@RequestBody PerfilDeAcesso aPerfilDeAcesso){
        service.salvarPerfilDeAcesso(aPerfilDeAcesso);
    }
    
    @RequestMapping(value = "/{aId}", method = RequestMethod.DELETE)
    public void removerPerfilDeAcesso(@PathVariable Long aId){
        service.removerPerfilDeAcesso(aId);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void alterarPerfilDeAcesso(@RequestBody PerfilDeAcesso aPerfilDeAcesso){
        service.alterarPerfilDeAcesso(aPerfilDeAcesso);
    }
}

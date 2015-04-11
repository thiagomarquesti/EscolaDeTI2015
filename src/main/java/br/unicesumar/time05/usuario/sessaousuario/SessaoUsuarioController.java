package br.unicesumar.time05.usuario.sessaousuario;

import br.unicesumar.time05.usuario.Usuario;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class SessaoUsuarioController {
    
    @Autowired
    SessaoUsuarioService service;
    
    @RequestMapping(value = "/{aLogin}/{aSenha}", method = RequestMethod.POST)
    public boolean efetuarLogin(@PathVariable String aLogin, @PathVariable String aSenha, HttpSession session){
       return service.efetuarLogin(aLogin, aSenha, session);
    }
    
    @RequestMapping(value = "/usuariologado", method = RequestMethod.GET)
    public Usuario getUsuarioLogado(){
        return service.getUsuarioLogado();
    }
}
package br.unicesumar.time05.usuario.sessaousuario;

import br.unicesumar.time05.usuario.Usuario;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class SessaoUsuarioController {
    
    @Autowired
    SessaoUsuarioService service;
    
    @RequestMapping(value = "/efetuarlogin", method = RequestMethod.POST)
    public boolean efetuarLogin(@RequestBody DadosLogin aDadosLogin, HttpSession session){
        return service.efetuarLogin(aDadosLogin, session);
    }
    
    @RequestMapping(value = "/efetuarlogout", method = RequestMethod.POST)
    public void efetuarLogout(@RequestBody DadosLogin aDadosLogin, HttpSession session){
        service.efetuarLogout(aDadosLogin, session);
    }
    
    @RequestMapping(value = "/usuariologado", method = RequestMethod.GET)
    public Usuario getUsuarioLogado(){
        return service.getUsuarioLogado();
    }
}
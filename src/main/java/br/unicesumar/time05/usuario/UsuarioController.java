package br.unicesumar.time05.usuario;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @RequestMapping(method = RequestMethod.POST)
    public void salvarUsuario(@RequestBody Usuario aUsuario){
        //try {
            usuarioService.salvarUsuario(aUsuario);
        //} catch (Exception e) {
        //    throw new RuntimeException("Erro ao salvar o usuário, verifique os dados fornecidos!");
        //}
    
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getUsuarios(){
        return usuarioService.getUsuarios();
    }
    
    @RequestMapping(value = "/{aUsuarioId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getUsuario(@PathVariable Long aUsuarioId){
        return usuarioService.getUsuarioById(aUsuarioId);
    }
    
    @RequestMapping(value = "/{aUsuarioId}", method = RequestMethod.DELETE)
    public void removerUsuario(@PathVariable Long aUsuarioId){
        usuarioService.removerUsuario(aUsuarioId);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void editarUsuario(@RequestBody Usuario aUsuario){
            usuarioService.salvarUsuario(aUsuario);
    }

    @RequestMapping(value = "/{aUsuarioId}", method = RequestMethod.PUT)
    public void alterarStatus(@PathVariable Long aUsuarioId){
        usuarioService.trocarStatusUsuario(aUsuarioId);
    }

    @RequestMapping(value = "/verificarLogin/{aLogin:.+}" ,method = RequestMethod.GET)
    public boolean verifcarLogin(@PathVariable String aLogin){
        return usuarioService.verificarLogin(aLogin);
    }

    @RequestMapping(value = "/verificarEmail/{aEmail:.+}" ,method = RequestMethod.GET)
    public boolean verifcarEmail(@PathVariable String aEmail){
        return usuarioService.verificarEmail(aEmail);
    }

    @RequestMapping(value = "/verificarSenha/{aSenha:.+}" ,method = RequestMethod.GET)
    public boolean verifcarSenha(@PathVariable String aSenha){
        return usuarioService.verificarSenha(aSenha);
    }

    @RequestMapping(value = "/verificarEmail/{aEmail:.+}/{aUsuarioId}" ,method = RequestMethod.GET)
    public boolean verifcarEmail(@PathVariable String aEmail, @PathVariable Long aUsuarioId){
        return usuarioService.verificarEmail(aEmail, aUsuarioId);
    }
    
    @RequestMapping(value = "/verificarLogin/{aLogin:.+}/{aUsuarioId}" ,method = RequestMethod.GET)
    public boolean verifcarLogin(@PathVariable String aLogin, @PathVariable Long aUsuarioId){
        return usuarioService.verificarLogin(aLogin, aUsuarioId);
    }
}

package br.unicesumar.time05.usuario;

import br.unicesumar.time05.email.Email;
import classesBase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController extends ControllerBase<Usuario, Long, UsuarioService>{
    
    @RequestMapping(value = "/verificarSenha/{aSenha:.+}" ,method = RequestMethod.GET)
    public boolean verifcarSenha(@PathVariable Senha aSenha){
        return service.verificarSenha(aSenha);
    }

    @RequestMapping(value = "/verificarEmail/{aEmail:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public boolean verifcarEmail(@PathVariable String aEmail, @PathVariable Long aUsuarioId) {
        return service.verificarEmail(aEmail, aUsuarioId);
    }

    @RequestMapping(value = "/verificarLogin/{aLogin:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public boolean verifcarLogin(@PathVariable String aLogin, @PathVariable Long aUsuarioId) {
        return service.verificarLogin(aLogin, aUsuarioId);
    }

    @RequestMapping(value = "/perfil/{aUsuarioId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getPerfis(@PathVariable Long aUsuarioId) {
        return service.getPerfis(aUsuarioId);
    }

    @RequestMapping(value = "/perfil/{aUsuarioId}", method = RequestMethod.POST)
    public void addPerfilDeAcesso(@PathVariable Long aUsuarioId, @RequestBody Long[] aPerfilId) {
        service.addPerfil(aUsuarioId, aPerfilId);
    }

    @RequestMapping(value = "/perfil/{aUsuarioId}", method = RequestMethod.DELETE)
    public void deletarPerfilDeAcesso(@PathVariable Long aUsuarioId, @RequestBody Long[] aPerfilId) {
        service.deletePerfis(aUsuarioId, aPerfilId);
    }

}

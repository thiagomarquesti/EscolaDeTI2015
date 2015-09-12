package br.unicesumar.time05.usuario;

import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import classesbase.ControllerBase;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController extends ControllerBase<CriarUsuario, Long, UsuarioService> {

    @Override
    public Usuario getObjeto(@PathVariable Long aId) {
        Usuario u = (Usuario) service.getObjeto(aId);
        if (new File("src/main/webapp/fotos/users/" + u.getIdpessoa() + ".jpg").exists()) {
            u.setImgSrc("src/main/webapp/fotos/users/" + u.getIdpessoa() + ".jpg");
        }
        return u;
    }

    @RequestMapping(value = "/verificarSenha/{aSenha:.+}", method = RequestMethod.GET)
    public boolean verifcarSenha(@PathVariable Senha aSenha) {
        return service.verificarSenha(aSenha);
    }

    @RequestMapping(value = "/verificarCpf/{aCpf:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public Map<String, String> verifcarCpf(@PathVariable CPF aCpf, @PathVariable Long aUsuarioId) {
//        Map<String, String> map = new HashMap<>();
//        map.put("retorno", "valido");
//        return map;
        if (aUsuarioId != -1) {
            return service.verificarCpf(aCpf, aUsuarioId);
        } else {
            return service.verificarCpf(aCpf);
        }
    }

    @RequestMapping(value = "/trocarStatusUsuario/{aUsuarioId}", method = RequestMethod.PUT)
    public void alterarStatus(@PathVariable Long aUsuarioId) {
        service.trocarStatusUsuario(aUsuarioId);
    }

    @RequestMapping(value = "/verificarEmail/{aEmail:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public boolean verifcarEmail(@PathVariable String aEmail, @PathVariable Long aUsuarioId) {
        if (aUsuarioId != -1) {
            return service.verificarEmail(new Email(aEmail), aUsuarioId);
        } else {
            return service.verificarEmail(new Email(aEmail));
        }
    }

//    @RequestMapping(value = "/verificarEmail/{aEmail:.+}", method = RequestMethod.GET)
//    public boolean verifcarEmail(@PathVariable String aEmail) {
//        return service.verificarEmail(new Email(aEmail));
//    }
    @RequestMapping(value = "/verificarLogin/{aLogin:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public boolean verifcarLogin(@PathVariable String aLogin, @PathVariable Long aUsuarioId) {
        return service.verificarLogin(aLogin, aUsuarioId);
    }

    @RequestMapping(value = "/verificarLogin/{aLogin:.+}", method = RequestMethod.GET)
    public boolean verifcarLogin(@PathVariable String aLogin) {
        return service.verificarLogin(aLogin);
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

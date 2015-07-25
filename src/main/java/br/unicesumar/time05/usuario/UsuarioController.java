package br.unicesumar.time05.usuario;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.ConsultaPersonalizada.ParametrosConsulta;
import br.unicesumar.time05.ConsultaPersonalizada.RetornoConsultaPaginada;
import classesBase.ControllerBase;
import classesBase.ServiceBase;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController extends ControllerBase<Usuario, Long, UsuarioService>{

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/{aUsuarioId}", method = RequestMethod.PUT)
    public void alterarStatus(@PathVariable Long aUsuarioId) {
        usuarioService.trocarStatusUsuario(aUsuarioId);
    }

    @RequestMapping(value = "/verificarLogin/{aLogin:.+}", method = RequestMethod.GET)
    public boolean verifcarLogin(@PathVariable String aLogin) {
        return usuarioService.verificarLogin(aLogin);
    }

    @RequestMapping(value = "/verificarEmail/{aEmail:.+}", method = RequestMethod.GET)
    public boolean verifcarEmail(@PathVariable String aEmail) {
        return usuarioService.verificarEmail(aEmail);
    }

    @RequestMapping(value = "/verificarSenha/{aSenha:.+}", method = RequestMethod.GET)
    public boolean verifcarSenha(@PathVariable String aSenha) {
        return usuarioService.verificarSenha(aSenha);
    }

    @RequestMapping(value = "/verificarEmail/{aEmail:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public boolean verifcarEmail(@PathVariable String aEmail, @PathVariable Long aUsuarioId) {
        return usuarioService.verificarEmail(aEmail, aUsuarioId);
    }

    @RequestMapping(value = "/verificarLogin/{aLogin:.+}/{aUsuarioId}", method = RequestMethod.GET)
    public boolean verifcarLogin(@PathVariable String aLogin, @PathVariable Long aUsuarioId) {
        return usuarioService.verificarLogin(aLogin, aUsuarioId);
    }

    @RequestMapping(value = "/perfil/{aUsuarioId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getPerfis(@PathVariable Long aUsuarioId) {
        return usuarioService.getPerfis(aUsuarioId);
    }

    @RequestMapping(value = "/perfil/{aUsuarioId}", method = RequestMethod.POST)
    public void addPerfilDeAcesso(@PathVariable Long aUsuarioId, @RequestBody Long[] aPerfilId) {
        usuarioService.addPerfil(aUsuarioId, aPerfilId);
    }

    @RequestMapping(value = "/perfil/{aUsuarioId}", method = RequestMethod.DELETE)
    public void deletarPerfilDeAcesso(@PathVariable Long aUsuarioId, @RequestBody Long[] aPerfilId) {
        usuarioService.deletePerfis(aUsuarioId, aPerfilId);
    }

}

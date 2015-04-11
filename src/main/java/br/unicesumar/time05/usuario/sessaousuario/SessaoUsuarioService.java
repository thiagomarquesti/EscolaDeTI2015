package br.unicesumar.time05.usuario.sessaousuario;

import br.unicesumar.time05.usuario.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessaoUsuarioService {
    
    @Autowired
    EntityManager em;
    
    @Autowired
    private SessaoUsuario sessaoUsuario;
        
    public boolean efetuarLogin(String aLogin, String aSenha, HttpSession session){
               
        String JPQLUsuario = "SELECT u "
                           + "  FROM Usuario u "
                           + " WHERE u.login = :login "
                           + "   AND u.senha = :senha ";
        
        Usuario usuario;
        usuario = (Usuario) em.createQuery(JPQLUsuario)
                .setParameter("login", aLogin)
                .setParameter("senha", aSenha)
                .getSingleResult();
        
        if (usuario != null){
            session.setAttribute("usuarioLogado", usuario.getLogin());
            sessaoUsuario.setUsuario(usuario);
            return true;
        }
        
        return false;
    }

    public Usuario getUsuarioLogado() {
        return sessaoUsuario.getUsuario();
    }
}

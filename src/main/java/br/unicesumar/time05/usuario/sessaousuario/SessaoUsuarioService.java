package br.unicesumar.time05.usuario.sessaousuario;

import br.unicesumar.time05.usuario.Usuario;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessaoUsuarioService {
    
    @Autowired
    EntityManager em;
    
    @Autowired
    private SessaoUsuario sessaoUsuario;
        
    public boolean efetuarLogin(DadosLogin aDadosLogin, HttpSession session){
               
        String JPQLUsuario = "SELECT u "
                           + "  FROM Usuario u "
                           + " WHERE u.login = :login "
                           + "   AND u.senha = :senha ";
        
        Usuario usuario;
        usuario = (Usuario) em.createQuery(JPQLUsuario)
                .setParameter("login", aDadosLogin.getLogin())
                .setParameter("senha", aDadosLogin.getSenha())
                .getSingleResult();
        
        if (usuario != null){
            session.setAttribute("usuarioLogado", usuario);
            sessaoUsuario.setUsuario(usuario);
            return true;
        }
        
        return false;
    }

    public Usuario getUsuarioLogado() {
        if(sessaoUsuario != null){
            return sessaoUsuario.getUsuario();
        }
        return null;
    }

    public boolean efetuarLogout(DadosLogin aDadosLogin, HttpSession session) {
        if (sessaoUsuario != null && sessaoUsuario.getUsuario() != null){
            if (sessaoUsuario.getUsuario().getLogin().equals(aDadosLogin.getLogin()) && 
                sessaoUsuario.getUsuario().getSenha().equals(aDadosLogin.getSenha())){
                session.invalidate();
                sessaoUsuario.setUsuario(null);
                return true;
            }
            return false;
        }
        return false;
    }
}

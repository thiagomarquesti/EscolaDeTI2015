package br.unicesumar.time05.usuario.sessaousuario;

import br.unicesumar.time05.rowMapper.MapRowMapper;
import br.unicesumar.time05.usuario.Status;
import br.unicesumar.time05.usuario.Usuario;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SessaoUsuarioService {
    
    @Autowired
    EntityManager em;
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
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
        
        if ((usuario != null) && usuario.getStatus() == Status.ATIVO){
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
        if (this.verificarUsuarioLogado(aDadosLogin)){
            session.invalidate();
            sessaoUsuario.setUsuario(null);
            return true;
        }
        return false;
    }
    
    private boolean verificarUsuarioLogado(DadosLogin aDadosLogin){
        boolean logado = false;
        if (sessaoUsuario != null && sessaoUsuario.getUsuario() != null){
            if (sessaoUsuario.getUsuario().getLogin().equals(aDadosLogin.getLogin()) &&
                sessaoUsuario.getUsuario().getSenha().equals(aDadosLogin.getSenha())){
                logado = true;
            }
        }
        return logado;
    }

    public List<Map<String, Object>> getStatusPorLogin(String aLogin) {
        
        String SQL =
                  "SELECT u.status "
                + "  FROM usuario u "
                + " WHERE u.login = :aLogin";

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        
        List<Map<String, Object>> statusUsuario = jdbcTemplate.query(SQL, params, new MapRowMapper());
        return statusUsuario;
    }
}
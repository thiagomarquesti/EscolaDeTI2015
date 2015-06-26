package br.unicesumar.time05.usuario.sessaousuario;

import br.unicesumar.time05.usuario.ConsultaPersonalizada.QueryPersonalizada;
import br.unicesumar.time05.usuario.Status;
import br.unicesumar.time05.usuario.Usuario;
import br.unicesumar.time05.usuario.UsuarioRepository;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class SessaoUsuarioService {

    @Autowired
    EntityManager em;
    @Autowired
    private QueryPersonalizada query;
    @Autowired
    private SessaoUsuario sessaoUsuario;
    @Autowired
    UsuarioRepository usuarioRepo;

    public boolean efetuarLogin(DadosLogin aDadosLogin, HttpSession session) {

        String SQL = "SELECT u.id "
                + "  FROM usuario u "
                + " WHERE u.login = :login "
                + "   AND u.senha = :senha ";

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", aDadosLogin.getLogin());
        params.addValue("senha", aDadosLogin.getSenha());

        List<Map<String, Object>> result = query.execute(SQL, params);
        Long idUsuario = (Long) result.get(0).get("id");

        Usuario usuario = usuarioRepo.findOne(idUsuario);
        if ((usuario != null) && usuario.getStatus() == Status.ATIVO) {
            session.setAttribute("usuarioLogado", usuario);
            sessaoUsuario.setUsuario(usuario);
            return true;
        }
        return false;
    }

    public Map<String, Object> getUsuarioLogado() {
        if (sessaoUsuario != null && sessaoUsuario.getUsuario() != null) {
            String SQL = "SELECT u.id, u.nome"
                    + "     FROM usuario u"
                    + "    WHERE u.id = :id";

            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", sessaoUsuario.getUsuario().getId());

            return query.execute(SQL, params).get(0);
        }
        return null;
    }

    public void efetuarLogout(HttpSession session) {
        session.invalidate();
        sessaoUsuario.setUsuario(null);
    }

    private boolean verificarUsuarioLogado(DadosLogin aDadosLogin) {
        boolean logado = false;
        if (sessaoUsuario != null && sessaoUsuario.getUsuario() != null) {
            if (sessaoUsuario.getUsuario().getLogin().equals(aDadosLogin.getLogin())
                    && sessaoUsuario.getUsuario().getSenha().equals(aDadosLogin.getSenha())) {
                logado = true;
            }
        }
        return logado;
    }

    public List<Map<String, Object>> getStatusPorLogin(String aLogin) {

        String SQL
                = "SELECT u.status "
                + "  FROM usuario u "
                + " WHERE u.login = :aLogin";

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);

        List<Map<String, Object>> statusUsuario = query.execute(SQL, params);
        return statusUsuario;
    }

    List<Map<String, Object>> getItensDeAcessoUsuarioLogado() {

        if (sessaoUsuario != null && sessaoUsuario.getUsuario() != null) {
            String SQL
                    = "  SELECT ia.id, "
                    + "         ia.nome, "
                    + "         ia.rota, "
                    + "         ia.superior_id "
                    + "    FROM usuario_perfis up "
                    + "    JOIN perfildeacesso pa ON (up.perfis_id = pa.id) "
                    + "    JOIN perfildeacesso_itemacesso pai ON (pa.id = pai.perfildeacesso_id) "
                    + "    JOIN itemacesso ia ON (pai.itemacesso_id = ia.id) "
                    + "   WHERE up.usuario_id = 1 "
                    + "GROUP BY ia.id, "
                    + "         ia.nome, "
                    + "         ia.rota, "
                    + "         ia.superior_id "
                    + "ORDER BY ia.id ";

            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idUsuario", sessaoUsuario.getUsuario().getId());

            List<Map<String, Object>> statusUsuario = query.execute(SQL, params);
            return statusUsuario;
        }
        return null;
    }
}

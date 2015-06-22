package br.unicesumar.time05.usuario.sessaousuario;

import br.unicesumar.time05.itemacesso.ItemAcessoUsuarioInMemory;
import br.unicesumar.time05.rowMapper.MapRowMapper;
import br.unicesumar.time05.usuario.Status;
import br.unicesumar.time05.usuario.Usuario;
import br.unicesumar.time05.usuario.UsuarioRepository;
import java.util.ArrayList;
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
    @Autowired
    UsuarioRepository usuarioRepo;

    public boolean efetuarLogin(DadosLogin aDadosLogin, HttpSession session) {

        String SQL = "SELECT u.idusuario "
                + "  FROM usuario u "
                + " WHERE u.login = :login "
                + "   AND u.senha = :senha ";

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", aDadosLogin.getLogin());
        params.addValue("senha", aDadosLogin.getSenha());

        List<Map<String, Object>> result = jdbcTemplate.query(SQL, params, new MapRowMapper());
        if (result.size() > 0) {

            Long idUsuario = (Long) result.get(0).get("idusuario");

            Usuario usuario = usuarioRepo.findOne(idUsuario);
            if ((usuario != null) && usuario.getStatus() == Status.ATIVO) {
                session.setAttribute("usuarioLogado", usuario);
                sessaoUsuario.setUsuario(usuario);
                return true;
            }

        }
        return false;
    }

    public Map<String, Object> getUsuarioLogado() {
        if (sessaoUsuario != null && sessaoUsuario.getUsuario() != null) {
            String SQL = "SELECT u.idusuario, u.nome"
                    + "     FROM usuario u"
                    + "    WHERE u.idusuario = :id";

            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", sessaoUsuario.getUsuario().getIdUsuario());

            return jdbcTemplate.query(SQL, params, new MapRowMapper()).get(0);
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

        List<Map<String, Object>> statusUsuario = jdbcTemplate.query(SQL, params, new MapRowMapper());
        return statusUsuario;
    }

    public ItemAcessoUsuarioInMemory getItensDeAcessoUsuarioLogado() {

        if (sessaoUsuario != null && sessaoUsuario.getUsuario() != null) {
            String SQL
                    = "  SELECT ia.iditemacesso, "
                    + "         ia.nome, "
                    + "         ia.rota, "
                    + "         ia.superior_id "
                    + "    FROM usuario_perfis up "
                    + "    JOIN perfildeacesso pa ON (up.perfis_id = pa.id) "
                    + "    JOIN perfildeacesso_itemacesso pai ON (pa.id = pai.perfildeacesso_id) "
                    + "    JOIN itemacesso ia ON (pai.itemacesso_id = ia.id) "
                    + "   WHERE up.usuario_id = :idUsuario "
                    + "GROUP BY ia.id, "
                    + "         ia.nome, "
                    + "         ia.rota, "
                    + "         ia.superior_id "
                    + "ORDER BY ia.superior_id NULLS FIRST, ia.id ";

            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idUsuario", sessaoUsuario.getUsuario().getIdUsuario());

            List<Map<String, Object>> resultQuery = jdbcTemplate.query(SQL, params, new MapRowMapper());

            if (!resultQuery.isEmpty()) {

                ItemAcessoUsuarioInMemory itemDeAcesso;

                itemDeAcesso = new ItemAcessoUsuarioInMemory(
                        Long.parseLong(resultQuery.get(0).get("id").toString()),
                        resultQuery.get(0).get("nome").toString(),
                        resultQuery.get(0).get("rota").toString(),
                        null
                );

                itemDeAcesso.setItens(this.getListaDeFilhos(resultQuery, itemDeAcesso));

                return itemDeAcesso;
            } else {
                return null;
            }
        }
        return null;
    }

    private List<ItemAcessoUsuarioInMemory> getListaDeFilhos(List<Map<String, Object>> resultQuery, ItemAcessoUsuarioInMemory itemPai) {

        List<ItemAcessoUsuarioInMemory> listaDeFilhos = new ArrayList<>();

        for (Map<String, Object> item : resultQuery) {
            if (item.get("superior_id") != null && item.get("superior_id").toString().equals(itemPai.getiditemacesso().toString())) {

                ItemAcessoUsuarioInMemory itemDeAcesso;

                itemDeAcesso = new ItemAcessoUsuarioInMemory(
                        Long.parseLong(item.get("iditemacesso").toString()),
                        item.get("nome").toString(),
                        item.get("rota").toString(),
                        null
                );

                itemDeAcesso.setItens(this.getListaDeFilhos(resultQuery, itemDeAcesso));
                listaDeFilhos.add(itemDeAcesso);
            }
        }

        return listaDeFilhos;
    }
}

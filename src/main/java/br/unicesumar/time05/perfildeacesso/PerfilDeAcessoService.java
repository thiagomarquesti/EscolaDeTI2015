package br.unicesumar.time05.perfildeacesso;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import classesBase.ServiceBase;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PerfilDeAcessoService extends ServiceBase<PerfilDeAcesso, Long, PerfilDeAcessoRepository> {

    public PerfilDeAcessoService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(PerfilDeAcesso.class));
    }

    public List<Map<String, Object>> getItensDeAcessoPorPerfilDeAcessoID(Long aId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aId", aId);

        String sql
                = "SELECT i.iditemacesso, "
                + "       i.nome, "
                + "       i.rota, "
                + "       i.superior_id "
                + "  FROM perfildeacesso_itemacesso pi "
                + "  JOIN itemacesso i ON (pi.itemacesso_id = i.iditemacesso) "
                + " WHERE pi.perfildeacesso_id = :aId";

        List<Map<String, Object>> itensPerfilDeAcesso = super.query.execute(sql, params);
        return itensPerfilDeAcesso;
    }

}

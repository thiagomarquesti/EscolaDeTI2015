package br.unicesumar.time05.cidade;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import classesBase.ServiceBase;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CidadeService extends ServiceBase<Cidade, Long, CidadeRepository>{

    public CidadeService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Cidade.class));
    }
    
    private String SQLConsultaCidadePorEstado = "SELECT codigoibge, descricao, estado_codigoestado "
            + " FROM cidade"
            + " WHERE estado_codigoestado = :aEstadoId";
    
    public List<Map<String, Object>> cidadePorEstado(Long aEstadoId){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aEstadoId", aEstadoId);
        List<Map<String, Object>> cidades = query.execute(this.SQLConsultaCidadePorEstado, params);
        return Collections.unmodifiableList(cidades);
    }
}

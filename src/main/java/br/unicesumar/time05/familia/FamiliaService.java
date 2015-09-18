package br.unicesumar.time05.familia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.rowmapper.MapRowMapper;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class FamiliaService extends ServiceBase<Familia, Long, FamiliaRepository> {

    private final String sqlPadrao
            = "SELECT f.idfamilia, "
            + "       f.telefone, "
            + "       f.nomefamilia, "
            + "       f.idrepresentante, "
            + "       ir.nome "
            + "  FROM familia f "
            + "  LEFT JOIN indigena ir ON f.idrepresentante = ir.codigoassindi";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public FamiliaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Familia.class));
        setSqlPadrao(sqlPadrao, "nomefamilia");
        setSqlPadraoPorID(sqlPadrao + " WHERE f.idfamilia = :idfamilia", "nomefamilia", "idfamilia");
    }
    
    public Map<String, Object> getQuantidadeIntegrantesFamilia(Long aIdfamilia){
        String sql = " SELECT count(f.idfamilia) as quantidade "
                   + "   FROM familia f "
                   + "  INNER JOIN familia_indigena fi "
                   + "     ON fi.idfamilia = f.idfamilia "
                   + "  INNER JOIN indigena i "
                   + "     ON i.codigoassindi = fi.codigoassindi "
                   + "  WHERE f.idfamilia = :aIdfamilia ";
        
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aIdfamilia", aIdfamilia);
        List<Map<String, Object>> familia = jdbcTemplate.query(sql, params, new MapRowMapper());
     
        try {
            return familia.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Nenhum resultado encontrado!");
        }
    }
}

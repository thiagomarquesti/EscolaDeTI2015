package br.unicesumar.time05.familia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
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
    
    public Integer getQuantidadeIntegrantesFamilia(Long aIdfamilia){
        String sql = " SELECT count(f.idfamilia) "
                   + "   FROM familia f "
                   + "  INNER JOIN familia_indigena fi "
                   + "     ON fi.idfamilia = f.idfamilia "
                   + "  INNER JOIN indigena i "
                   + "     ON i.codigoassindi = fi.codigoassindi "
                   + "  WHERE f.idfamilia = :aIdfamilia ";
        
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aIdfamilia", aIdfamilia);
        Integer quantidade = jdbcTemplate.queryForObject(sql, params, Integer.class);
        
                
        try {
            return quantidade;
        } catch (Exception e) {
            throw new RuntimeException("Nenhum resultado encontrado!");
        }
    }
}

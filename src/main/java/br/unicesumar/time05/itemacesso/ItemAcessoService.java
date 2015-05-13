package br.unicesumar.time05.itemacesso;

import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.hibernate.cfg.CollectionSecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ItemAcessoService {
    @Autowired
    private ItemAcessoRepository repo;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    public List<Map<String, Object>> getItensAcesso(){
        List<Map<String, Object>> itensDeAcesso = jdbcTemplate.query("SELECT id, nome, rota, superior_id FROM itemacesso", new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(itensDeAcesso);
    }
    
    public List<Map<String, Object>> getItensAcessoPorSuperior(Long aSuperiorId){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aSuperiorId", aSuperiorId);
        
        String sql = "SELECT id, nome, rota, superior_id FROM itemacesso " +
                     "WHERE superior_id = :aSuperiorId";
        
        List<Map<String, Object>> itensDeAcesso = jdbcTemplate.query(sql, params, new MapRowMapper());
        return Collections.unmodifiableList(itensDeAcesso);
    }
    
}
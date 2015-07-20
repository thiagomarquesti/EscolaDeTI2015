package br.unicesumar.time05.etnia;

import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class EtniaService {

    @Autowired
    private EtniaRepository repo;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void salvarEtnia(Etnia aEtnia) {
        repo.save(aEtnia);
    }

    public void alterarEtnia(Etnia aEtnia) {
        repo.save(aEtnia);
    }

    public void removerEtnia(Long aId) {
        repo.delete(aId);
    }

    public List<Map<String, Object>> getEtnias() {
        
        final String SQL = "SELECT idetnia, descricao FROM etnia";
        List<Map<String, Object>> etnias = jdbcTemplate.query(SQL, new MapRowMapper());
        return Collections.unmodifiableList(etnias);
    }
    
    public List<Map<String, Object>> getEtniaById(Long aIdEtnia) {
        
        final String SQL = "SELECT idetnia, descricao FROM etnia WHERE idetnia = :idetnia";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idetnia", aIdEtnia);
        
        List<Map<String, Object>> etnias = jdbcTemplate.query(SQL, params, new MapRowMapper());
        return Collections.unmodifiableList(etnias);
    }
}

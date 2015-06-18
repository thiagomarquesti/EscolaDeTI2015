package br.unicesumar.time05.convenio;

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
class ConvenioService {

    @Autowired
    private ConvenioRepository repo;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    void salvarConvenio(Convenio aConvenio) {
        repo.save(aConvenio);
    }

    public void alterarConvenio(Convenio aConvenio) {
        repo.save(aConvenio);
    }

    void removerConvenio(Long aIdConvenio) {
        repo.delete(aIdConvenio);
    }

    public List<Map<String, Object>> getConvenios() {

        final String SQL = "SELECT idConvenio, descricao FROM CONVENIO";
        List<Map<String, Object>> convenios = jdbcTemplate.query(SQL, new MapRowMapper());
        return Collections.unmodifiableList(convenios);
    }

    public List<Map<String, Object>> getConvenioById(Long aIdConvenio) {

        final String SQL = "SELECT idConvenio, descricao FROM CONVENIO WHERE idConvenio = :idConvenio";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idConvenio", aIdConvenio);

        List<Map<String, Object>> convenios = jdbcTemplate.query(SQL, params, new MapRowMapper());
        return Collections.unmodifiableList(convenios);
    }
}
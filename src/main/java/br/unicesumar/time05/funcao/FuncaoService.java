package br.unicesumar.time05.funcao;

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
class FuncaoService {

    @Autowired
    private FuncaoRepository repo;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    void salvarFuncao(Funcao aFuncao) {
        repo.save(aFuncao);
    }

    public void alterarFuncao(Funcao aFuncao) {
        repo.save(aFuncao);
    }

    void removerFuncao(Long aIdFuncao) {
        repo.delete(aIdFuncao);
    }

    public boolean verificarDescricao(String aDescricao) {

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aDescricao", aDescricao);
        List<Map<String, Object>> funcao = jdbcTemplate.query("SELECT descricao FROM FUNCAO WHERE descricao = :aDescricao", params, new MapRowMapper());
        return funcao.isEmpty();

        
    }

    public List<Map<String, Object>> getFuncoes() {

        final String SQL = "SELECT idfuncao, descricao FROM FUNCAO";
        List<Map<String, Object>> funcoes = jdbcTemplate.query(SQL, new MapRowMapper());
        return Collections.unmodifiableList(funcoes);
    }

    public List<Map<String, Object>> getFuncaoById(Long aIdFuncao) {

        final String SQL = "SELECT idfuncao, descricao FROM FUNCAO WHERE idfuncao = :idfuncao";
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idfuncao", aIdFuncao);

        List<Map<String, Object>> funcoes = jdbcTemplate.query(SQL, params, new MapRowMapper());
        return Collections.unmodifiableList(funcoes);
    }
}
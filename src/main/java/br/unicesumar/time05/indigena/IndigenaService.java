/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicesumar.time05.indigena;

import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author Bruno
 */
public class IndigenaService {
    
    @Autowired
    private IndigenaRepository repo;
    
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void salvar(Indigena indigena) {
        repo.save(indigena);
    }

    public List<Map<String, Object>> getIndigenas() {
        List<Map<String, Object>> indigena = jdbc.query("", new MapSqlParameterSource() ,new MapRowMapper());
        return Collections.unmodifiableList(indigena);
    }

    public List<Map<String, Object>> getIndigenas(Long codigoAssindi) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idIndigena", codigoAssindi);
        List<Map<String, Object>> indigena = jdbc.query("", params,new MapRowMapper());
        return Collections.unmodifiableList(indigena);
        
    }

    public void alterar(Indigena indigena) {
        repo.save(indigena);
    }

    public void deletar(Long codigoAssindi) {
        repo.delete(codigoAssindi);
    }
    
}

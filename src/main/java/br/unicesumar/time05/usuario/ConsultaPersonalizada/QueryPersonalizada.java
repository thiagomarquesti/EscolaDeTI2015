package br.unicesumar.time05.usuario.ConsultaPersonalizada;

import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryPersonalizada {

    final int NUM_REGISTROS_PAGINA = 10;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private MapRowMapper RowMapper;
    @Autowired
    private RetornoConsultaPaginada retornoConsulta;

    public List<Map<String, Object>> execute(String SQL) {
        return this.execute(SQL, new MapSqlParameterSource());
    }

    public List<Map<String, Object>> execute(String SQL, MapSqlParameterSource params) {
        return Collections.unmodifiableList(jdbcTemplate.query(SQL, params, RowMapper));
    }

    public RetornoConsultaPaginada executeComPaginacao(String SQL, MapSqlParameterSource params, ParametrosConsulta parametrosConsulta) {

        if (parametrosConsulta != null) {

            if (!parametrosConsulta.getPalavraChave().isEmpty()) {
                SQL += this.adicionaOperadorCondicional(SQL);
                SQL += parametrosConsulta.getExpressaoParaBusca();
            }
            
            params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_LIKE, parametrosConsulta.getPalavraChave());

            if (!parametrosConsulta.getOrdenarPor().isEmpty()) {
                SQL += OperadoresSQL.ORDER_BY + parametrosConsulta.getOrdenarPor();
            }

            List<Map<String, Object>> result = jdbcTemplate.query(SQL, params, RowMapper);
            retornoConsulta.setTotalDeRegistros(result.size());
            
            Double paginas = (double) result.size() / NUM_REGISTROS_PAGINA;
            retornoConsulta.setPaginas(Math.ceil(paginas));

            if (parametrosConsulta.getPagina() > 0) {
                SQL += OperadoresSQL.LIMIT + NUM_REGISTROS_PAGINA + OperadoresSQL.OFFSET + ((parametrosConsulta.getPagina() * NUM_REGISTROS_PAGINA) - NUM_REGISTROS_PAGINA);
            }
        }

        System.out.println(SQL);
        retornoConsulta.setListaDeRegistros(Collections.unmodifiableList(jdbcTemplate.query(SQL, params, RowMapper)));
        return retornoConsulta;
    }

    private String adicionaOperadorCondicional(String SQL) {

        String operador;
        if (!SQL.contains(OperadoresSQL.WHERE)) {
            operador = OperadoresSQL.WHERE;
        } else {
            operador = OperadoresSQL.AND;
        }

        return operador;
    }
}

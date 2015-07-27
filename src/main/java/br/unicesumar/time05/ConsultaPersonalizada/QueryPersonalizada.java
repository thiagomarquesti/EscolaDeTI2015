package br.unicesumar.time05.ConsultaPersonalizada;

import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.ArrayList;
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
    
    public List<Map<String, Object>> executePorID(String SQL, Object ID){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_IGUAL, ID);
        return Collections.unmodifiableList(jdbcTemplate.query(SQL, params, RowMapper));
    }
    
    public RetornoConsultaPaginada executeComPaginacao(ConstrutorDeSQL construtorDeSQL, ParametrosConsulta parametrosConsulta) {
        String SQL;
        SQL = construtorDeSQL.getSQL(parametrosConsulta);
        return executeComPaginacao(SQL, parametrosConsulta);
    }

    public RetornoConsultaPaginada executeComPaginacao(String SQL, ParametrosConsulta parametrosConsulta){
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        if ((parametrosConsulta != null) && (parametrosConsulta.getPalavraChave() != null) && (!parametrosConsulta.getPalavraChave().isEmpty())) {
            if (!SQL.contains(OperadoresSQL.WHERE.trim())){
                SQL += this.adicionaWhereEmSQL(SQL);
            }
            params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_LIKE, "%" + parametrosConsulta.getPalavraChave() + "%");
            params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_IGUAL, parametrosConsulta.getPalavraChave());
        }
        
        List<Map<String, Object>> result = jdbcTemplate.query(SQL, params, RowMapper);
        retornoConsulta.setTotalDeRegistros(result.size());

        Double paginas = (double) result.size() / NUM_REGISTROS_PAGINA;
        retornoConsulta.setQuantidadeDePaginas((int)Math.ceil(paginas));
        retornoConsulta.setPaginaAtual(parametrosConsulta.getPagina());
        
        if ((parametrosConsulta != null) && (parametrosConsulta.getOrdenarPor() != null) && (!parametrosConsulta.getOrdenarPor().isEmpty())) {
            SQL += (OperadoresSQL.ORDER_BY + parametrosConsulta.getOrdenarPor());
            if ((!parametrosConsulta.getSentidoOrdenacao().isEmpty()) && (parametrosConsulta.getSentidoOrdenacao().equalsIgnoreCase(OperadoresSQL.DESC.trim()))){
                SQL += OperadoresSQL.DESC;
            }
        }

        if ((parametrosConsulta != null) && (parametrosConsulta.getPagina() > 0)) {
            SQL += OperadoresSQL.LIMIT + NUM_REGISTROS_PAGINA + OperadoresSQL.OFFSET + ((parametrosConsulta.getPagina() * NUM_REGISTROS_PAGINA) - NUM_REGISTROS_PAGINA);
        }
        
        System.out.println(SQL);
        retornoConsulta.setListaDeRegistros(Collections.unmodifiableList(jdbcTemplate.query(SQL, params, RowMapper)));
        return retornoConsulta;
    }

    private String adicionaWhereEmSQL(String SQL){
        
        String StringComOsCampos = SQL.substring(SQL.indexOf(OperadoresSQL.SELECT.trim()) + OperadoresSQL.SELECT.trim().length(), SQL.indexOf(OperadoresSQL.FROM.trim()));
        String[] campos = StringComOsCampos.split(",");
        
        String camposDoWhere = "";
        for (String campo : campos){
            if (camposDoWhere.isEmpty()){
                camposDoWhere += "((" + campo.trim() + "::varchar " + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
            } else {
                camposDoWhere += OperadoresSQL.OR + "(" + campo.trim() + "::varchar " + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
            }
        }
        camposDoWhere += ")";
        
        return OperadoresSQL.WHERE + camposDoWhere;
    }
}

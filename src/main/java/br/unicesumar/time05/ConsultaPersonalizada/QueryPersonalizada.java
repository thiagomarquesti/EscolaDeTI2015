package br.unicesumar.time05.ConsultaPersonalizada;

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
    private MapRowMapper rowMapper;
    @Autowired
    private RetornoConsultaPaginada retornoConsulta;

    public List<Map<String, Object>> execute(String aSQL) {
        return this.execute(aSQL, new MapSqlParameterSource());
    }

    public List<Map<String, Object>> execute(String aSQL, MapSqlParameterSource aParams) {
        return Collections.unmodifiableList(jdbcTemplate.query(aSQL, aParams, rowMapper));
    }
    
    public List<Map<String, Object>> executePorID(String aSQL, Object aID){
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_IGUAL, aID);
        return Collections.unmodifiableList(jdbcTemplate.query(aSQL, params, rowMapper));
    }
    
    public RetornoConsultaPaginada executeComPaginacao(ConstrutorDeSQL aConstrutorDeSQL, ParametrosConsulta aParametrosConsulta) {
        String SQL;
        SQL = aConstrutorDeSQL.getSQL(aParametrosConsulta);
        return executeComPaginacao(SQL, aParametrosConsulta);
    }

    public RetornoConsultaPaginada executeComPaginacao(String aSQL, ParametrosConsulta aParametrosConsulta){
        
        MapSqlParameterSource params = new MapSqlParameterSource();
        if ((aParametrosConsulta != null) && (aParametrosConsulta.getPalavraChave() != null) && (!aParametrosConsulta.getPalavraChave().isEmpty())) {
            if (!aSQL.contains(OperadoresSQL.WHERE.trim())){
                aSQL += this.adicionaWhereEmSQL(aSQL);
            }
            params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_LIKE, "%" + aParametrosConsulta.getPalavraChave() + "%");
            params.addValue(OperadoresSQL.NOME_PARAMETRO_PARA_IGUAL, aParametrosConsulta.getPalavraChave());
        }
        
        List<Map<String, Object>> result = jdbcTemplate.query(aSQL, params, rowMapper);
        retornoConsulta.setTotalDeRegistros(result.size());

        Double paginas = (double) result.size() / NUM_REGISTROS_PAGINA;
        retornoConsulta.setQuantidadeDePaginas((int)Math.ceil(paginas));
        retornoConsulta.setPaginaAtual(aParametrosConsulta.getPagina());
        
        if ((aParametrosConsulta != null) && (aParametrosConsulta.getOrdenarPor() != null) && (!aParametrosConsulta.getOrdenarPor().isEmpty())) {
            aSQL += (OperadoresSQL.ORDER_BY + aParametrosConsulta.getOrdenarPor());
            if ((!aParametrosConsulta.getSentidoOrdenacao().isEmpty()) && (aParametrosConsulta.getSentidoOrdenacao().equalsIgnoreCase(OperadoresSQL.DESC.trim()))){
                aSQL += OperadoresSQL.DESC;
            }
        }

        if ((aParametrosConsulta != null) && (aParametrosConsulta.getPagina() > 0)) {
            aSQL += OperadoresSQL.LIMIT + NUM_REGISTROS_PAGINA + OperadoresSQL.OFFSET + ((aParametrosConsulta.getPagina() * NUM_REGISTROS_PAGINA) - NUM_REGISTROS_PAGINA);
        }
        
        System.out.println(aSQL);
        retornoConsulta.setListaDeRegistros(Collections.unmodifiableList(jdbcTemplate.query(aSQL, params, rowMapper)));
        return retornoConsulta;
    }

    private String adicionaWhereEmSQL(String aSQL){
        
        String StringComOsCampos = aSQL.substring(aSQL.indexOf(OperadoresSQL.SELECT.trim()) + OperadoresSQL.SELECT.trim().length(), aSQL.indexOf(OperadoresSQL.FROM.trim()));
        String[] campos = StringComOsCampos.split(",");
        String campoSemFormatacao;
        for (int i = 0; i < campos.length; i++) {
            campoSemFormatacao = campos[i];
            campoSemFormatacao = campoSemFormatacao.trim();
            if (campoSemFormatacao.contains(" ")) {
                campoSemFormatacao = campoSemFormatacao.substring(0, campoSemFormatacao.indexOf(" "));
            }
            campos[i] = campoSemFormatacao;
        }

        String camposDoWhere = "";
        for (String campo : campos) {
            if (camposDoWhere.isEmpty()) {
                camposDoWhere += "((" + campo.trim() + "::varchar " + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
            } else {
                camposDoWhere += OperadoresSQL.OR + "(" + campo.trim() + "::varchar " + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
            }
        }
        camposDoWhere += ")";

        return OperadoresSQL.WHERE + camposDoWhere;
    }
}

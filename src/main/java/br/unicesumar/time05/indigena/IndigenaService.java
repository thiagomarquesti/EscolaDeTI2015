
package br.unicesumar.time05.indigena;

import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@Transactional
public class IndigenaService {
    
    @Autowired
    private IndigenaRepository repo;
    
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public void salvar(Indigena aIndigena) {
        repo.save(aIndigena);
    }

    public List<Map<String, Object>> getIndigenas() {
        List<Map<String, Object>> aIndigena = jdbc.query("SELECT i.codigo_assindi,  i.codigoSUS, "
                + "i.cpf, i.data_nascimento, e.descricao, i.escolaridade,i.estado_civil, "
                + "i.genero, i.nome, t.telefone, ti.nome_terra "
                + "FROM indigena i "
                + "INNER JOIN etnia e "
                + "ON i.etnia_idetnia = e.idetnia "
                + "INNER JOIN indigena_convenio incon "
                + " ON i.codigo_assindi = incon.indigena_id "
                + "INNER JOIN convenio con "
                + "ON incon.convenio_id = con.idconvenio "
                + "INNER JOIN telefone t "
                + "ON i.telefone_idtelefone = t.idtelefone "
                + "INNER JOIN terra_indigena ti "
                + "ON i.terra_indigena_idterraindigena = ti.id_terra_indigena", new MapSqlParameterSource() ,new MapRowMapper());
        return Collections.unmodifiableList(aIndigena);
    }

    public List<Map<String, Object>> getIndigenas(Long aCodigoAssindi) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idIndigena", aCodigoAssindi);
        List<Map<String, Object>> aIndigena = jdbc.query("SELECT i.codigo_assindi,  i.codigoSUS, "
                + "i.cpf, i.data_nascimento, e.descricao, i.escolaridade,i.estado_civil, "
                + "i.genero, i.nome, t.telefone, ti.nome_terra "
                + "FROM indigena i "
                + "INNER JOIN etnia e "
                + "ON i.etnia_idetnia = e.idetnia "
                + "INNER JOIN indigena_convenio incon "
                + " ON i.codigo_assindi = incon.indigena_id "
                + "INNER JOIN convenio con "
                + "ON incon.convenio_id = con.idconvenio "
                + "INNER JOIN telefone t "
                + "ON i.telefone_idtelefone = t.idtelefone "
                + "INNER JOIN terra_indigena ti "
                + "ON i.terra_indigena_idterraindigena = ti.id_terra_indigena "
                + "WHERE i.codigo_assindi = :idIndigena", params,new MapRowMapper());
        return Collections.unmodifiableList(aIndigena);
        
    }

   

    public void deletar(Long aCodigoAssindi) {
        repo.delete(aCodigoAssindi);
    }
    
   
    
}

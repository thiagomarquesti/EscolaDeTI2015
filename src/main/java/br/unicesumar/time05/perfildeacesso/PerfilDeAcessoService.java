package br.unicesumar.time05.perfildeacesso;

import br.unicesumar.time05.itemacesso.ItemAcesso;
import br.unicesumar.time05.itemacesso.ItemAcessoRepository;
import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PerfilDeAcessoService {
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @Autowired
    private PerfilDeAcessoRepository repo;
    @Autowired
    private ItemAcessoRepository ItemRepo;
    
    public List<Map<String, Object>> getPerfisDeAcesso(){
        List<Map<String, Object>> perfisDeAcesso = jdbcTemplate.query("SELECT id, nome FROM perfildeacesso", new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(perfisDeAcesso);
    }
    
    public List<Map<String, Object>> getPerfilDeAcesso(Long aId){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aId", aId);
        List<Map<String, Object>> perfilDeAcesso = jdbcTemplate.query("SELECT id, nome FROM perfildeacesso WHERE id = :aId", params, new MapRowMapper());
        return perfilDeAcesso;
    }

    public List<Map<String, Object>> getItensDeAcessoPorPerfilDeAcessoID(Long aId){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aId", aId);
        
        String sql = 
                  "SELECT i.id, "
                + "       i.nome, "
                + "       i.rota, "
                + "       i.superior_id "
                + "  FROM perfildeacesso_itemacesso pi "
                + "  JOIN itemacesso i ON (pi.itemacesso_id = i.id) "
                + " WHERE pi.perfildeacesso_id = :aId";
        
        List<Map<String, Object>> itensPerfilDeAcesso = jdbcTemplate.query(sql, params, new MapRowMapper());
        return itensPerfilDeAcesso;
    }
    
    public void salvarPerfilDeAcesso(PerfilDeAcesso aPerfilDeAcesso){
        //Para pegar a referência do intem que está no banco, o perfil precisa ser reconstruido antes de ser persistido
        PerfilDeAcesso novo = new PerfilDeAcesso(aPerfilDeAcesso.getNome(), null);
        Set<ItemAcesso> itens = new HashSet<>();
        for (ItemAcesso item : aPerfilDeAcesso.getItens()) {
            itens.add(ItemRepo.findOne(item.getId()));
        }
        novo.setItens(itens);
        repo.save(novo);
    }
    
    public void removerPerfilDeAcesso(Long aId){
        repo.delete(aId);
    }
    
    public void alterarPerfilDeAcesso(PerfilDeAcesso aPerfilDeAcesso){
        repo.save(aPerfilDeAcesso);
    }
}
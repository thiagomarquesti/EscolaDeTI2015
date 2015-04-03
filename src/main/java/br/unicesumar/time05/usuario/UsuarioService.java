package br.unicesumar.time05.usuario;

import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.Collection;
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
public class UsuarioService {
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @Autowired
    private UsuarioRepository usuarioRepo;
    
    public void salvarUsuario(Usuario aUsuario){
        usuarioRepo.save(aUsuario);
    }
    
    public void removerUsuario(Long aUsuarioId){
        try {
            usuarioRepo.delete(aUsuarioId);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não encontrado");
        }
    }
    
    public List<Map<String, Object>> getUsuarios(){
        List<Map<String, Object>> usuarios = jdbcTemplate.query("select id, nome, login, email, senha, status from usuario"
                , new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(usuarios);
    }
    
    public  List<Map<String, Object>> getUsuarioById(Long aUsuarioId){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aUsuarioId", aUsuarioId);
        List<Map<String, Object>> usuario = jdbcTemplate.query("select id, nome, login, email, senha, status from usuario "+
                 "where id = :aUsuarioId", params, new MapRowMapper());
        return usuario;
    }
    
    public boolean verificarLogin(String aLogin){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        List<Map<String, Object>> usuario = jdbcTemplate.query("select login from usuario where login = :aLogin", params, new MapRowMapper());
        if(usuario.size()>0)
            return false;
        else
            return true;
    }
}

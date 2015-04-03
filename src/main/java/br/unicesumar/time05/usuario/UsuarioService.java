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
        usuarioRepo.delete(aUsuarioId);
    }
    
    public List<Usuario> getUsuarios(){
//    public List<Map<String, Object>> getUsuarios(){
//        List<Map<String, Object>> usuarios = jdbcTemplate.query("select id, nome, login, email, senha", new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(usuarioRepo.findAll());
    }
    
    public Usuario getUsuarioById(Long aUsuarioId){
        return usuarioRepo.findOne(aUsuarioId);
    }
    
    public boolean verificarLogin(String aLogin){
        return false;
    }
}

package br.unicesumar.time05.usuario;

import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcessoRepository;
import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    @Autowired
    private PerfilDeAcessoRepository perfilRepo;
    
    public void salvarUsuario(Usuario aUsuario){
        try {
            usuarioRepo.save(aUsuario);
            usuarioRepo.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
      
    public void removerUsuario(Long aUsuarioId){
        try {
            usuarioRepo.delete(aUsuarioId);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não encontrado!");
        }
    }
    
    public List<Map<String, Object>> getUsuarios(){
        List<Map<String, Object>> usuarios = jdbcTemplate.query("SELECT idusuario, nome, login, email, senha, status FROM usuario"
                , new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(usuarios);
    }
    
    public  List<Map<String, Object>> getUsuarioById(Long aUsuarioId){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aUsuarioId", aUsuarioId);
        List<Map<String, Object>> usuario = jdbcTemplate.query("SELECT idusuario, nome, login, email, status FROM usuario "+
                 "WHERE idusuario = :aUsuarioId", params, new MapRowMapper());
        return Collections.unmodifiableList(usuario);
    }
    
    public boolean verificarLogin(String aLogin){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        List<Map<String, Object>> usuario = jdbcTemplate.query("SELECT login FROM usuario WHERE login = :aLogin", params, new MapRowMapper());
        //se o array usuario estiver vazio retorna true, indicando que o login está disponível
        return usuario.isEmpty();
    }
    
    public boolean verificarEmail(String aEmail){
        if(aEmail != null && !aEmail.isEmpty()){
            //verifica se o email é valido
            String email = aEmail;
            String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
            Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if(!matcher.matches()){
                return false;
            }
        
            //verifica se o email já existe no banco
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail);
            List<Map<String, Object>> usuario = jdbcTemplate.query("SELECT email FROM usuario WHERE email = :aEmail", params, new MapRowMapper());
            if(!usuario.isEmpty()){
                return false;
            }
            //se o usuario array de usuario for vazio e o email for valido retorna true, indicando que o
            //endereço de email esta disponivel
            //Caso o array tenha algum valor, significa que o email já está cadastrado então retorna false, indicando email em uso
            //caso o email seja invalido retorna false
            return true;
        }
        else
            throw new RuntimeException("Campo email vazio!");
    }
    
    public boolean verificarSenha(String aSenha){
        boolean valido = false;
        String senha = aSenha;
        
        Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%.]).{6,10})");
        Matcher matcher = pattern.matcher(senha);
        valido = matcher.matches();
  
        return valido;
    }
    
    public void trocarStatusUsuario(Long aUsuarioId){
        try {
            Usuario usuario =  usuarioRepo.getOne(aUsuarioId);
            if(usuario.getStatus()==Status.ATIVO)
                usuario.setStatus(Status.INATIVO);
            else
                usuario.setStatus(Status.ATIVO);
            usuarioRepo.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não encontrado!");
        }
    }

    boolean verificarEmail(String aEmail, Long aUsuarioId) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail);
            params.addValue("aId", aUsuarioId);
            List<Map<String, Object>> usuario = jdbcTemplate.query("SELECT idusuario, email FROM usuario WHERE email = :aEmail AND idusuario <> :aId", params, new MapRowMapper());
            if(!usuario.isEmpty()){
                return false;
            }
            return true;
    }

    boolean verificarLogin(String aLogin, Long aUsuarioId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        params.addValue("aId", aUsuarioId);
        List<Map<String, Object>> usuario = jdbcTemplate.query("SELECT idusuario, login FROM usuario WHERE login = :aLogin AND idusuario <> :aId", params, new MapRowMapper());
        return usuario.isEmpty();
    }
    
    public void addPerfil(Long aUsuarioId, Long[] aPerfilId){
        Usuario usuario = usuarioRepo.findOne(aUsuarioId);
        List<PerfilDeAcesso> perfis = new ArrayList<>();
        for (Long aPerfil : aPerfilId) {
            perfis.add(perfilRepo.findOne(aPerfil));
        }
        usuario.setPerfil(perfis);
        this.salvarUsuario(usuario);
    }

    public List<Map<String, Object>> getPerfis(Long aUsuarioId){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aId", aUsuarioId);
        
        String sql = 
                  "SELECT p.idperfildeacesso, "
                + "       p.nome "
                + "  FROM usuario_perfis up "
                + "  JOIN perfildeacesso p ON (up.perfis_id = p.idperfildeacesso) "
                + " WHERE up.usuario_id = :aId";
        
        List<Map<String, Object>> itensPerfilDeAcesso = jdbcTemplate.query(sql, params, new MapRowMapper());
        return itensPerfilDeAcesso;
    }

    public void deletePerfis(Long aUsuarioId, Long[] perfis){
        Usuario usuario = usuarioRepo.findOne(aUsuarioId);
        for (Long perfil : perfis) {
            usuario.removerPerfil(perfilRepo.findOne(perfil));
        }
    }
}

package br.unicesumar.time05.usuario;

import br.unicesumar.time05.rowMapper.MapRowMapper;
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
    
    public void salvarUsuario(Usuario aUsuario){
        //certifica que o email esteja com letras minusculas e retira espaços no começo e fim da string
        aUsuario.setEmail(aUsuario.getEmail().toLowerCase().trim());
        //valida o email e a o login
        if((this.verificarEmail(aUsuario.getEmail()) && this.verificarLogin(aUsuario.getLogin()) && this.verificarSenha(aUsuario.getSenha()))
                ||aUsuario.getId()!=null)
            usuarioRepo.save(aUsuario);
        else
            throw new RuntimeException("Login e/ou Email já estão em uso");
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
                throw new RuntimeException("Email invalido!");
            }
        
            //verifica se o email já existe no banco
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail);
            List<Map<String, Object>> usuario = jdbcTemplate.query("select email from usuario where email = :aEmail", params, new MapRowMapper());
            
            //se o usuario array de usuario for vazio e o email for valido retorna true, indicando que o
            //endereço de email esta disponivel
            //Caso o array tenha algum valor, significa que o email já está cadastrado então retorna false, indicando email em uso
            //caso o email seja invalido retorna false
            return usuario.isEmpty() && matcher.matches();
        }
        else
            throw new RuntimeException("Campo email vazio!");
    }
    
    public boolean verificarSenha(String aSenha){
        boolean valido[] = {false,false,false,false,false};
        String senha = aSenha;
        
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(senha);
        valido[0] = matcher.find();
        
        pattern = Pattern.compile("[a-z]");
        matcher = pattern.matcher(senha);
        valido[1] = matcher.find();
        
        pattern = Pattern.compile("[0-9]");
        matcher = pattern.matcher(senha);
        valido[2] = matcher.find();
        
        pattern = Pattern.compile("[:-@.]");
        matcher = pattern.matcher(senha);
        valido[3] = matcher.find();

        
        valido[4] = senha.length()<11&&senha.length()>5;
        
      
        
        return valido[0]&&valido[1]&&valido[2]&&valido[3]&&valido[4];
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
            throw new RuntimeException("Usuario não encontrado");
        }
    }
}

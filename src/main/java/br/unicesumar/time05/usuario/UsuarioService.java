package br.unicesumar.time05.usuario;

import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcessoRepository;
import classesBase.ServiceBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class UsuarioService extends ServiceBase<Usuario, Long, UsuarioRepository>{

    @Autowired
    private PerfilDeAcessoRepository perfilRepo;
    
    public UsuarioService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Usuario.class));
    }

    public List<Map<String, Object>> getUsuarios(){
        List<Map<String, Object>> usuarios = query.execute("SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, us.login, us.status, pf.genero, pf.cpf, t.telefone,"
                + " ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.descricao, u.sigla "
                + "FROM pessoa p"
                + " INNER JOIN pessoa_fisica pf "
                + "    ON pf.idpessoa = p.idpessoa"
                + " INNER JOIN pessoa_telefone pt "
                + "    ON pt.pessoa_id = p.idpessoa"
                + " INNER JOIN telefone t "
                + "    ON pt.telefone_id = t.idtelefone"
                + " INNER JOIN endereco ende "
                + "    ON p.endereco_id = ende.idendereco"
                + " INNER JOIN endereco_cidade ec "
                + "    ON ende.idendereco = ec.endereco_id"
                + " INNER JOIN cidade c"
                + "    ON ec.cidade_id = c.codigoibge"
                + " INNER JOIN uf u"
                + "    ON c.estado_codigoestado = u.codigoestado"
                + " INNER JOIN usuario us"
                + "    ON us.idpessoa = p.idpessoa"
                , new MapSqlParameterSource());
        return Collections.unmodifiableList(usuarios);
    }
    
    public  List<Map<String, Object>> getUsuarioById(Long aUsuarioId){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aUsuarioId", aUsuarioId);
        List<Map<String, Object>> usuario = query.execute("SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, us.login, us.status, pf.genero, pf.cpf, t.telefone,"
                + " ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.descricao, u.sigla "
                + "FROM pessoa p"
                + " INNER JOIN pessoa_fisica pf "
                + "    ON pf.idpessoa = p.idpessoa"
                + " INNER JOIN pessoa_telefone pt "
                + "    ON pt.pessoa_id = p.idpessoa"
                + " INNER JOIN telefone t "
                + "    ON pt.telefone_id = t.idtelefone"
                + " INNER JOIN endereco ende "
                + "    ON p.endereco_id = ende.idendereco"
                + " INNER JOIN endereco_cidade ec "
                + "    ON ende.idendereco = ec.endereco_id"
                + " INNER JOIN cidade c"
                + "    ON ec.cidade_id = c.codigoibge"
                + " INNER JOIN uf u"
                + "    ON c.estado_codigoestado = u.codigoestado"
                + " INNER JOIN usuario us"
                + "    ON us.idpessoa = p.idpessoa"
                + " WHERE p.idpessoa = :aUsuarioId", params);
        return Collections.unmodifiableList(usuario);
    }
    public boolean verificarLogin(String aLogin){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        List<Map<String, Object>> usuario = query.execute("SELECT login FROM usuario WHERE login = :aLogin", params);
        //se o array usuario estiver vazio retorna true, indicando que o login está disponível
        return usuario.isEmpty();
    }
    
    public boolean verificarEmail(Email aEmail){
        if(aEmail.verificarValido()){
        
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail.getEmail());
            List<Map<String, Object>> usuario = query.execute("SELECT email FROM pessoa WHERE email = :aEmail", params);
            if(!usuario.isEmpty()){
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }
    public boolean verificarSenha(Senha aSenha){
        return aSenha.senhaValida();
    }

    public void trocarStatusUsuario(Long aUsuarioId) {
        try {
            Usuario usuario = super.repository.getOne(aUsuarioId);
            if (usuario.getStatus() == Status.ATIVO) {
                usuario.setStatus(Status.INATIVO);
            } else {
                usuario.setStatus(Status.ATIVO);
            }
            super.repository.save(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Usuario não encontrado!");
        }
    }

    boolean verificarEmail(String aEmail, Long aUsuarioId) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail);
            params.addValue("aId", aUsuarioId);
            List<Map<String, Object>> usuario = query.execute("SELECT idpessoa, email FROM pessoa WHERE email = :aEmail AND idpessoa <> :aId", params);
            if(!usuario.isEmpty()){
                return false;
            }
            return true;
    }

    boolean verificarLogin(String aLogin, Long aUsuarioId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        params.addValue("aId", aUsuarioId);
        List<Map<String, Object>> usuario = query.execute("SELECT p.idpessoa, u.login FROM usuario u, pessoa p WHERE u.login = :aLogin AND p.idpessoa <> :aId", params);
        return usuario.isEmpty();
    }

    public void addPerfil(Long aUsuarioId, Long[] aPerfilId) {
        Usuario usuario = super.repository.findOne(aUsuarioId);
        List<PerfilDeAcesso> perfis = new ArrayList<>();
        for (Long aPerfil : aPerfilId) {
            perfis.add(perfilRepo.findOne(aPerfil));
        }
        usuario.setPerfil(perfis);
        super.salvar(usuario);
        //this.salvarUsuario(usuario);
    }

    public List<Map<String, Object>> getPerfis(Long aUsuarioId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aId", aUsuarioId);
        String sql = 
                  "SELECT p.idperfildeacesso, "
                + "       p.nome "
                + "  FROM usuario_perfis up "
                + "  JOIN perfildeacesso p ON (up.perfis_idperfildeacesso = p.idperfildeacesso) "
                + " WHERE up.usuario_idpessoa = :aId";
        
        List<Map<String, Object>> itensPerfilDeAcesso = query.execute(sql, params);
        return itensPerfilDeAcesso;
    }

    public void deletePerfis(Long aUsuarioId, Long[] perfis) {
        Usuario usuario = super.repository.findOne(aUsuarioId);
        for (Long perfil : perfis) {
            usuario.removerPerfil(perfilRepo.findOne(perfil));
        }
    }
}

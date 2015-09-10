package br.unicesumar.time05.usuario;

import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.ConsultaPersonalizada.ParametrosConsulta;
import br.unicesumar.time05.ConsultaPersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.cidade.CidadeRepository;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.funcao.FuncaoRepository;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcessoRepository;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;
import br.unicesumar.time05.upload.UploadService;
import classesbase.ServiceBase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public class UsuarioService extends ServiceBase<CriarUsuario, Long, UsuarioRepository> {

    @Autowired
    private PerfilDeAcessoRepository perfilRepo;
    @Autowired
    private FuncaoRepository funcaoRepo;
    @Autowired
    private CidadeRepository cidadeRepo;
    @Autowired
    private UploadService uploadService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final String SQLConsultaUsuarios
            = "SELECT p.idpessoa,"
            + "       p.nome,"
            + "       p.email,"
            + "       p.tipo_pessoa,"
            + "       us.login,"
            + "       us.status,"
            + "       pf.genero,"
            + "       pf.cpf,"
            + "       pf.datanascimento,"
            + "       tl.array_telefone[1] as telefone1,"
            + "       tl.array_telefone[2] as telefone2,"
            + "       ende.bairro,"
            + "       ende.cep,"
            + "       ende.complemento,"
            + "       ende.logradouro,"
            + "       ende.numero,"
            + "       c.codigoibge,"
            + "       u.codigoestado,"
            + "       us.funcao_idfuncao "
            + "FROM pessoa p "
            + "LEFT JOIN pessoa_fisica pf ON pf.idpessoa = p.idpessoa "
            + "LEFT JOIN (SELECT pt.pessoa_id, array_agg(t.telefone) array_telefone"
            + "           FROM pessoa_telefone pt"
            + "           LEFT JOIN telefone t ON pt.telefone_id = t.idtelefone"
            + "           GROUP BY pt.pessoa_id) as tl ON p.idpessoa = tl.pessoa_id "
            + "LEFT JOIN endereco ende ON p.endereco_id = ende.idendereco "
            + "LEFT JOIN endereco_cidade ec ON ende.idendereco = ec.endereco_id "
            + "LEFT JOIN cidade c ON ec.cidade_id = c.codigoibge "
            + "LEFT JOIN uf u ON c.estado_codigoestado = u.codigoestado "
            + "LEFT JOIN usuario us ON us.idpessoa = p.idpessoa";

    private final String SQLConsultaUsuarioPorID
            = "SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, us.login, us.status, pf.genero, pf.cpf, pf.datanascimento, t.telefone,"
            + " ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.codigoibge, u.codigoestado,"
            + " us.funcao_idfuncao"
            + " FROM pessoa p"
            + " LEFT JOIN pessoa_fisica pf "
            + "    ON pf.idpessoa = p.idpessoa"
            + " LEFT JOIN pessoa_telefone pt "
            + "    ON pt.pessoa_id = p.idpessoa"
            + " LEFT JOIN telefone t "
            + "    ON pt.telefone_id = t.idtelefone"
            + " LEFT JOIN endereco ende "
            + "    ON p.endereco_id = ende.idendereco"
            + " LEFT JOIN endereco_cidade ec "
            + "    ON ende.idendereco = ec.endereco_id"
            + " LEFT JOIN cidade c"
            + "    ON ec.cidade_id = c.codigoibge"
            + " LEFT JOIN uf u"
            + "    ON c.estado_codigoestado = u.codigoestado"
            + " LEFT JOIN usuario us"
            + "    ON us.idpessoa = p.idpessoa"
            + " WHERE p.idpessoa = :aUsuarioId";

    private final String ordenar = "p.nome";

    public UsuarioService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Usuario.class));
    }

    @Override
    public void salvar(CriarUsuario aUsuario) {
        Usuario usuario;
        if (repository.count() == 0) {
            usuario = new Usuario(aUsuario.getLogin(), aUsuario.getSenha(), new HashSet<PerfilDeAcesso>(), new CPF(""), Genero.MASCULINO, aUsuario.getNome(), new ArrayList<Telefone>(), aUsuario.getEmail(), new Endereco(), TipoPessoa.USUÁRIO, new Date());
            usuario.setPerfil(perfilRepo.findAll());
        } else {
            Cidade cidade = cidadeRepo.findOne(aUsuario.getCodigoibge());
            Endereco end = new Endereco(aUsuario.getLogradouro(), aUsuario.getNumero(), aUsuario.getBairro(), aUsuario.getComplemento(), aUsuario.getCep(), cidade);
            if (aUsuario.getLogin() == null || aUsuario.getLogin() == "") {
                usuario = new Usuario(aUsuario, end, funcaoRepo.findOne(aUsuario.getIdfuncao()));
            } else {
                if (!verificarLogin(aUsuario.getLogin())) {
                    throw new RuntimeException("Login já existe no sistema");
                }
                usuario = new Usuario(aUsuario, end, funcaoRepo.findOne(aUsuario.getIdfuncao()));
                List<PerfilDeAcesso> perfis = new ArrayList<>();
                for (PerfilDeAcesso p : aUsuario.getPerfis()) {
                    perfis.add(perfilRepo.findOne(p.getIdperfildeacesso()));
                }
                usuario.setPerfil(perfis);
            }
            usuario.setTipoPessoa(TipoPessoa.USUÁRIO);
            List<Telefone> telefones = usuario.getTelefones();
            if (telefones.get(0).equals(telefones.get(1))) {
                telefones.remove(1);
            }
            telefones.remove("");
            usuario.setTelefones(telefones);
        }

        try {
            repository.saveAndFlush(usuario);
            if (repository.count() > 1 && aUsuario.getImgSrc() != null && aUsuario.getImgSrc().startsWith("data:image/jpeg;base64")) {
                uploadService.uploadWebcam(aUsuario.getImgSrc(), usuario.getIdpessoa(), "users");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> findByID(Long aUsuarioId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aUsuarioId", aUsuarioId);
        List<Map<String, Object>> usuario = query.execute(this.SQLConsultaUsuarioPorID, params);
        return Collections.unmodifiableList(usuario);
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(this.SQLConsultaUsuarios, ordenar, parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(this.SQLConsultaUsuarios, ordenar, new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        List<Map<String, Object>> usuarios = query.execute(this.SQLConsultaUsuarios, new MapSqlParameterSource());
        return Collections.unmodifiableList(usuarios);
    }

    @Override
    public void alterar(CriarUsuario aUsuario) {
        Usuario usuario = repository.findOne(aUsuario.getIdpessoa());
        usuario.alterar(aUsuario);
        usuario.getEndereco().setCidade(cidadeRepo.findOne(aUsuario.getCodigoibge()));
        usuario.setFuncao(funcaoRepo.findOne(aUsuario.getIdfuncao()));
        if (aUsuario.getImgSrc() != null && aUsuario.getImgSrc().startsWith("data:image/jpeg;base64")) {
            uploadService.uploadWebcam(aUsuario.getImgSrc(), aUsuario.getIdpessoa(), "users");
        }
        repository.save(usuario);
    }

    public boolean verificarLogin(String aLogin) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aLogin", aLogin);
        List<Map<String, Object>> usuario = query.execute("SELECT login FROM usuario WHERE login = :aLogin", params);
        //se o array usuario estiver vazio retorna true, indicando que o login está disponível
        return usuario.isEmpty();
    }

    public boolean verificarSenha(Senha aSenha) {
        return aSenha.senhaValida();
    }

    public Map<String, String> verificarCpf(CPF aCpf) {
        Map<String, String> retorno = new HashMap<>();
        if (aCpf.valido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aCpf", aCpf.getCpf());
            List<Map<String, Object>> usuario = query.execute("SELECT cpf FROM pessoa_fisica WHERE cpf = :aCpf", params);
            if (!usuario.isEmpty()) {
                retorno.put("retorno", "existe");
            } else {
                retorno.put("retorno", "valido");
            }
        } else {
            retorno.put("retorno", "invalido");
        }
        return retorno;
    }

    public Map<String, String> verificarCpf(CPF aCpf, Long aUsuarioId) {
        Map<String, String> retorno = new HashMap<>();
        if (aCpf.valido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aCpf", aCpf.getCpf());
            params.addValue("aId", aUsuarioId);
            List<Map<String, Object>> usuario = query.execute("SELECT idpessoa, cpf FROM pessoa_fisica WHERE cpf = :aCpf AND idpessoa <> :aId", params);
            if (!usuario.isEmpty()) {
                retorno.put("retorno", "existe");
            }
            retorno.put("retorno", "valido");
        } else {
            retorno.put("retorno", "invalido");
        }
        return retorno;
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

    public boolean verificarEmail(Email aEmail, Long aUsuarioId) {
        if (aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail.getEmail());
            params.addValue("aId", aUsuarioId);
            List<Map<String, Object>> usuario = query.execute("SELECT idpessoa, email FROM pessoa WHERE email = :aEmail AND idpessoa <> :aId", params);
            if (!usuario.isEmpty()) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarEmail(Email aEmail) {
        if (aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail.getEmail());
            List<Map<String, Object>> usuario = query.execute("SELECT email FROM pessoa WHERE email = :aEmail", params);
            if (!usuario.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }

    public boolean verificarLogin(String aLogin, Long aUsuarioId
    ) {
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
        repository.save(usuario);
        //this.salvarUsuario(usuario);
    }

    public List<Map<String, Object>> getPerfis(Long aUsuarioId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aId", aUsuarioId);
        String sql
                = "SELECT p.idperfildeacesso, "
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

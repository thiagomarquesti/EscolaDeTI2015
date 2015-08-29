package br.unicesumar.time05.pessoaJuridica;

import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.rowMapper.MapRowMapper;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class JuridicaService {

    @Autowired
    private JuridicaRepository juridicaRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void salvarJuridica(PessoaJuridica aPessoa) {
        try {
            juridicaRepo.save(aPessoa);
            juridicaRepo.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removerJuridica(Long aPessoaId) {
        try {
            juridicaRepo.delete(aPessoaId);
        } catch (Exception e) {
            throw new RuntimeException("Pessoa não encontrada");
        }
    }

    public List<Map<String, Object>> getJuridica() {
        List<Map<String, Object>> juridica = jdbcTemplate.query("SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pj.cnpj, t.telefone,"
                + " ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.descricao, u.sigla "
                + "FROM pessoa p"
                + " INNER JOIN pessoa_juridica pj "
                + "    ON pj.idpessoa = p.idpessoa"
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
                + "    ON c.estado_codigoestado = u.codigoestado",
                new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(juridica);
    }

    public Map<String, Object> getJuridicaById(Long aPessoaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aPessoaId", aPessoaId);
        List<Map<String, Object>> juridica = jdbcTemplate.query("SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pj.genero, pj.cnpj, t.telefone,"
                + " ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.descricao, u.sigla "
                + "FROM pessoa p"
                + " INNER JOIN pessoa_juridica pj "
                + "    ON pj.idpessoa = p.idpessoa"
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
                + " WHERE p.idpessoa = :aPessoaId",
                params, new MapRowMapper());
        try {
            return juridica.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Nenhum resultado encontrado!");
        }
    }

    public boolean verificarEmail(Email aEmail) {
        if (aEmail != null && aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail);
            List<Map<String, Object>> pessoa = jdbcTemplate.query("SELECT email FROM pessoa WHERE email = :aEmail", params, new MapRowMapper());
            if (!pessoa.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }

    public void trocarTipoJuridica(Long aPessoaId, String tipo) {
        PessoaJuridica pessoa = juridicaRepo.getOne(aPessoaId);
        switch (tipo) {
            case "USUÁRIO":
                pessoa.setTipoPessoa(TipoPessoa.USUÁRIO);
                break;
            case "VISITANTE":
                pessoa.setTipoPessoa(TipoPessoa.VISITANTE);
                break;
            case "ÍNDIO":
                pessoa.setTipoPessoa(TipoPessoa.ÍNDIO);
                break;
        }
        juridicaRepo.save(pessoa);
    }

    boolean verificarEmail(String aEmail, Long aPessoaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aEmail", aEmail);
        params.addValue("aId", aPessoaId);
        List<Map<String, Object>> pessoa = jdbcTemplate.query("SELECT id, email FROM pessoa WHERE email = :aEmail AND id <> :aId", params, new MapRowMapper());
        if (!pessoa.isEmpty()) {
            return false;
        }
        return true;
    }

}

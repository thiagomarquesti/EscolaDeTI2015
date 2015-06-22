package br.unicesumar.time05.pessoaFisica;

import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.rowMapper.MapRowMapper;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;
import br.unicesumar.time05.uf.UF;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class FisicaService {

    @Autowired
    private FisicaRepository fisicaRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void salvarFisica(PessoaFisica aPessoa) {
        try {
            fisicaRepo.save(aPessoa);
            fisicaRepo.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removerFisica(Long aPessoaId) {
        try {
            fisicaRepo.delete(aPessoaId);
        } catch (Exception e) {
            throw new RuntimeException("Pessoa não encontrada");
        }
    }

    public List<Map<String, Object>> getFisica() {
//    public PessoaFisica getFisica() {
        List<Map<String, Object>> fisica = jdbcTemplate.query("SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pf.genero, pf.cpf, t.telefone,"
                + " ende.bairro, ende.cep, ende.complemento, ende.logradouro, ende.numero, c.nome, u.sigla "
                + "FROM pessoa p"
                + " INNER JOIN pessoa_fisica pf "
                + "    ON pf.idpessoa = p.idpessoa"
                + " INNER JOIN pessoa_telefone pt "
                + "    ON pt.pessoa_id = p.idpessoa"
                + " INNER JOIN telefone t "
                + "    ON pt.telefone_id = t.telefone"
                + " INNER JOIN endereco ende "
                + "    ON p.endereco_id = ende.idendereco"
                + " INNER JOIN endereco_cidade ec "
                + "    ON ende.idendereco = ec.endereco_id "
                + "INNER JOIN cidade c "
                + "    ON ec.cidade_id = c.codigoibge"
                + " INNER JOIN endereco_estado ee "
                + "    ON ende.idendereco = ee.endereco_id "
                + "INNER JOIN uf u "
                + "    ON ee.estado_id = u.sigla",
                new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(fisica);
//        return fisicaRepo.findOne(1l);
    }

    public Map<String, Object> getFisicaById(Long aPessoaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aPessoaId", aPessoaId);
        List<Map<String, Object>> pessoa = jdbcTemplate.query("SELECT p.idpessoa, p.nome, p.tipo_pessoa, pf.genero FROM pessoa p JOIN pessoa_fisica pf ON pf.idpessoa = p.idpessoa"
                + "WHERE id = :aPessoaId", params, new MapRowMapper());
        return pessoa.get(0);
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

    public void trocarTipoPessoa(Long aPessoaId, String tipo) {
        PessoaFisica pessoa = fisicaRepo.getOne(aPessoaId);
        switch (tipo) {
            case "USUÁRIO":
                pessoa.setTipo(TipoPessoa.USUÁRIO);
                break;
            case "VISITANTE":
                pessoa.setTipo(TipoPessoa.VISITANTE);
                break;
            case "ÍNDIO":
                pessoa.setTipo(TipoPessoa.ÍNDIO);
                break;
        }
        fisicaRepo.save(pessoa);
    }

    boolean verificarEmail(String aEmail, Long aPessoaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aEmail", aEmail);
        params.addValue("aId", aPessoaId);
        List<Map<String, Object>> fisica = jdbcTemplate.query("SELECT id, email FROM pessoa WHERE email = :aEmail AND id <> :aId", params, new MapRowMapper());
        if (!fisica.isEmpty()) {
            return false;
        }
        return true;
    }

}

package br.unicesumar.time05.pessoa;

import br.unicesumar.time05.email.Email;
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
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void salvarPessoa(Pessoa aPessoa) {
        try {
            pessoaRepo.save(aPessoa);
            pessoaRepo.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removerPessoa(Long aPessoaId) {
        try {
            pessoaRepo.delete(aPessoaId);
        } catch (Exception e) {
            throw new RuntimeException("Pessoa não encontrada");
        }
    }

    public List<Map<String, Object>> getPessoas() {
        List<Map<String, Object>> pessoa = jdbcTemplate.query("SELECT id, nome, telefones, email, enderecos, tipoPessoa FROM pessoa",
                new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(pessoa);
    }

    public Map<String, Object> getPessoaById(Long aPessoaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aPessoaId", aPessoaId);
        List<Map<String, Object>> pessoa = jdbcTemplate.query("SELECT id, nome, login, email, status FROM pessoa "
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
        Pessoa pessoa = pessoaRepo.getOne(aPessoaId);
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
        pessoaRepo.save(pessoa);
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

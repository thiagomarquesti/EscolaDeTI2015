package br.unicesumar.time05.pessoaFisica;

import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.rowMapper.MapRowMapper;
import br.unicesumar.time05.pessoa.TipoPessoa;
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
        List<Map<String, Object>> fisica = jdbcTemplate.query("SELECT idfisica, nome, telefones, email, enderecos, tipoPessoa, cpf, genero FROM pessoa",
                new MapSqlParameterSource(), new MapRowMapper());
        return Collections.unmodifiableList(fisica);
    }

    public Map<String, Object> getFisicaById(Long aPessoaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aPessoaId", aPessoaId);
        List<Map<String, Object>> pessoa = jdbcTemplate.query("SELECT idfisica, nome, telefones, email, enderecos, tipoPessoa, cpf, genero FROM pessoa "
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

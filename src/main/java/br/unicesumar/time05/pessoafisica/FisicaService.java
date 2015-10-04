package br.unicesumar.time05.pessoafisica;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.rowmapper.MapRowMapper;
import br.unicesumar.time05.pessoa.TipoPessoa;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class FisicaService extends ServiceBase<PessoaFisica, Long, FisicaRepository>{

    @Autowired
    private FisicaRepository fisicaRepo;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    final String SQLConsultaFisica = "SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pf.genero, pf.cpf, t.telefone,"
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
                + "    ON c.estado_codigoestado = u.codigoestado";

    @Override
    protected void setConstrutorDeSQL(br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL aConstrutorDeSQL) {
        super.setConstrutorDeSQL(new ConstrutorDeSQL(PessoaFisica.class)); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaFisica);
    }
    
    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaFisica, "p.nome", new ParametrosConsulta());
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        return query.executeComPaginacao(SQLConsultaFisica, "p.nome", aParametrosConsulta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObjeto(Long aId) {
        return repository.findOne(aId);
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
                pessoa.setTipoPessoa(TipoPessoa.USUÁRIO);
                break;
            case "VISITANTE":
                pessoa.setTipoPessoa(TipoPessoa.VISITANTE);
                break;
            case "FÍSICA":
                pessoa.setTipoPessoa(TipoPessoa.FÍSICA);
                break;
            case "JURÍDICA":
                pessoa.setTipoPessoa(TipoPessoa.JURÍDICA);
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

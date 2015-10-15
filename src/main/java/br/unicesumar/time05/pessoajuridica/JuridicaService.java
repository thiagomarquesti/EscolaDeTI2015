package br.unicesumar.time05.pessoajuridica;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.email.Email;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class JuridicaService extends ServiceBase<PessoaJuridica, Long, JuridicaRepository> {

    final String SQLConsultaJuridica = "SELECT p.idpessoa, p.nome, p.email, p.tipo_pessoa, pj.cnpj, t.telefone,"
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
            + "    ON c.estado_codigoestado = u.codigoestado";

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaJuridica);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaJuridica, "p.nome", new ParametrosConsulta()); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getObjeto(Long aId) {
        return repository.findOne(aId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        return query.executeComPaginacao(SQLConsultaJuridica, "p.nome", aParametrosConsulta); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void setConstrutorDeSQL(br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL aConstrutorDeSQL) {
        super.setConstrutorDeSQL(new ConstrutorDeSQL(PessoaJuridica.class)); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean verificarEmail(Email aEmail) {
        if (aEmail != null && aEmail.verificarValido()) {
            final MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("aEmail", aEmail);
            List<Map<String, Object>> pessoa = query.execute("SELECT email FROM pessoa WHERE email = :aEmail", params);
            if (!pessoa.isEmpty()) {
                return false;
            }
            return true;
        } else {
            throw new RuntimeException("Campo email vazio!");
        }
    }

    boolean verificarEmail(String aEmail, Long aPessoaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aEmail", aEmail);
        params.addValue("aId", aPessoaId);
        List<Map<String, Object>> pessoa = query.execute("SELECT id, email FROM pessoa WHERE email = :aEmail AND id <> :aId", params);
        if (!pessoa.isEmpty()) {
            return false;
        }
        return true;
    }

}

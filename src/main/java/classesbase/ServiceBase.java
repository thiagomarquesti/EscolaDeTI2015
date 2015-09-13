package classesbase;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.QueryPersonalizada;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ServiceBase<Entidade extends Object, ID extends Serializable, Reposity extends JpaRepository> {

    @Autowired
    public Reposity repository;

    @Autowired
    protected QueryPersonalizada query;

    private ConstrutorDeSQL ConstrutorDeSQL;

    protected void setConstrutorDeSQL(ConstrutorDeSQL aConstrutorDeSQL) {
        this.ConstrutorDeSQL = aConstrutorDeSQL;
    }

    public void salvar(Entidade aEntidade) {
        repository.save(aEntidade);
    }

    public void remover(ID aID) {
        repository.delete(aID);
    }

    public void alterar(Entidade aEntidade) {
        repository.save(aEntidade);
    }

    public List<Map<String, Object>> findByID(ID aID) {
        return query.executePorID(ConstrutorDeSQL.getSQLComWherePorID(), aID);
    }

    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        return query.executeComPaginacao(ConstrutorDeSQL, aParametrosConsulta);
    }

    public Object getObjeto(ID aId) {
        return repository.findOne(aId);
    }

    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(ConstrutorDeSQL, new ParametrosConsulta());
    }

    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(ConstrutorDeSQL.getSQL(new ParametrosConsulta()));
    }
}

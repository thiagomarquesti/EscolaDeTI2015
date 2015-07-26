package classesBase;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.ConsultaPersonalizada.ParametrosConsulta;
import br.unicesumar.time05.ConsultaPersonalizada.QueryPersonalizada;
import br.unicesumar.time05.ConsultaPersonalizada.RetornoConsultaPaginada;
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

    private ConstrutorDeSQL construtorDeSQL;

    protected void setConstrutorDeSQL(ConstrutorDeSQL construtorDeSQL) {
        this.construtorDeSQL = construtorDeSQL;
    }

    public void salvar(Entidade Entidade) {
        repository.save(Entidade);
    }

    public void remover(ID aID) {
        repository.delete(aID);
    }

    public void alterar(Entidade Entidade) {
        repository.save(Entidade);
    }

    public List<Map<String, Object>> findByID(ID id) {
        return query.executePorID(construtorDeSQL.getSQLComWherePorID(), id);
    }

    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(construtorDeSQL, parametrosConsulta);
    }
    
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(construtorDeSQL, new ParametrosConsulta());
    }
    
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(construtorDeSQL.getSQL(new ParametrosConsulta()));
    }
}

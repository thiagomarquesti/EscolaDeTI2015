package br.unicesumar.time05.eventos;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import java.io.Serializable;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class EventosService extends ServiceBase<Eventos, Long, EventosRepository> {

    private final String sql
            = "      select e.descricao, "
            + " 	    e.horainicial, "
            + " 	    e.horafinal, "
            + " 	    e.datainicial, "
            + " 	    e.datafinal, "
            + " 	    e.visualizarnocalendario, "
            + "             'EVENTO' as tipo "
            + "  from eventos e "
            + " union all "
            + "select 'Aniversário '||p.nome as descricao, "
            + "       '00:00:00' as horainicial, "
            + "       '23:59:00' as horafinal, "
            + "       pf.datanascimento as datainicial, "
            + "       pf.datanascimento as datafinal, "
            + "       true as visualizarnocalendario, "
            + "       'ANIVERSARIO' as tipo "
            + "  from pessoa p "
            + " inner join pessoa_fisica pf "
            + "    on pf.idpessoa = p.idpessoa ";

    public EventosService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Eventos.class));
        setSqlPadrao(sql, "descricao");
    }

}

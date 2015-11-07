package br.unicesumar.time05.eventos;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
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
            + "    on pf.idpessoa = p.idpessoa "
            + " union all "
            + " select 'Aniversário '||i.nome as descricao, "
            + "        '00:00:00' as horainicial, "
            + "        '23:59:00' as horafinal, "
            + "        i.datanascimento as datainicial, "
            + "        i.datanascimento as datafinal, "
            + "        true as visualizarnocalendario, "
            + "        'ANIVERSARIO' as tipo "
            + "   from indigena i ";

    public EventosService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Eventos.class));
    }

    public List<Map<String, Object>> carregaCalendario() {
        return super.query.execute(sql);
    }

}

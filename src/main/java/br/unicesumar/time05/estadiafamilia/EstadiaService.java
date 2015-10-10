package br.unicesumar.time05.estadiafamilia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import org.springframework.stereotype.Component;

@Component
public class EstadiaService extends ServiceBase<Estadia, Long, EstadiaRepository> {

    String sqlPadrao
            = "  SELECT es.dataentrada, "
            + "         es.datasaida, "
            + "         es.idfamilia, "
            + "         es.observacoesentrada, "
            + "         es.observacoessaida, "
            + "         COUNT(ei.codigoassindi) as quantidademembros "
            + "    FROM estadia es "
            + "    LEFT JOIN estadia_indigena ei ON es.idestadia = ei.idestadia "
            + "GROUP BY es.idestadia ";

    public EstadiaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Estadia.class));
        setSqlPadrao(sqlPadrao, "es.dataentrada");
        setSqlPadraoPorID(sqlPadrao + " WHERE es.idestadia", "es.dataentrada", "idestadia");
    }
}

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

    public EventosService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Eventos.class));
    }
    
}

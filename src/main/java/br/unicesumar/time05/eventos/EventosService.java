package br.unicesumar.time05.eventos;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
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

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta aParametrosConsulta) {
        String palavraChave = aParametrosConsulta.getPalavraChave();
        if(palavraChave.equalsIgnoreCase("sim"))
            aParametrosConsulta.setPalavraChave("TRUE");
        if(palavraChave.equalsIgnoreCase("não"))
            aParametrosConsulta.setPalavraChave("FALSE");
        
        return super.listar(aParametrosConsulta); //To change body of generated methods, choose Tools | Templates.
    }
    
}

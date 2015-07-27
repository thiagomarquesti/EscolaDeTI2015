package br.unicesumar.time05.etnia;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import classesBase.ServiceBase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class EtniaService extends ServiceBase<Etnia, Long, EtniaRepository> {

    public EtniaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Etnia.class));
    }

}

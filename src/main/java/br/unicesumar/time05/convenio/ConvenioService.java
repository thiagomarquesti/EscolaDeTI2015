package br.unicesumar.time05.convenio;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.classesbase.ServiceBase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
class ConvenioService extends ServiceBase<Convenio, Long, ConvenioRepository> {

    public ConvenioService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Convenio.class));
    }
}

package br.unicesumar.time05.ocorrencia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class OcorrenciaService extends ServiceBase<Ocorrencia, Long, OcorrenciaRepository> {
     public OcorrenciaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Ocorrencia.class));
    }
   
}

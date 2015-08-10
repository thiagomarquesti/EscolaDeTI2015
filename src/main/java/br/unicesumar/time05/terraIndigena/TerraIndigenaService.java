package br.unicesumar.time05.terraIndigena;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import classesBase.ServiceBase;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TerraIndigenaService extends ServiceBase<TerraIndigena, Long, TerraIndigenaRepository>{

    public TerraIndigenaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(TerraIndigena.class));
    }

}

package br.unicesumar.time05.terraIndigena;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import classesBase.ServiceBase;

class TerraIndigenaService extends ServiceBase<TerraIndigena, Long, TerraIndigenaRepository> {

    public TerraIndigenaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(TerraIndigena.class));
    }

}

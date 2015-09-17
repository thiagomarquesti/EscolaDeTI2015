package br.unicesumar.time05.familia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import classesbase.ServiceBase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class FamiliaService extends ServiceBase<Familia, Long, FamiliaRepository> {

    private final String sqlPadrao
            = "SELECT f.idfamilia, "
            + "       f.telefone, "
            + "       f.nomefamilia, "
            + "       f.idrepresentante, "
            + "       ir.nome "
            + "  FROM familia f "
            + "  LEFT JOIN indigena ir ON f.idrepresentante = ir.codigoassindi";

    public FamiliaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Familia.class));
        setSqlPadrao(sqlPadrao, "nomefamilia");
        setSqlPadraoPorID(sqlPadrao + " WHERE f.idfamilia = :idfamilia", "nomefamilia", "idfamilia");
    }
}

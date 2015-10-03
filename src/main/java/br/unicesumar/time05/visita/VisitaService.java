package br.unicesumar.time05.visita;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.ConsultaPersonalizada.ParametrosConsulta;
import br.unicesumar.time05.ConsultaPersonalizada.RetornoConsultaPaginada;
import classesBase.ServiceBase;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class VisitaService extends ServiceBase<Visita, Long, VisitaRepository> {

    private final String SQLConsultaVisita = ("SELECT v.idvisita, "
            + " v.datavisita, "
            + " v.horavisita, "
            + " v.quantidadedevisitantes, "
            + " v.observacao, "
            + " v.horaentrada, "
            + " v.horasaida, "
            + " v.seriecurso, "
            + " v.pessoaresponsavel, "
            + " v.entidade "
            + " FROM visita v ");
            //+ " LEFT JOIN pessoa e "
    //+ " ON v.descricao_identidade = p.identidade "
    //+ " LEFT JOIN pessoa p "
    //+ " ON v.pessoaresponsavel_idpessoa = p.idpessoa ");

    public VisitaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Visita.class));
    }

    @Override
    public List<Map<String, Object>> findByID(Long aVisitaId) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aVisitaId", aVisitaId);
        List<Map<String, Object>> visita = query.execute(this.SQLConsultaVisita + "WHERE v.idvista =:aVisitaId", params);
        return Collections.unmodifiableList(visita);
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(this.SQLConsultaVisita, parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(this.SQLConsultaVisita, new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        List<Map<String, Object>> visitas = query.execute(this.SQLConsultaVisita, new MapSqlParameterSource());
        return Collections.unmodifiableList(visitas);
    }
}

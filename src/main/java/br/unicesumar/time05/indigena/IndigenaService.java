package br.unicesumar.time05.indigena;

import br.unicesumar.time05.ConsultaPersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.ConsultaPersonalizada.ParametrosConsulta;
import br.unicesumar.time05.ConsultaPersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.terraIndigena.TerraIndigenaRepository;
import classesBase.ServiceBase;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class IndigenaService extends ServiceBase<CriarIndigena, Long, IndigenaRepository> {

    public IndigenaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Indigena.class));
    }

    @Autowired
    TerraIndigenaRepository terraRepository;
    
    //Select modigicado dia 08/08 Bruno Fiorentini/Thiago Marialva
    private final String SQLConsultaIndigena = "SELECT i.codigo_assindi,  i.codigoSUS, "
            + "i.cpf, i.data_nascimento, e.descricao, i.escolaridade,i.estado_civil, "
            + "i.genero, i.nome, t.telefone, ti.nometerra "
            + "FROM indigena i "
            + "LEFT JOIN etnia e "
            + " ON i.etnia_idetnia = e.idetnia "
            + "LEFT JOIN telefone t "
            + "ON i.telefone_idtelefone = t.idtelefone "
            + "LEFT JOIN terraindigena ti "
            + "ON i.terra_indigena_idterraindigena = ti.idterraindigena";

    //Select modigicado dia 08/08 Bruno Fiorentini/Thiago Marialva
    private final String SQLCOnsultaIndigenaPorId = "SELECT i.codigo_assindi,  i.codigoSUS, "
            + "i.cpf, i.data_nascimento, e.descricao, i.escolaridade,i.estado_civil, "
            + "i.genero, i.nome, t.telefone, ti.nometerra "
            + "FROM indigena i "
            + "LEFT JOIN etnia e "
            + " ON i.etnia_idetnia = e.idetnia "
            + "LEFT JOIN telefone t "
            + " ON i.telefone_idtelefone = t.idtelefone "
            + "LEFT JOIN terraindigena ti "
            + " ON i.terra_indigena_idterraindigena = ti.idterraindigena "
            + "WHERE i.codigo_assindi = :idIndigena";

    @Override
    public void salvar(CriarIndigena aCIndigena){
        Indigena i = new Indigena(aCIndigena.getNome(), aCIndigena.getCpf(), aCIndigena.getEtnia(), aCIndigena.getGenero(), aCIndigena.getDataNascimento(), aCIndigena.getConvenio(), aCIndigena.getTelefone(), null, aCIndigena.getEscolaridade(), aCIndigena.getEstadoCivil(), aCIndigena.getCodigoSUS());
        i.setTerraIndigena(terraRepository.findOne(aCIndigena.getTerraIndigena()));
        repository.save(i);
    }
    
    @Override
    public List<Map<String, Object>> findByID(Long aCodigoAssindi) {
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idIndigena", aCodigoAssindi);

        List<Map<String, Object>> aIndigena = query.execute(SQLCOnsultaIndigenaPorId, params);

        return Collections.unmodifiableList(aIndigena);
    }

    @Override
    public RetornoConsultaPaginada listar(ParametrosConsulta parametrosConsulta) {
        return query.executeComPaginacao(SQLConsultaIndigena, parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaIndigena, new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaIndigena);
    }
    
}

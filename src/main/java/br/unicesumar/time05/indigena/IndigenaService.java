package br.unicesumar.time05.indigena;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.consultapersonalizada.ParametrosConsulta;
import br.unicesumar.time05.consultapersonalizada.RetornoConsultaPaginada;
import br.unicesumar.time05.etnia.Etnia;
import br.unicesumar.time05.etnia.EtniaService;
import br.unicesumar.time05.terraindigena.TerraIndigena;
import br.unicesumar.time05.terraindigena.TerraIndigenaService;
import br.unicesumar.time05.upload.UploadService;
import classesbase.ServiceBase;
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
    private TerraIndigenaService terraService;
    @Autowired
    private EtniaService etniaService;
    @Autowired
    private UploadService uploadService;

    //Select modigicado dia 08/08 Bruno Fiorentini/Thiago Marialva
    private final String SQLConsultaIndigena = "SELECT i.codigoassindi,  i.codigoSUS, "
            + "i.cpf, i.datanascimento, e.descricao, i.escolaridade,i.estadocivil, "
            + "i.genero, i.nome, t.telefone, ti.nometerra "
            + "FROM indigena i "
            + "LEFT JOIN etnia e "
            + " ON i.etnia_idetnia = e.idetnia "
            + "LEFT JOIN telefone t "
            + "ON i.telefone_idtelefone = t.idtelefone "
            + "LEFT JOIN terraindigena ti "
            + "ON i.terraindigena_idterraindigena = ti.idterraindigena ";
    
    //Select modigicado dia 08/08 Bruno Fiorentini/Thiago Marialva
    private final String SQLCOnsultaIndigenaPorId = "SELECT i.codigoassindi,  i.codigoSUS, "
            + "i.cpf, i.datanascimento, e.descricao, i.escolaridade,i.estadocivil, "
            + "i.genero, i.nome, t.telefone, ti.nometerra "
            + "FROM indigena i "
            + "LEFT JOIN etnia e "
            + " ON i.etnia_idetnia = e.idetnia "
            + "LEFT JOIN telefone t "
            + " ON i.telefone_idtelefone = t.idtelefone "
            + "LEFT JOIN terraindigena ti "
            + " ON i.terraindigena_idterraindigena = ti.idterraindigena "
            + "WHERE i.codigoassindi = :idIndigena";

    @Override
    public void salvar(CriarIndigena aIndigena) {
        Indigena i = new Indigena(null, aIndigena.getNome(), aIndigena.getCpf(), null, aIndigena.getGenero(), aIndigena.getDataNascimento(), aIndigena.getConvenio(), aIndigena.getTelefone(), null, aIndigena.getEscolaridade(), aIndigena.getEstadoCivil(), aIndigena.getCodigoSUS(), aIndigena.getOcorrencias());
        i.setTerraIndigena((TerraIndigena) terraService.getObjeto(aIndigena.getTerraIndigena()));
        i.setEtnia((Etnia) etniaService.getObjeto(aIndigena.getEtnia()));
        repository.save(i);
        repository.flush();
        if (aIndigena.getImgSrc() != null && aIndigena.getImgSrc().startsWith("data:image/jpeg;base64")) {
            uploadService.uploadWebcam(aIndigena.getImgSrc(), i.getCodigoAssindi(), "indios");
        }
    }

    @Override
    public void alterar(CriarIndigena aIndigena) {
        Indigena i = new Indigena(aIndigena.getCodigoAssindi(), aIndigena.getNome(), aIndigena.getCpf(), null, aIndigena.getGenero(), aIndigena.getDataNascimento(), aIndigena.getConvenio(), aIndigena.getTelefone(), null, aIndigena.getEscolaridade(), aIndigena.getEstadoCivil(), aIndigena.getCodigoSUS(), aIndigena.getOcorrencias());
        i.setTerraIndigena((TerraIndigena) terraService.getObjeto(aIndigena.getTerraIndigena()));
        i.setEtnia((Etnia) etniaService.getObjeto(aIndigena.getEtnia()));
        if (aIndigena.getImgSrc() != null && aIndigena.getImgSrc().startsWith("data:image/jpeg;base64")) {
            uploadService.uploadWebcam(aIndigena.getImgSrc(), aIndigena.getCodigoAssindi(), "indios");
        }

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
        return query.executeComPaginacao(SQLConsultaIndigena, "i.nome", parametrosConsulta);
    }

    @Override
    public RetornoConsultaPaginada listar() {
        return query.executeComPaginacao(SQLConsultaIndigena, "i.nome", new ParametrosConsulta());
    }

    @Override
    public List<Map<String, Object>> listarSemPaginacao() {
        return query.execute(SQLConsultaIndigena);
    }

}

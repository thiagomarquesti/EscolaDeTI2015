package br.unicesumar.time05.familia;

import br.unicesumar.time05.consultapersonalizada.ConstrutorDeSQL;
import br.unicesumar.time05.usuario.sessaousuario.SessaoUsuario;
import classesbase.ServiceBase;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class FamiliaService extends ServiceBase<Familia, Long, FamiliaRepository> {

    @Autowired
    DataSource dataSource;
    
    @Autowired
    SessaoUsuario sessaoUsuario;

    private final String sqlPadrao
            = "SELECT f.idfamilia, "
            + "       f.telefone, "
            + "       f.nomefamilia, "
            + "       f.idrepresentante, "
            + "       ir.nome, "
            + " (SELECT count(ff.idfamilia) as quantidade "
            + "   FROM familia ff "
            + "  INNER JOIN familia_indigena fi "
            + "     ON fi.idfamilia = ff.idfamilia "
            + "  INNER JOIN indigena i "
            + "     ON i.codigoassindi = fi.codigoassindi "
            + "  WHERE ff.idfamilia = f.idfamilia) "
            + "  FROM familia f "
            + "  LEFT JOIN indigena ir ON f.idrepresentante = ir.codigoassindi";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public FamiliaService() {
        setConstrutorDeSQL(new ConstrutorDeSQL(Familia.class));
        setSqlPadrao(sqlPadrao, "nomefamilia");
        setSqlPadraoPorID(sqlPadrao + " WHERE f.idfamilia = :idfamilia", "nomefamilia", "idfamilia");
    }

    public Map<String, Object> getQuantidadeIntegrantesFamilia(Long aIdfamilia) {
        String sql = " SELECT count(f.idfamilia) as quantidade "
                + "   FROM familia f "
                + "  INNER JOIN familia_indigena fi "
                + "     ON fi.idfamilia = f.idfamilia "
                + "  INNER JOIN indigena i "
                + "     ON i.codigoassindi = fi.codigoassindi "
                + "  WHERE f.idfamilia = :aIdfamilia ";

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aIdfamilia", aIdfamilia);

        List<Map<String, Object>> familia = query.execute(sql, params);

        try {
            return familia.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Nenhum resultado encontrado!");
        }
    }

    public List<Map<String, Object>> getMembros(Long aIdFamilia) {
        String sql
                = "SELECT i.codigoassindi, "
                + "       i.codigoSUS, "
                + "       i.cpf, "
                + "       i.datanascimento, "
                + "       e.descricao, "
                + "       i.escolaridade, "
                + "       i.estadocivil, "
                + "       i.genero, "
                + "       i.nome, "
                + "       t.telefone, "
                + "       ti.nometerra "
                + "FROM familia_indigena fi "
                + "JOIN indigena i ON fi.codigoassindi = i.codigoassindi "
                + "LEFT JOIN etnia e ON i.etnia_idetnia = e.idetnia "
                + "LEFT JOIN telefone t ON i.telefone_idtelefone = t.idtelefone "
                + "LEFT JOIN terraindigena ti ON i.terraindigena_idterraindigena = ti.idterraindigena "
                + "WHERE fi.idfamilia = :aIdFamilia";

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aIdFamilia", aIdFamilia);
        return query.execute(sql, params);
    }

    List<Map<String, Object>> getFamiliasPorIndigena(Long aCodigoAssindi) {
        String sql
                = "SELECT f.idfamilia, "
                + "       f.telefone, "
                + "       f.nomefamilia, "
                + "       f.idrepresentante, "
                + "       ir.nome, "
                + "       (SELECT count(fi.codigoassindi) as quantidade "
                + "          FROM familia ff "
                + "          JOIN familia_indigena fi ON fi.idfamilia = ff.idfamilia "
                + "          JOIN indigena i ON i.codigoassindi = fi.codigoassindi "
                + "         WHERE ff.idfamilia = f.idfamilia) "
                + " FROM familia_indigena fi "
                + " LEFT JOIN familia f ON f.idfamilia = fi.idfamilia "
                + " LEFT JOIN indigena ir ON f.idrepresentante = ir.codigoassindi "
                + "WHERE (ir.codigoassindi = :aCodigoAssindi) or (fi.codigoassindi = :aCodigoAssindi) "
                + "GROUP BY fi.idfamilia, f.idfamilia, ir.codigoassindi ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("aCodigoAssindi", aCodigoAssindi);
        return query.execute(sql, params);
    }

    String gerarRelatorioSimples() {

        try {
            URL reportResource = getClass().getClassLoader().getResource("./relatorios/teste01.jrxml");
            JasperReport report = JasperCompileManager.compileReport(reportResource.getFile());
            JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, Object>(), dataSource.getConnection());
            
            File path = new File("src/main/webapp/rels/" + sessaoUsuario.getUsuario().getIdUsuario());
            path.mkdirs();
            for (File arquivo : path.listFiles()){
                arquivo.delete();
            }
            File pdf = new File(path + "/" + UUID.randomUUID().toString() + ".pdf");
            JasperExportManager.exportReportToPdfFile(print, pdf.getAbsolutePath());
            System.out.println("Imprimindo arquivo " + pdf);
            return "/rels/" + sessaoUsuario.getUsuario().getIdUsuario() + "/" +  pdf.getName();
        } catch (JRException | SQLException ex) {
            Logger.getLogger(FamiliaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

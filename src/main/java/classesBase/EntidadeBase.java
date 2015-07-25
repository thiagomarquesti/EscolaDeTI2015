package classesBase;

import br.unicesumar.time05.ConsultaPersonalizada.CampoConsulta;
import br.unicesumar.time05.ConsultaPersonalizada.CampoParaScriptSQL;
import br.unicesumar.time05.ConsultaPersonalizada.DadosParaConsultaSQL;
import java.io.Serializable;
import java.lang.reflect.Field;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import org.springframework.stereotype.Component;

@Component
public class EntidadeBase implements Serializable {

    private Class classe;
    
    public void setClass(Class classe){
        this.classe = classe;
    }
    
    public DadosParaConsultaSQL getDadosParaScriptSQL(){
        
        DadosParaConsultaSQL dadosParaConsulta = new DadosParaConsultaSQL();
                
        String nomeTabela = "";
        if (this.classe.isAnnotationPresent(Entity.class)) {
            Entity entidade = (Entity)this.classe.getAnnotation(Entity.class);
            nomeTabela = entidade.name();
        }
        
        if (nomeTabela.isEmpty()) {
            nomeTabela = this.classe.getSimpleName();
        }        
        dadosParaConsulta.setNomeTabela(nomeTabela);
        
        
        for (Field campo : this.classe.getDeclaredFields()) {

            if (campo.isAnnotationPresent(CampoConsulta.class)) {

                String nomeCampo = "";
                if (campo.isAnnotationPresent(Column.class)) {
                    nomeCampo = campo.getAnnotation(Column.class).name();
                }

                if (nomeCampo.isEmpty() && campo.isAnnotationPresent(JoinColumn.class)) {
                    nomeCampo = campo.getAnnotation(JoinColumn.class).name();
                }

                if (nomeCampo.isEmpty()) {
                    nomeCampo = campo.getName();
                }

                CampoConsulta campoConsulta = campo.getAnnotation(CampoConsulta.class);
                dadosParaConsulta.addCampo(new CampoParaScriptSQL(nomeCampo, campoConsulta.tipoComparacao()));
            }
        }
        
        return dadosParaConsulta;
    }
    
}

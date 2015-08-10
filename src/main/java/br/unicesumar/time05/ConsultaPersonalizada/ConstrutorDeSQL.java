package br.unicesumar.time05.ConsultaPersonalizada;

import java.lang.reflect.Field;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

public class ConstrutorDeSQL {

    private final Class entidade;
    private DadosParaConsultaSQL dadosParaConsulta;
    private String SQL;

    public ConstrutorDeSQL(Class entidade) {
        this.entidade = entidade;
    }

    public String getSQL(ParametrosConsulta parametros) {
        this.SQL = "";
        this.extrairDadosDaEntidade();
        this.preparaSelect();
        this.preparaFrom();
        this.preparaWhere(parametros);
        return this.SQL;
    }

    public String getSQLComWherePorID() {
        this.SQL = "";
        this.extrairDadosDaEntidade();
        this.preparaSelect();
        this.preparaFrom();
        this.preparaWherePorID();
        return this.SQL;
    }

    private void preparaSelect() {

        this.SQL += OperadoresSQL.SELECT;

        String campos = "";
        for (CampoParaScriptSQL campo : this.dadosParaConsulta.getCampos()) {
            if (campos.isEmpty()) {
                campos = campo.getCampo();
            } else {
                campos += (", " + campo.getCampo());
            }
        }

        this.SQL += campos;
    }

    private void preparaFrom() {
        this.SQL += OperadoresSQL.FROM;
        this.SQL += this.dadosParaConsulta.getNomeTabela();
    }

    private void preparaWherePorID() {
        this.SQL += OperadoresSQL.WHERE + this.dadosParaConsulta.getIdTabela() + OperadoresSQL.IGUAL + OperadoresSQL.PARAMETRO_PARA_IGUAL;
    }

    private void preparaWhere(ParametrosConsulta parametros) {

        if ((parametros != null) && (parametros.getPalavraChave() != null) && (!parametros.getPalavraChave().isEmpty())) {

            this.SQL += OperadoresSQL.WHERE;
            String campos = "";
            for (CampoParaScriptSQL campo : this.dadosParaConsulta.getCampos()) {

                if (campos.isEmpty()) {
                    campos = "(" + montaCondicao(campo);
                } else {
                    campos += (OperadoresSQL.OR + montaCondicao(campo));
                }
            }
            campos += ")";
            this.SQL += campos;
        }
    }

    private String montaCondicao(CampoParaScriptSQL campo) {
        String resultado;
        if (campo.getComparacao() == TipoComparacao.CONTEM) {
            resultado = "(" + campo.getCampo().trim() + "::varchar " + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
        } else {
            resultado = "(" + campo.getCampo().trim() + "::varchar " + OperadoresSQL.IGUAL + OperadoresSQL.PARAMETRO_PARA_IGUAL + ")";
        }
        return resultado;
    }

    private void extrairDadosDaEntidade() {

        dadosParaConsulta = new DadosParaConsultaSQL();

        String nomeTabela = "";
        if (this.entidade.isAnnotationPresent(Entity.class)) {
            Entity entidadeDaClasse = (Entity) this.entidade.getAnnotation(Entity.class);
            nomeTabela = entidadeDaClasse.name();
        }

        if (nomeTabela.isEmpty()) {
            nomeTabela = this.entidade.getSimpleName();
        }
        dadosParaConsulta.setNomeTabela(nomeTabela);

        for (Field campo : this.entidade.getDeclaredFields()) {

            if (campo.isAnnotationPresent(CampoConsulta.class)) {
                CampoConsulta campoConsulta = campo.getAnnotation(CampoConsulta.class);
                dadosParaConsulta.addCampo(new CampoParaScriptSQL(this.getNomeCampo(campo), campoConsulta.tipoComparacao()));
                
                if (campoConsulta.campoOrdenacaoPadrao() && dadosParaConsulta.getCampoOrdenacaoPadrao().isEmpty()){
                    dadosParaConsulta.setCampoOrdenacaoPadrao(this.getNomeCampo(campo));
                }
            }

            if (campo.isAnnotationPresent(Id.class)) {
                dadosParaConsulta.setIdTabela(this.getNomeCampo(campo));
            }
        }
    }

    private String getNomeCampo(Field campo) {

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

        return nomeCampo;
    }
    
    public String getCampoOrdenacaoPadrao(){
        
        if ((this.dadosParaConsulta != null) && (!this.dadosParaConsulta.getCampoOrdenacaoPadrao().isEmpty())){            
            return this.dadosParaConsulta.getCampoOrdenacaoPadrao();
        } else {
            return "";
        }        
    }
}

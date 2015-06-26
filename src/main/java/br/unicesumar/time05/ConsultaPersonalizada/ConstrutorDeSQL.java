package br.unicesumar.time05.ConsultaPersonalizada;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

public class ConstrutorDeSQL<E extends Object> {

    private final Class<E> entidade;
    private final ParametrosConsulta parametros;

    private String SQL;
    private String nomeDaEntidade;
    private String campoId;
    private List<String> camposDaConsulta;

    public ConstrutorDeSQL(Class<E> entidade, ParametrosConsulta parametros) {
        this.entidade = entidade;
        this.parametros = parametros;
    }

    public String getSQL() {
        this.validaEntidade();
        this.extrairNomeDaEntidade();
        this.extrairCamposDaEntidade();

        this.SQL = "";
        this.preparaSelect();
        this.preparaFrom();
        this.preparaWhere();
        this.preparaOrderBy();
        return this.SQL;
    }

    private void validaEntidade() {
        if (!entidade.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("A Entidade passada para construção de Consulta SQL não é válida.");
        }
    }

    private void extrairCamposDaEntidade() {
        this.camposDaConsulta = new ArrayList<>();
        for (Field campo : this.entidade.getDeclaredFields()) {

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

                this.camposDaConsulta.add(nomeCampo);
            }
        }
    }

    private void extrairNomeDaEntidade() {
        String nomeTabela = "";
        if (entidade.isAnnotationPresent(Entity.class)) {
            nomeTabela = this.entidade.getAnnotation(Entity.class).name();
        }
        if (nomeTabela.isEmpty()) {
            nomeTabela = entidade.getSimpleName();
        }
        this.nomeDaEntidade = nomeTabela;
    }

    private void preparaSelect() {

        this.SQL += OperadoresSQL.SELECT;

        String campos = "";
        for (String campo : this.camposDaConsulta) {
            if (campos.isEmpty()) {
                campos = campo;
            } else {
                campos += (", " + campo);
            }
        }

        this.SQL += campos;
    }

    private void preparaFrom() {
        this.SQL += OperadoresSQL.FROM;
        this.SQL += this.nomeDaEntidade;
    }

    private void preparaWhere() {
        this.SQL += OperadoresSQL.WHERE;

        String campos = "";
        for (String campo : this.camposDaConsulta) {
            if (campos.isEmpty()) {
                campos = "((" + campo + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
            } else {
                campos += (OperadoresSQL.OR + "(" + campo + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")");
            }
        }
        campos += ")";

        this.SQL += campos;
    }

    private void preparaOrderBy() {
        if ((this.parametros.getOrdenarPor() != null) && (!this.parametros.getOrdenarPor().isEmpty())) {
            this.SQL += (OperadoresSQL.ORDER_BY + this.parametros.getOrdenarPor());
        }
    }
}

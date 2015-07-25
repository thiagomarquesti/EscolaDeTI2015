package br.unicesumar.time05.ConsultaPersonalizada;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DadosParaConsultaSQL {

    String nomeTabela;
    String idTabela;

    List<CampoParaScriptSQL> campos;

    public DadosParaConsultaSQL() {
        campos = new ArrayList<>();
    }
    
    public String getIdTabela() {
        return idTabela;
    }

    public void setIdTabela(String idTabela) {
        this.idTabela = idTabela;
    }

    public void setNomeTabela(String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

    public void addCampo(CampoParaScriptSQL campo) {
        this.campos.add(campo);
    }

    public String getNomeTabela() {
        return nomeTabela;
    }

    public List<CampoParaScriptSQL> getCampos() {
        return Collections.unmodifiableList(campos);
    }
}

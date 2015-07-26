package br.unicesumar.time05.ConsultaPersonalizada;

public class CampoParaScriptSQL {

    String campo;
    TipoComparacao comparacao;

    public CampoParaScriptSQL(String campo, TipoComparacao comparacao) {
        this.campo = campo;
        this.comparacao = comparacao;
    }

    public String getCampo() {
        return campo;
    }

    public TipoComparacao getComparacao() {
        return comparacao;
    }
}

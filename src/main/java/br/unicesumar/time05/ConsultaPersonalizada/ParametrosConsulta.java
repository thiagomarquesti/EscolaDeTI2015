package br.unicesumar.time05.ConsultaPersonalizada;

import java.io.Serializable;

public class ParametrosConsulta implements Serializable {

    private final int pagina;
    private final String ordenarPor;
    private final String sentidoOrdenacao;
    private final String palavraChave;

    public ParametrosConsulta() {
        this.pagina = 0;
        this.ordenarPor = "";
        this.sentidoOrdenacao = "";
        this.palavraChave = "";
    }
    
    public ParametrosConsulta(int aPagina, String aOrdenarPor, String aSentidoOrdenacao, String aPalavraChave) {
        this.pagina = aPagina;
        this.ordenarPor = aOrdenarPor;
        this.sentidoOrdenacao = aSentidoOrdenacao;
        this.palavraChave = aPalavraChave;
    }

    public ParametrosConsulta(int aPagina, String aOrdenarPor, String aSentidoOrdenacao) {
        this.pagina = aPagina;
        this.ordenarPor = aOrdenarPor;
        this.sentidoOrdenacao = aSentidoOrdenacao;
        this.palavraChave = "";
    }

    public int getPagina() {
        return pagina;
    }

    public String getOrdenarPor() {
        return ordenarPor;
    }

    public String getSentidoOrdenacao() {
        return sentidoOrdenacao;
    }

    public String getPalavraChave() {
        return palavraChave;
    }
}

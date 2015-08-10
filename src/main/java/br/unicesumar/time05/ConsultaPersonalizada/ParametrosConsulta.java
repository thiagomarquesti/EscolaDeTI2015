package br.unicesumar.time05.ConsultaPersonalizada;

import java.io.Serializable;

public class ParametrosConsulta implements Serializable {

    private final int pagina;
    private final int registrosPorPagina;
    private final String ordenarPor;
    private final String sentidoOrdenacao;
    private final String palavraChave;

    public ParametrosConsulta() {
        this.registrosPorPagina = 0;
        this.pagina = 0;
        this.ordenarPor = "";
        this.sentidoOrdenacao = "";
        this.palavraChave = "";
    }
/*
    public ParametrosConsulta(int pagina, int registrosPorPagina) {
        this.pagina = pagina;
        this.registrosPorPagina = registrosPorPagina;
        this.ordenarPor = "";
        this.sentidoOrdenacao = "";
        this.palavraChave = "";
    }*/

    public ParametrosConsulta(int registrosPorPagina, int pagina, String ordenarPor, String sentidoOrdenacao, String palavraChave) {
        this.registrosPorPagina = registrosPorPagina;
        this.pagina = pagina;
        this.ordenarPor = ordenarPor;
        this.sentidoOrdenacao = sentidoOrdenacao;
        this.palavraChave = palavraChave;
    }

    public ParametrosConsulta(int registrosPorPagina, int pagina, String ordenarPor, String sentidoOrdenacao) {
        this.registrosPorPagina = registrosPorPagina;
        this.pagina = pagina;
        this.ordenarPor = ordenarPor;
        this.sentidoOrdenacao = sentidoOrdenacao;
        this.palavraChave = "";
    }

    public int getRegistrosPorPagina() {
        return registrosPorPagina;
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

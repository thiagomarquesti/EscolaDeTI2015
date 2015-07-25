package br.unicesumar.time05.ConsultaPersonalizada;

import java.io.Serializable;

public class ParametrosConsulta implements Serializable {

    private final int pagina;
    private final String ordenarPor;
    private final String palavraChave;

    public ParametrosConsulta() {
        this.pagina = 0;
        this.ordenarPor = "";
        this.palavraChave = "";
    }
    
    public ParametrosConsulta(int pagina, String ordenarPor, String palavraChave) {
        this.pagina = pagina;
        this.ordenarPor = ordenarPor;
        this.palavraChave = palavraChave;
    }

    public ParametrosConsulta(int pagina, String ordenarPor) {
        this.pagina = pagina;
        this.ordenarPor = ordenarPor;
        this.palavraChave = "";
    }

    public int getPagina() {
        return pagina;
    }

    public String getOrdenarPor() {
        return ordenarPor;
    }

    public String getPalavraChave() {
        return palavraChave;
    }
}

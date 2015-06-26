package br.unicesumar.time05.usuario.ConsultaPersonalizada;

import java.io.Serializable;

public class ParametrosConsulta implements Serializable {

    private final int pagina;
    private final String ordenarPor;
    private final String camposDaBusca;
    private final String palavraChave;
    
    public ParametrosConsulta(int pagina, String ordenarPor, String camposDaBusca, String palavraChave) {
        this.pagina = pagina;
        this.ordenarPor = ordenarPor;
        this.camposDaBusca = camposDaBusca;
        this.palavraChave = palavraChave;
    }

    public ParametrosConsulta(int pagina, String ordenarPor) {
        this.pagina = pagina;
        this.ordenarPor = ordenarPor;
        this.camposDaBusca = "";
        this.palavraChave = "";
    }

    public String getExpressaoParaBusca() {
        if (this.palavraChave.isEmpty() || this.camposDaBusca.isEmpty()) {
            return "";
        }

        String expressao = "";
        String campos[] = this.camposDaBusca.split(OperadoresSQL.SEPARADOR_CAMPOS_CONSULTA);
        for (String campo : campos) {
            if (expressao.isEmpty()) {
                expressao += "((" + campo + " " + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
            } else {
                expressao += OperadoresSQL.OR + "(" + campo + OperadoresSQL.ILIKE + OperadoresSQL.PARAMETRO_PARA_LIKE + ")";
            }
        }

        expressao += ")";
        return expressao;
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

    public String getCamposDaBusca() {
        return camposDaBusca;
    }

}

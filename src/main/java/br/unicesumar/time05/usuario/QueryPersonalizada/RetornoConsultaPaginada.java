package br.unicesumar.time05.usuario.QueryPersonalizada;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RetornoConsultaPaginada implements Serializable{

    private Double paginas;
    private int totalDeRegistros;
    private List<Map<String, Object>> listaDeRegistros;

    public RetornoConsultaPaginada() {
    }

    public Double getPaginas() {
        return paginas;
    }

    public void setPaginas(Double paginas) {
        this.paginas = paginas;
    }

    public int getTotalDeRegistros() {
        return totalDeRegistros;
    }

    public void setTotalDeRegistros(int totalDeRegistros) {
        this.totalDeRegistros = totalDeRegistros;
    }

    public List<Map<String, Object>> getListaDeRegistros() {
        return listaDeRegistros;
    }

    public void setListaDeRegistros(List<Map<String, Object>> listaDeRegistros) {
        this.listaDeRegistros = listaDeRegistros;
    }    
}

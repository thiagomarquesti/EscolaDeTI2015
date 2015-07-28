package br.unicesumar.time05.perfildeacesso;

import br.unicesumar.time05.itemacesso.ItemAcesso;
import java.util.List;

public class PerfilBuilder {
    private Long idperfil;
    private String nome;
    private List<ItemAcesso> iditens;

    public PerfilBuilder() {
    }

    public PerfilBuilder(String nome, List<ItemAcesso> iditens) {
        this.nome = nome;
        this.iditens = iditens;
    }

    public PerfilBuilder(Long idperfil, String nome, List<ItemAcesso> iditens) {
        this.idperfil = idperfil;
        this.nome = nome;
        this.iditens = iditens;
    }
    public Long getIdperfil() {
        return idperfil;
    }

    public void setIdperfil(Long idperfil) {
        this.idperfil = idperfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ItemAcesso> getIditens() {
        return iditens;
    }

    public void setIditens(List<ItemAcesso> iditens) {
        this.iditens = iditens;
    }
    
}

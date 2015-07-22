package br.unicesumar.time05.itemacesso;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ItemAcessoUsuarioInMemory implements Serializable {

    private Long id;
    private String nome;
    private String rota;
    private String icone;
    private List<ItemAcessoUsuarioInMemory> itens;

    public ItemAcessoUsuarioInMemory(Long id, String nome, String rota, String icone, List<ItemAcessoUsuarioInMemory> itens) {
        this.id = id;
        this.nome = nome;
        this.rota = rota;
        this.icone = icone;
        this.itens = itens;
    }

    public void setItens(List<ItemAcessoUsuarioInMemory> itens) {
        this.itens = itens;
    }

    public ItemAcessoUsuarioInMemory() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRota() {
        return rota;
    }

    public List<ItemAcessoUsuarioInMemory> getItens() {
        return itens;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemAcessoUsuarioInMemory other = (ItemAcessoUsuarioInMemory) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public String getIcone() {
        return icone;
    }
}

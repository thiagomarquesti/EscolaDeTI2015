package br.unicesumar.time05.itemacesso;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity(name = "itemacesso")
public class ItemAcesso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long iditemacesso;
    private String nome;
    private String rota;
    @ManyToOne
    @JoinColumn(name = "superior_id")
    private ItemAcesso superior;

    public ItemAcesso() {
    }

    public ItemAcesso(Long id, String nome, String rota) {
        this.iditemacesso = id;
        this.nome = nome;
        this.rota = rota;
    }

    public ItemAcesso(Long id, String nome, String rota, ItemAcesso superior) {
        this.iditemacesso = id;
        this.nome = nome;
        this.rota = rota;
        this.superior = superior;
    }

    public Long getIdItemAcesso() {
        return iditemacesso;
    }

    public String getNome() {
        return nome;
    }

    public String getRota() {
        return rota;
    }

    public ItemAcesso getSuperior() {
        return superior;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }

    public void setSuperior(ItemAcesso superior) {
        this.superior = superior;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.iditemacesso);
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
        final ItemAcesso other = (ItemAcesso) obj;
        if (!Objects.equals(this.iditemacesso, other.iditemacesso)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemAcesso{" + "id=" + iditemacesso + ", nome=" + nome + ", rota=" + rota + ", superior=" + superior + '}';
    }
        
}

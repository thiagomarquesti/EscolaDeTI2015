package br.unicesumar.time05.itemacesso;

import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "itemacesso")
public class ItemAcesso {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String nome;
    private String rota;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "superior_id")
    private ItemAcesso superior;

    public ItemAcesso() {
    }

    public ItemAcesso(String nome, String rota, ItemAcesso superior) {
        this.nome = nome;
        this.rota = rota;
        this.superior = superior;
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
        hash = 79 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ItemAcesso{" + "id=" + id + ", nome=" + nome + ", rota=" + rota + ", superior=" + superior + '}';
    }
        
}

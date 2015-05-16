package br.unicesumar.time05.perfildeacesso;

import br.unicesumar.time05.itemacesso.ItemAcesso;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotBlank;

@Entity(name = "perfildeacesso")
public class PerfilDeAcesso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "o nome não pode ser vazio!")
    private String nome;
    @ManyToMany
    @JoinTable(name = "perfildeacesso_itemacesso",
            joinColumns = {@JoinColumn(name = "perfildeacesso_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "itemacesso_id", referencedColumnName = "id")})
    private Set<ItemAcesso> itens;

    public PerfilDeAcesso() {
    }

    public PerfilDeAcesso(String nome, Set<ItemAcesso> itens) {
        this.nome = nome;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Set<ItemAcesso> getItens() {
        return itens;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setItens(Set<ItemAcesso> itens) {
        this.itens = itens;
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
        final PerfilDeAcesso other = (PerfilDeAcesso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PerfilDeAcesso{" + "id=" + id + ", nome=" + nome + ", itens=" + itens + '}';
    }
    
    
}

package br.unicesumar.time05.etnia;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Etnia implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long idetnia;
    String descricao;

    public Etnia() {
    }

    public Etnia(String descricao) {
        this.descricao = descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdetnia() {
        return idetnia;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Etnia{" + "idetnia=" + idetnia + ", descricao=" + descricao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idetnia);
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
        final Etnia other = (Etnia) obj;
        if (!Objects.equals(this.idetnia, other.idetnia)) {
            return false;
        }
        return true;
    }

}
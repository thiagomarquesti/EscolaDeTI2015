package br.unicesumar.time05.funcao;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Funcao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    Long idfuncao;
    String descricao;

    public Funcao() {
    }

    public Funcao(String descricao) {
        this.descricao = descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdfuncao() {
        return idfuncao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Funcao{" + "idfuncao=" + idfuncao + ", descricao=" + descricao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.idfuncao);
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
        final Funcao other = (Funcao) obj;
        if (!Objects.equals(this.idfuncao, other.idfuncao)) {
            return false;
        }
        return true;
    }
}
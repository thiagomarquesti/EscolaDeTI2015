package br.unicesumar.time05.convenio;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Convenio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)

    Long idConvenio;
    String descricao;

    public Convenio() {
    }

    public Convenio(String convenio) {
        this.descricao = descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdConvenio() {
        return idConvenio;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Convenio{" + "idConvenio=" + idConvenio + ", descricao=" + descricao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.idConvenio);
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
        final Convenio other = (Convenio) obj;
        if (!Objects.equals(this.idConvenio, other.idConvenio)) {
            return false;
        }
        return true;
    }
}
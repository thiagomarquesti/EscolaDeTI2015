package br.unicesumar.time05.cnpj;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import org.hibernate.validator.constraints.br.CNPJ;

@Embeddable
public class Cnpj implements Serializable{
    @CNPJ
    private String cnpj;

    public Cnpj() {
    }

    public Cnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.cnpj);
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
        final Cnpj other = (Cnpj) obj;
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        return true;
    }
    
}

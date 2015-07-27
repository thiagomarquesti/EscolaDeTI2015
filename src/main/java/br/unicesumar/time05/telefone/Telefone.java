package br.unicesumar.time05.telefone;

import br.unicesumar.time05.ConsultaPersonalizada.CampoConsulta;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Telefone implements Serializable{
    
    @CampoConsulta
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idtelefone;
    
    @CampoConsulta
    private String telefone;

    public Telefone() {
    }

    public Telefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.telefone);
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
        final Telefone other = (Telefone) obj;
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Telefone{" + "telefone=" + telefone + '}';
    }

}

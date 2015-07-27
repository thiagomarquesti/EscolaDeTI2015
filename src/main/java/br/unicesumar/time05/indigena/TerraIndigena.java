package br.unicesumar.time05.indigena;

import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.uf.UF;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TerraIndigena implements Serializable{
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long idTerraIndigena;
   private String nomeTerra;
   @ManyToOne
   private Cidade cidade;

    public TerraIndigena() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idTerraIndigena);
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
        final TerraIndigena other = (TerraIndigena) obj;
        if (!Objects.equals(this.idTerraIndigena, other.idTerraIndigena)) {
            return false;
        }
        return true;
    }
   
    public TerraIndigena(String nomeTerra, UF estado, Cidade cidade) {
        this.nomeTerra = nomeTerra;
        this.cidade = cidade;
    }

    public Long getIdTerraIndigena() {
        return idTerraIndigena;
    }

    public String getNomeTerra() {
        return nomeTerra;
    }

    public void setNomeTerra(String nomeTerra) {
        this.nomeTerra = nomeTerra;
    }



    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
   
}

package br.unicesumar.time05.terraIndigena;

import br.unicesumar.time05.ConsultaPersonalizada.CampoConsulta;
import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.uf.UF;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "terraindigena")
public class TerraIndigena implements Serializable{
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @CampoConsulta
   private Long idterraindigena;
   @CampoConsulta
   private String nometerra;
   @ManyToOne
   private Cidade cidade;

    public TerraIndigena() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idterraindigena);
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
        if (!Objects.equals(this.idterraindigena, other.idterraindigena)) {
            return false;
        }
        return true;
    }
   
    public TerraIndigena(String nomeTerra, UF estado, Cidade cidade) {
        this.nometerra = nomeTerra;
        this.cidade = cidade;
    }

    public Long getIdTerraIndigena() {
        return idterraindigena;
    }

    public String getNomeTerra() {
        return nometerra;
    }

    public void setNomeTerra(String nomeTerra) {
        this.nometerra = nomeTerra;
    }



    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
   
}

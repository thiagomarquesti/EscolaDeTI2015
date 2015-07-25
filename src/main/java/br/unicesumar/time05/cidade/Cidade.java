package br.unicesumar.time05.cidade;

import br.unicesumar.time05.itemacesso.InicializadorItemAcesso;
import br.unicesumar.time05.uf.UF;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cidade {

    @Id
    private int codigoIBGE;
    private String descricao;
    @ManyToOne
    private UF estado;

    public Cidade() {

    }

    public Cidade(int codigoIBGE, String descricao, UF estado) {
        this.codigoIBGE = codigoIBGE;
        this.descricao = descricao;
        this.estado = estado;
    }

    public int getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(int codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UF getEstado() {
        return estado;
    }

    public void setEstado(UF estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.codigoIBGE;
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
        final Cidade other = (Cidade) obj;
        if (this.codigoIBGE != other.codigoIBGE) {
            return false;
        }
        return true;
    }

}

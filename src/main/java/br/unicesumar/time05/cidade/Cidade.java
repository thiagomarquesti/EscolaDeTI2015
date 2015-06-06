package br.unicesumar.time05.cidade;

public class Cidade {
    private int codigoIBGE;
    private String nome;

    public Cidade() {
    }

    public Cidade(int codigoIBGE, String nome) {
        this.codigoIBGE = codigoIBGE;
        this.nome = nome;
    }

    public int getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(int codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

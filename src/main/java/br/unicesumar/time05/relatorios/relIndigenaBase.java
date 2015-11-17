package br.unicesumar.time05.relatorios;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class relIndigenaBase implements Serializable{

    private Date dataini;
    private Date datafim;
    private int idadefim;
    private int idadeini;
    private List<Long> familias;
    private List<Long> etnias;
    private List<String> escolaridades;
    private List<String> estadoscivis;
    private List<Boolean> generos;
    private List<Long> terrasindigena;
    private List<Long> convenios;

    public relIndigenaBase() {
    }

    public relIndigenaBase(Date dataini, Date datafim, int idadefim, int idadeini, List<Long> familias, List<Long> etnias, List<String> escolaridades, List<String> estadoscivis, List<Boolean> generos, List<Long> terrasindigena, List<Long> convenios) {
        this.dataini = dataini;
        this.datafim = datafim;
        this.idadefim = idadefim;
        this.idadeini = idadeini;
        this.familias = familias;
        this.etnias = etnias;
        this.escolaridades = escolaridades;
        this.estadoscivis = estadoscivis;
        this.generos = generos;
        this.terrasindigena = terrasindigena;
        this.convenios = convenios;
    }

    public Date getDataini() {
        return dataini;
    }

    public void setDataini(Date dataini) {
        this.dataini = dataini;
    }

    public Date getDatafim() {
        return datafim;
    }

    public void setDatafim(Date datafim) {
        this.datafim = datafim;
    }

    public int getIdadefim() {
        return idadefim;
    }

    public void setIdadefim(int idadefim) {
        this.idadefim = idadefim;
    }

    public int getIdadeini() {
        return idadeini;
    }

    public void setIdadeini(int idadeini) {
        this.idadeini = idadeini;
    }

    public List<Long> getFamilias() {
        return familias;
    }

    public void setFamilias(List<Long> familias) {
        this.familias = familias;
    }

    public List<Long> getEtnias() {
        return etnias;
    }

    public void setEtnias(List<Long> etnias) {
        this.etnias = etnias;
    }

    public List<String> getEscolaridades() {
        return escolaridades;
    }

    public void setEscolaridades(List<String> escolaridades) {
        this.escolaridades = escolaridades;
    }

    public List<String> getEstadoscivis() {
        return estadoscivis;
    }

    public void setEstadoscivis(List<String> estadoscivis) {
        this.estadoscivis = estadoscivis;
    }

    public List<Boolean> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Boolean> generos) {
        this.generos = generos;
    }

    public List<Long> getTerrasindigena() {
        return terrasindigena;
    }

    public void setTerrasindigena(List<Long> terrasindigena) {
        this.terrasindigena = terrasindigena;
    }

    public List<Long> getConvenios() {
        return convenios;
    }

    public void setConvenios(List<Long> convenios) {
        this.convenios = convenios;
    }
    
}

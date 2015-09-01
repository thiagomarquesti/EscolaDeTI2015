package br.unicesumar.time05.indigena;

import br.unicesumar.time05.terraIndigena.TerraIndigena;
import br.unicesumar.time05.ConsultaPersonalizada.CampoConsulta;
import br.unicesumar.time05.convenio.Convenio;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.etnia.Etnia;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Indigena implements Serializable{

    @CampoConsulta
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigoassindi;
   
    @CampoConsulta
    private String nome;
    
    @CampoConsulta
    @Embedded
    private CPF cpf;
    
    @CampoConsulta
    @ManyToOne
    private Etnia etnia;
    
    @CampoConsulta
    @Enumerated(EnumType.STRING)
    private Genero genero;
    
    @CampoConsulta
    private Date datanascimento;

    @ManyToMany
    @JoinTable(name = "indigena_convenio",
            joinColumns = {
                @JoinColumn(name = "indigena_id", referencedColumnName = "codigoAssindi")},
            inverseJoinColumns = {
                @JoinColumn(name = "convenio_id", referencedColumnName = "idconvenio")})
    private Set<Convenio> convenio;
   
    @ManyToOne(cascade = CascadeType.ALL)
    private Telefone telefone;

    @ManyToOne(cascade = CascadeType.ALL)
    private TerraIndigena terraindigena;
    
    @Enumerated(EnumType.STRING)
    private Escolaridade escolaridade;
    
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadocivil;
    
    private Long codigosus;

    public Indigena() {
    }
    
    public Indigena(Long codigoAssindi, String nome, CPF cpf, Etnia etnia, Genero genero, Date dataNascimento, Set<Convenio> convenio, Telefone telefone, TerraIndigena terraIndigena, Escolaridade escolaridade, EstadoCivil estadoCivil, Long codigoSUS) {
        this.codigoassindi = codigoAssindi;
        this.nome = nome;
        this.cpf = cpf;
        this.etnia = etnia;
        this.genero = genero;
        this.datanascimento = dataNascimento;
        this.convenio = convenio;
        this.telefone = telefone;
        this.terraindigena = terraIndigena;
        this.escolaridade = escolaridade;
        this.estadocivil = estadoCivil;
        this.codigosus = codigoSUS;
    }

    public Long getCodigoAssindi() {
        return codigoassindi;
    }

    public void setCodigoAssindi(Long codigoAssindi) {
        this.codigoassindi = codigoAssindi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public Etnia getEtnia() {
        return etnia;
    }

    public void setEtnia(Etnia etnia) {
        this.etnia = etnia;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Date getDataNascimento() {
        return datanascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.datanascimento = dataNascimento;
    }

    public Set<Convenio> getConvenio() {
        return convenio;
    }

    public void setConvenio(Set<Convenio> convenio) {
        this.convenio = convenio;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public TerraIndigena getTerraIndigena() {
        return terraindigena;
    }

    public void setTerraIndigena(TerraIndigena terraIndigena) {
        this.terraindigena = terraIndigena;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public EstadoCivil getEstadoCivil() {
        return estadocivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadocivil = estadoCivil;
    }

    public Long getCodigoSUS() {
        return codigosus;
    }

    public void setCodigoSUS(Long codigoSUS) {
        this.codigosus = codigoSUS;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.codigoassindi);
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
        final Indigena other = (Indigena) obj;
        if (!Objects.equals(this.codigoassindi, other.codigoassindi)) {
            return false;
        }
        return true;
    }

}

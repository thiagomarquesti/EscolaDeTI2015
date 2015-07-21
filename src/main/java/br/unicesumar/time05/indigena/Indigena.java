package br.unicesumar.time05.indigena;


import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.convenio.Convenio;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.etnia.Etnia;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.telefone.Telefone;
import br.unicesumar.time05.uf.UF;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Indigena {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigoAssindi;
    private String nome;
    private CPF cpf;
    private Etnia etnia;
    private Genero genero;
    private Date dataNascimento;
    private Set<Convenio> convenio;
    private Telefone telefone;
    private TerraIndigena terraIndigena;
    private Escolaridade escolaridade;
    private EstadoCivil estadoCivil;
    private Long codigoSUS;

    public Indigena() {
    }

    public Indigena(String nome, CPF cpf, Etnia etnia, Genero genero, Date dataNascimento, Set<Convenio> convenio, Telefone telefone, TerraIndigena terraIndigena, Escolaridade escolaridade, EstadoCivil estadoCivil, Long codigoSUS) {
        this.nome = nome;
        this.cpf = cpf;
        this.etnia = etnia;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.convenio = convenio;
        this.telefone = telefone;
        this.terraIndigena = terraIndigena;
        this.escolaridade = escolaridade;
        this.estadoCivil = estadoCivil;
        this.codigoSUS = codigoSUS;
    }

    public Long getCodigoAssindi() {
        return codigoAssindi;
    }

    public void setCodigoAssindi(Long codigoAssindi) {
        this.codigoAssindi = codigoAssindi;
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
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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
        return terraIndigena;
    }

    public void setTerraIndigena(TerraIndigena terraIndigena) {
        this.terraIndigena = terraIndigena;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Long getCodigoSUS() {
        return codigoSUS;
    }

    public void setCodigoSUS(Long codigoSUS) {
        this.codigoSUS = codigoSUS;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.codigoAssindi);
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
        if (!Objects.equals(this.codigoAssindi, other.codigoAssindi)) {
            return false;
        }
        return true;
    }
    
    
    
    
}

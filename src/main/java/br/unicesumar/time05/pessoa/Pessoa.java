package br.unicesumar.time05.pessoa;

import br.unicesumar.time05.ConsultaPersonalizada.CampoConsulta;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {

    @CampoConsulta
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idpessoa;
    
    @CampoConsulta
    @NotBlank(message = "Nome não estar vazio!")
    private String nome;
    
    @CampoConsulta
    @NotNull(message = "Telefone não pode estar vazio!")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "pessoa_telefone",
            joinColumns = {
                @JoinColumn(name = "pessoa_id", referencedColumnName = "idpessoa")},
            inverseJoinColumns = {
                @JoinColumn(name = "telefone_id", referencedColumnName = "idtelefone")})
    private Set<Telefone> telefones;

    @CampoConsulta
    @NotNull(message = "Email não pode estar vazio!")
    @Embedded
    private Email email;

    @CampoConsulta
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "idendereco")
    private Endereco endereco;
    
    @CampoConsulta
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    public Pessoa() {
    }

    public Pessoa(String nome, Email email) {
        this.nome = nome;
        this.email = email;
    }

    public Pessoa(String nome, Set<Telefone> telefones, Email email, Endereco endereco, TipoPessoa tipoPessoa) {
        this.nome = nome;
        this.telefones = telefones;
        this.email = email;
        this.endereco = endereco;
        this.tipoPessoa = tipoPessoa;
    }

    public Pessoa(String nome, Set<Telefone> telefones, Email email) {
        this.nome = nome;
        this.telefones = telefones;
        this.email = email;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setIdpessoa(Long idpessoa) {
        this.idpessoa = idpessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Long getIdpessoa() {
        return idpessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    public void setTelefone(Telefone telefone) {
        this.telefones.add(telefone);
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TipoPessoa getTipo() {
        return tipoPessoa;
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.idpessoa);
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
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.idpessoa, other.idpessoa)) {
            return false;
        }
        return true;
    }

}

package br.unicesumar.time05.pessoa;

import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idpessoa;
    
    @NotBlank
    private String nome;

    @NotBlank
    @ManyToMany
    @JoinTable(name = "pessoa_telefone",
            joinColumns = {
                @JoinColumn(name = "pessoa_id", referencedColumnName = "idpessoa")},
            inverseJoinColumns = {
                @JoinColumn(name = "telefone_id", referencedColumnName = "telefone")})
    private Set<Telefone> telefones;

    @NotBlank
    @OneToOne
    @JoinTable(name = "pessoa_email",
            joinColumns = {
                @JoinColumn(name = "pessoa_id", referencedColumnName = "idpessoa")},
            inverseJoinColumns = {
                @JoinColumn(name = "email_id", referencedColumnName = "email")})
    private Email email;

    @OneToMany
    @JoinTable(name = "pessoa_endereco",
            joinColumns = {
                @JoinColumn(name = "pessoa_id", referencedColumnName = "idpessoa")},
            inverseJoinColumns = {
                @JoinColumn(name = "endereco_id", referencedColumnName = "idendereco")})
    private Set<Endereco> enderecos;
    
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    public Pessoa() {
    }

    public Pessoa(String nome, Set<Telefone> telefones, Email email, Set<Endereco> enderecos, TipoPessoa tipoPessoa) {
        this.nome = nome;
        this.telefones = telefones;
        this.email = email;
        this.enderecos = enderecos;
        this.tipoPessoa = tipoPessoa;
    }

    public Pessoa(String nome, Set<Telefone> telefones, Email email) {
        this.nome = nome;
        this.telefones = telefones;
        this.email = email;
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

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public void setEnderecos(Endereco endereco) {
        this.enderecos.add(endereco);
    }

    public TipoPessoa getTipo() {
        return tipoPessoa;
    }

    public void setTipo(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
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

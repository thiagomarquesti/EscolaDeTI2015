package br.unicesumar.time05.pessoa;

import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.telefone.Telefone;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    private String nome;
    @NotBlank
    private Set<Telefone> telefones;
    @NotBlank
    private Email email;

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

    public Long getId() {
        return id;
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
        hash = 67 * hash + Objects.hashCode(this.id);
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
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}

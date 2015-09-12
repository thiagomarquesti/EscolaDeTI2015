package br.unicesumar.time05.pessoafisica;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.pessoa.Pessoa;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"cpf"}, name = "uk_cpf")})
public class PessoaFisica extends Pessoa implements Serializable{

    @CampoConsulta
    @Embedded
    @Column(unique = true, nullable = false)
    private CPF cpf;
    
    @CampoConsulta
    @Enumerated(EnumType.STRING)
    private Genero genero;
    
    @CampoConsulta
    @Temporal(TemporalType.DATE)
    private Date datanascimento;

    public PessoaFisica() {
    }

    public PessoaFisica(String nome, Email email) {
        super(nome, email);
    }

    public PessoaFisica(CPF cpf, Genero genero, String nome, List<Telefone> telefones, Email email, Endereco endereco, TipoPessoa tipoPessoa, Date datanasc) {
        super(nome, telefones, email, endereco, tipoPessoa);
        this.cpf = cpf;
        this.genero = genero;
        this.datanascimento = datanasc;
    }

    public PessoaFisica(CPF cpf, Genero genero, String nome, List<Telefone> telefones, Email email) {
        super(nome, telefones, email);
        this.cpf = cpf;
        this.genero = genero;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
//        cpf.setCpf(String.format("^(\\d{3}\\.?){3}\\-\\d{2}", cpf.getCpf()));
        this.cpf = cpf;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public void setDatanascimento(Date datanascimento) {
        this.datanascimento = datanascimento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.cpf);
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
        final PessoaFisica other = (PessoaFisica) obj;
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }

}

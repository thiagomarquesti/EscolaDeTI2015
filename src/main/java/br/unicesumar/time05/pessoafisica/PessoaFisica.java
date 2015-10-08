package br.unicesumar.time05.pessoafisica;

import br.unicesumar.time05.consultapersonalizada.CampoConsulta;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.funcao.Funcao;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cpf"}, name = "uk_cpf")})
public class PessoaFisica extends Pessoa implements Serializable {

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

    @CampoConsulta
    @ManyToOne(optional = true)
    private Funcao funcao;

    @Transient
    private String imgSrc;

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
    public PessoaFisica(CriarPessoaFisica u, Endereco endereco, Funcao funcao) {
        this(u.getCpf(), u.getGenero(), u.getNome(), u.getTelefones(), u.getEmail(), endereco, u.getTipoPessoa(), u.getDatanasc());
        this.funcao = funcao;
    }

    public void alterar(CriarPessoaFisica aUsuario) {
        this.setNome(aUsuario.getNome());
        this.setTelefones(aUsuario.getTelefones());
        this.setEmail(aUsuario.getEmail());
        this.setDatanascimento(aUsuario.getDatanasc());
        this.setTipoPessoa(TipoPessoa.USUÁRIO);
        this.setCpf(aUsuario.getCpf());
        this.setGenero(aUsuario.getGenero());
        this.getEndereco().setLogradouro(aUsuario.getLogradouro());
        this.getEndereco().setNumero(aUsuario.getNumero());
        this.getEndereco().setBairro(aUsuario.getBairro());
        this.getEndereco().setComplemento(aUsuario.getComplemento());
        this.getEndereco().setCep(aUsuario.getCep());
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

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
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

package br.unicesumar.time05.usuario;

import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.pessoaFisica.PessoaFisica;
import br.unicesumar.time05.telefone.Telefone;
import br.unicesumar.time05.ConsultaPersonalizada.CampoConsulta;
import br.unicesumar.time05.funcao.Funcao;
import java.io.Serializable;
import java.util.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario extends PessoaFisica implements Serializable {

    @CampoConsulta
    @ManyToOne(optional = true)
    private Funcao funcao;

    private String login = "";

    @Embedded
    private Senha senha;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PerfilDeAcesso> perfis = new HashSet<>();
    
    @Transient
    private String imgSrc;

    public Usuario() {
    }

    public Usuario(String login, Senha senha, String nome, Email email) {
        super(nome, email);
        this.login = login;
        this.senha = senha;
    }

//    public Usuario(String login, Senha senha, Set<PerfilDeAcesso> perfis, CPF cpf, Genero genero, String nome, Set<Telefone> telefones,
//            Email email, Endereco endereco, TipoPessoa tipoPessoa, Funcao funcao, Date datanasc) {
//        super(cpf, genero, nome, telefones, email, endereco, tipoPessoa, datanasc);
//        this.login = login;
//        this.senha = senha;
//        this.perfis = perfis;
//        this.funcao = funcao;
//    }

//    public Usuario(CPF cpf, Genero genero, String nome, Set<Telefone> telefones,
//            Email email, Endereco endereco, TipoPessoa tipoPessoa, Funcao funcao, Date datanasc) {
//        super(cpf, genero, nome, telefones, email, endereco, tipoPessoa, datanasc);
//        this.funcao = funcao;
//    }

    public Usuario(String login, Senha senha, Set<PerfilDeAcesso> perfis, CPF cpf, Genero genero, String nome, List<Telefone> telefones,
            Email email, Endereco endereco, TipoPessoa tipoPessoa, Date datanasc) {
        super(cpf, genero, nome, telefones, email, endereco, tipoPessoa, datanasc);
        this.login = login;
        this.senha = senha;
        this.perfis = perfis;
    }

    public Usuario(CriarUsuario u, Endereco endereco, Funcao funcao) {
        super(u.getCpf(), u.getGenero(), u.getNome(), u.getTelefones(), u.getEmail(), endereco, u.getTipoPessoa(), u.getDatanasc());
        this.funcao = funcao;
        this.login = u.getLogin();
        this.senha = u.getSenha();
    }

    public void alterar(CriarUsuario aUsuario) {
        this.setNome(aUsuario.getNome());
        this.setTelefones(aUsuario.getTelefones());
        this.setEmail(aUsuario.getEmail());
        this.setDatanascimento(aUsuario.getDatanasc());
        this.setTipoPessoa(TipoPessoa.USUÁRIO);
        this.setCpf(aUsuario.getCpf());
        this.setGenero(aUsuario.getGenero());
        this.setStatus(aUsuario.getStatus());
        this.getEndereco().setLogradouro(aUsuario.getLogradouro());
        this.getEndereco().setNumero(aUsuario.getNumero());
        this.getEndereco().setBairro(aUsuario.getBairro());
        this.getEndereco().setComplemento(aUsuario.getComplemento());
        this.getEndereco().setCep(aUsuario.getCep());
    }

    public Long getIdUsuario() {
        return super.getIdpessoa();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void setEmail(Email email) {
        super.setEmail(email);
    }

//    public Senha getSenha() {
//        return this.senha;
//    }

    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPerfil(List<PerfilDeAcesso> perfis) {
        this.perfis = new HashSet<>(perfis);
    }

    public boolean verificaSenha(String senha) {
        return this.senha.equals(senha);
    }
//    public void setPerfil(PerfilDeAcesso perfil){
//        this.perfis.add(perfil);
//    }
    public void removerPerfil(PerfilDeAcesso perfil) {
        this.perfis.remove(perfil);
    }

    public Set<PerfilDeAcesso> getPerfis() {
        return Collections.unmodifiableSet(this.perfis);
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
    public String toString() {
        return "Usuario{ nome=" + super.getNome() + ", login=" + login + ", email=" + super.getEmail() + '}';
    }


}

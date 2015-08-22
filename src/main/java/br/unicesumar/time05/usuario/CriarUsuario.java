package br.unicesumar.time05.usuario;

import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class CriarUsuario implements Serializable{

    private Long idpessoa;
    private String nome;
    private Set<Telefone> telefones;
    private Email email;
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private String cep;
    private Long codigoIBGE;
    private Long codigoUF;
    private Long idfuncao;
    private Date datanasc;
    private TipoPessoa tipo;
    private CPF cpf;
    private Genero genero;
    private String login;
    private Senha senha;
    private Status status = Status.ATIVO;
    private Set<Long> perfis = new HashSet<>();

    public CriarUsuario() {
    }
    
    public CriarUsuario(Long idpessoa, String nome, Set<Telefone> telefones, Email email, String logradouro, String numero, String bairro, String complemento, String cep, Long codigoIBGE, Long codigoUF, Long idfuncao, Date datanasc, TipoPessoa tipo, CPF cpf, Genero genero, String login, Senha senha) {
        this.idpessoa = idpessoa;
        this.nome = nome;
        this.telefones = telefones;
        this.email = email;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cep = cep;
        this.codigoIBGE = codigoIBGE;
//        this.codigoUF = codigoUF;
        this.idfuncao = idfuncao;
        this.datanasc = datanasc;
        this.tipo = tipo;
        this.cpf = cpf;
        this.genero = genero;
        this.login = login;
        this.senha = senha;
    }
    
    public Long getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(Long idpessoa) {
        this.idpessoa = idpessoa;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getCodigoIBGE() {
        return codigoIBGE;
    }

    public void setCodigoIBGE(Long codigoIBGE) {
        this.codigoIBGE = codigoIBGE;
    }

    public Long getIdfuncao() {
        return idfuncao;
    }

    public void setIdfuncao(Long idfuncao) {
        this.idfuncao = idfuncao;
    }

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }

    public TipoPessoa getTipoPessoa() {
        return tipo;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipo = tipoPessoa;
    }

    public CPF getCpf() {
        return cpf;
    }

    public void setCpf(CPF cpf) {
        this.cpf = cpf;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Senha getSenha() {
        return senha;
    }

    public void setSenha(Senha senha) {
        this.senha = senha;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Long> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Long> perfis) {
        this.perfis = perfis;
    }
    
}

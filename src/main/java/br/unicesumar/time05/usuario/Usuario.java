package br.unicesumar.time05.usuario;

import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.pessoaFisica.PessoaFisica;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"login"}, name = "uk_login")})
public class Usuario extends PessoaFisica implements Serializable{
    
    
    @NotBlank(message = "Campo login não pode estar vazio")
    @Column(unique = true, nullable = false)
    private String login;
    
    @Embedded
    private Senha senha;
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PerfilDeAcesso> perfis;
    
    public Usuario() {
    }

    public Usuario(String login, Senha senha, Set<PerfilDeAcesso> perfis, CPF cpf, Genero genero, String nome, Set<Telefone> telefones,
            Email email, Endereco endereco, TipoPessoa tipoPessoa) {
        super(cpf, genero, nome, telefones, email, endereco, tipoPessoa);
        this.login = login;
        this.senha = senha;
        this.perfis = perfis;
    }

    public Long getIdUsuario(){
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

    public String getSenha() {
        return senha.getSenha();
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
    
    public void setPerfil(List<PerfilDeAcesso> perfis){
        this.perfis.addAll(perfis);
    }

    public void removerPerfil(PerfilDeAcesso perfil){
        this.perfis.remove(perfil);
    }
    
    public Set<PerfilDeAcesso> getPerfis(){
        return Collections.unmodifiableSet(this.perfis);
    }
    
    

    @Override
    public String toString() {
        return "Usuario{ nome=" + super.getNome() + ", login=" + login + ", email=" + super.getEmail() + '}';
    }
    
}

package br.unicesumar.time05.usuario;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Usuario  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @NotBlank(message = "Campo nome não pode estar vazio")
    private String nome;
    
    @NotBlank(message = "Campo login não pode estar vazio")
    private String login;
    
    @NotBlank(message = "Campo email não pode estar vazio")
    private String email;
    
    @NotBlank(message = "Campo senha não pode estar vazio")
    private String senha;
    
    private Status status = Status.INATIVO;
    
    public Usuario() {
    }

    public Usuario(String nome, String login, String email, String senha, Status status) {
        this.nome = nome;
        this.login = login;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome + ", login=" + login + ", email=" + email + ", senha=" + senha + '}';
    }
    
}

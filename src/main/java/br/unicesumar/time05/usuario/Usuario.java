package br.unicesumar.time05.usuario;

import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Pattern;
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
    @Column(unique = true, nullable = false)
    private String login;
    
    @NotBlank(message = "Campo email não pode estar vazio")
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b")
    private String email;
    
    @NotBlank(message = "Campo senha não pode estar vazio")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%.]).{6,10})")
    private String senha;
    
    @Enumerated
    private Status status = Status.ATIVO;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PerfilDeAcesso> perfis;
    
    public Usuario() {
    }

    public Usuario(String nome, String login, String email, String senha) {
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

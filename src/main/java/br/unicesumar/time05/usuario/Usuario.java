package br.unicesumar.time05.usuario;

import br.unicesumar.time05.itemacesso.ItemAcesso;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

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
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).{6,10})")
    private String senha;
    
    private Set<PerfilDeAcesso> perfis = new HashSet<>();
    
    private Set<ItemAcesso> ItensAvulsos = new HashSet<>();

    public Set<PerfilDeAcesso> getPerfis() {
        return perfis;
    }

    public void addPerfis(PerfilDeAcesso perfil) {
        this.perfis.add(perfil);
    }

    public Set<ItemAcesso> getItensAvulsos() {
        return ItensAvulsos;
    }

    public void addItensAvulsos(ItemAcesso ItenAvulso) {
        boolean jaExiste = false;
        for (PerfilDeAcesso perfil : perfis) {
            if(perfil.getItens().contains(ItenAvulso)){
                jaExiste = true;
            }
        }
        if(!jaExiste){
            this.ItensAvulsos.add(ItenAvulso);
        }
    }
    
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

package br.unicesumar.time05.pessoaJuridica;

import br.unicesumar.time05.ConsultaPersonalizada.CampoConsulta;
import br.unicesumar.time05.cnpj.Cnpj;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.pessoa.Pessoa;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"cnpj"}, name = "uk_cnpj")})
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaJuridica extends Pessoa implements Serializable{

    @CampoConsulta
    @Embedded
    @Column(unique = true, nullable = false)
    private Cnpj cnpj;

    public PessoaJuridica() {
    }

    public PessoaJuridica(Cnpj cnpj, String nome, Set<Telefone> telefones, Email email, Endereco endereco, TipoPessoa tipoPessoa) {
        super(nome, telefones, email, endereco, tipoPessoa);
        this.cnpj = cnpj;
    }

    public PessoaJuridica(Cnpj cnpj, String nome, Set<Telefone> telefones, Email email) {
        super(nome, telefones, email);
        this.cnpj = cnpj;
    }

    public Cnpj getCnpj() {
        return cnpj;
    }

    public void setCnpj(Cnpj cnpj) {
//        cnpj.setCnpj(String.format("/^d{2}.d{3}.d{3}/d{4}-d{2}$/", cnpj));
        this.cnpj = cnpj;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.cnpj);
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
        final PessoaJuridica other = (PessoaJuridica) obj;
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        return true;
    }

}

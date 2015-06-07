package br.unicesumar.time05.pessoaFisica;

import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.email.Email;
import br.unicesumar.time05.endereco.Endereco;
import br.unicesumar.time05.pessoa.Pessoa;
import br.unicesumar.time05.pessoa.TipoPessoa;
import br.unicesumar.time05.telefone.Telefone;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaFisica extends Pessoa{
    private CPF cpf;
    private Genero genero;

    public PessoaFisica() {
    }

    public PessoaFisica(CPF cpf, Genero genero, String nome, Set<Telefone> telefones, Email email, Set<Endereco> enderecos, TipoPessoa tipoPessoa) {
        super(nome, telefones, email, enderecos, tipoPessoa);
        this.cpf = cpf;
        this.genero = genero;
    }

    public PessoaFisica(CPF cpf, Genero genero, String nome, Set<Telefone> telefones, Email email) {
        super(nome, telefones, email);
        this.cpf = cpf;
        this.genero = genero;
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

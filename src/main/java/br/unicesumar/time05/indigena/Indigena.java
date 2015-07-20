package br.unicesumar.time05.indigena;


import br.unicesumar.time05.cpf.CPF;
import br.unicesumar.time05.etnia.Etnia;
import br.unicesumar.time05.genero.Genero;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Indigena {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long codigoAssindi;
    private String nome;
    private CPF cpf;
    private Etnia etnia;
    private Genero genero;
    private Date dataNascimento;
    
    
}

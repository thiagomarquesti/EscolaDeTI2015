package br.unicesumar.time05.ConsultaPersonalizada;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface CampoConsulta {
    
    public boolean visivel = true;
    public TipoComparacao tipoComparacao = TipoComparacao.CONTEM;
}

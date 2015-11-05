package br.unicesumar.time05.indigena;

import br.unicesumar.time05.genero.Genero;
import br.unicesumar.time05.relatorios.formatoRelatorio;
import java.sql.Date;

class ParametrosRelatorioIndigena {

    public Genero genero;
    public Date dataInicial;
    public Date dataFinal;
    public Long idFamilia;
    public Long idEtnia;
    public Escolaridade escolaridade;
    public EstadoCivil estadoCivil;
    public Long idTerraIndigena;
    public Long idConvenio;
    public int idadeInicial;
    public int idadeFinal;
    public formatoRelatorio formatoRelatorio;

    public ParametrosRelatorioIndigena() {
    }

    public ParametrosRelatorioIndigena(Genero genero, Date dataInicial, Date dataFinal, Long idFamilia, Long idEtnia, Escolaridade escolaridade, EstadoCivil estadoCivil, Long idTerraIndigena, Long idConvenio, int idadeInicial, int idadeFinal, formatoRelatorio formatoRelatorio) {
        this.genero = genero;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.idFamilia = idFamilia;
        this.idEtnia = idEtnia;
        this.escolaridade = escolaridade;
        this.estadoCivil = estadoCivil;
        this.idTerraIndigena = idTerraIndigena;
        this.idConvenio = idConvenio;
        this.idadeInicial = idadeInicial;
        this.idadeFinal = idadeFinal;
        this.formatoRelatorio = formatoRelatorio;
    }
}

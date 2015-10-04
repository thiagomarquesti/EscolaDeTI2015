package br.unicesumar.time05.pessoafisica;

import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa/fisica")
public class FisicaController extends ControllerBase<PessoaFisica, Long, FisicaService>{

    @RequestMapping(value = "/trocarTipo/{aPessoaId}", method = RequestMethod.POST)
    public void alterarTipoPessoa(@PathVariable Long aPessoaId, @RequestBody String tipo) {
        service.trocarTipoPessoa(aPessoaId, tipo);
    }

}

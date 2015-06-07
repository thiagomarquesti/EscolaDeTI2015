package br.unicesumar.time05.pessoaFisica;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa/fisica")
public class FisicaController {

    @Autowired
    private FisicaService service;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarFisica(PessoaFisica aPessoa) {
        service.salvarFisica(aPessoa);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deletarFisica(Long aPessoaId) {
        service.removerFisica(aPessoaId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void alterarFisica(PessoaFisica aPessoa) {
        service.salvarFisica(aPessoa);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getFisica() {
        return service.getFisica();
    }

    @RequestMapping(value = "/{aPessoaId}", method = RequestMethod.GET)
    public Map<String, Object> getFisicaPorId(Long aPessoasId) {
        return service.getFisicaById(aPessoasId);
    }
    
}

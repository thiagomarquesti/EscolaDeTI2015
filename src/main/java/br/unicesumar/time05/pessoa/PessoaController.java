package br.unicesumar.time05.pessoa;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarPessoa(Pessoa aPessoa) {
        service.salvarPessoa(aPessoa);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deletarPessoa(Long aPessoaId) {
        service.removerPessoa(aPessoaId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void alterarPessoa(Pessoa aPessoa) {
        service.salvarPessoa(aPessoa);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getPessoas() {
        return service.getPessoas();
    }

    @RequestMapping(value = "/{aPessoaId}", method = RequestMethod.GET)
    public Map<String, Object> getPessoaPorId(Long aPessoasId) {
        return service.getPessoaById(aPessoasId);
    }
    
}

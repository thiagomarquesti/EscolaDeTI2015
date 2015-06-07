package br.unicesumar.time05.pessoaJuridica;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa/juridica")
public class JuridicaController {

    @Autowired
    private JuridicaService service;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarPessoa(PessoaJuridica aPessoa) {
        service.salvarJuridica(aPessoa);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deletarPessoa(Long aPessoaId) {
        service.removerJuridica(aPessoaId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void alterarPessoa(PessoaJuridica aPessoa) {
        service.salvarJuridica(aPessoa);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getPessoas() {
        return service.getJuridica();
    }

    @RequestMapping(value = "/{aPessoaId}", method = RequestMethod.GET)
    public Map<String, Object> getPessoaPorId(Long aPessoasId) {
        return service.getJuridicaById(aPessoasId);
    }
    
}

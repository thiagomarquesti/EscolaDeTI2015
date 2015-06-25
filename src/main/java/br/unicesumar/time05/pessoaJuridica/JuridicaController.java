package br.unicesumar.time05.pessoaJuridica;

import java.util.List;
import java.util.Map;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa/juridica")
public class JuridicaController {

    @Autowired
    private JuridicaService service;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarPessoa(@RequestBody PessoaJuridica aPessoa) {
        service.salvarJuridica(aPessoa);
    }

    @RequestMapping(value = "/{aPessoaId}", method = RequestMethod.DELETE)
    public void deletarPessoa(@PathVariable Long aPessoaId) {
        service.removerJuridica(aPessoaId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void alterarPessoa(@RequestBody PessoaJuridica aPessoa) {
        service.salvarJuridica(aPessoa);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getPessoas() {
        return service.getJuridica();
    }

    @RequestMapping(value = "/{aPessoaId}", method = RequestMethod.GET)
    public Map<String, Object> getPessoaPorId(@PathVariable Long aPessoaId) {
        return service.getJuridicaById(aPessoaId);
    }
    
    @RequestMapping(value = "/trocarTipo/{aPessoaId}", method = RequestMethod.POST)
    public void alterarTipoPessoa(@PathVariable Long aPessoaId, @RequestBody String tipo){
        service.trocarTipoJuridica(aPessoaId, tipo);
    }
}

package br.unicesumar.time05.pessoaFisica;

import br.unicesumar.time05.pessoa.TipoPessoa;
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
public class FisicaController {

    @Autowired
    private FisicaService service;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarFisica(@RequestBody PessoaFisica aPessoa) {
        service.salvarFisica(aPessoa);
    }

    @RequestMapping(value = "/{aPessoaId}", method = RequestMethod.DELETE)
    public void deletarFisica(@PathVariable Long aPessoaId) {
        service.removerFisica(aPessoaId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void alterarFisica(@RequestBody PessoaFisica aPessoa) {
        service.salvarFisica(aPessoa);
    }

    @RequestMapping(method = RequestMethod.GET)
//    public PessoaFisica getFisica() {
    public List<Map<String, Object>> getFisica() {
        return service.getFisica();
    }

    @RequestMapping(value = "/{aPessoaId}", method = RequestMethod.GET)
    public Map<String, Object> getFisicaPorId(@PathVariable Long aPessoaId) {
        return service.getFisicaById(aPessoaId);
    }

    @RequestMapping(value = "/trocarTipo/{aPessoaId}", method = RequestMethod.POST)
    public void alterarTipoPessoa(@PathVariable Long aPessoaId, @RequestBody String tipo) {
        service.trocarTipoPessoa(aPessoaId, tipo);
    }

}

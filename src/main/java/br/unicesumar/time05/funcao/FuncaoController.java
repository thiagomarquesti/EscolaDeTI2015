package br.unicesumar.time05.funcao;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/funcao")
public class FuncaoController {

    @Autowired
    FuncaoService funcaoService;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarFuncao(@RequestBody Funcao aFuncao) {
        funcaoService.salvarFuncao(aFuncao);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void editarFuncao(@RequestBody Funcao aFuncao) {
        funcaoService.salvarFuncao(aFuncao);
    }

    @RequestMapping(value = "/{aIdFuncao}", method = RequestMethod.DELETE)
    public void editarFuncao(@PathVariable Long aIdFuncao) {
        funcaoService.removerFuncao(aIdFuncao);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getFuncoes() {
        return funcaoService.getFuncoes();
    }

    @RequestMapping(value = "/{aIdFuncao}", method = RequestMethod.GET)
    public List<Map<String, Object>> getFuncaoById(@PathVariable Long aIdFuncao) {
        return funcaoService.getFuncaoById(aIdFuncao);
    }
}
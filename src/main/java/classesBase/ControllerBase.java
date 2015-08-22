package classesBase;

import br.unicesumar.time05.ConsultaPersonalizada.ParametrosConsulta;
import br.unicesumar.time05.ConsultaPersonalizada.RetornoConsultaPaginada;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ControllerBase<Entidade extends Object, ID extends Serializable, Service extends ServiceBase> {

    @Autowired
    public Service service;

    @RequestMapping(method = RequestMethod.POST)
    public void salvar(@RequestBody Entidade aEntidade) {
        try {
            service.salvar(aEntidade);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar entidade, verifique os dados fornecidos!");
        }
    }

    @RequestMapping(value = "/{aEntidadeID}", method = RequestMethod.DELETE)
    public void remover(@PathVariable ID aEntidadeID) {
        service.remover(aEntidadeID);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void editar(@RequestBody Entidade aEntidade) {
        service.alterar(aEntidade);
    }

    @RequestMapping(value = "/{aId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getEntidadePorId(@PathVariable ID aId) {
        return service.findByID(aId);
    }

    @RequestMapping(value = "/obj/{aId}", method = RequestMethod.GET)
    public Object getObjeto(@PathVariable ID aId) {
        return service.getObjeto(aId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getEntidadesListagem() {
        return service.listarSemPaginacao();
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadesPaginada() {
        return service.listar();
    }

    @RequestMapping(value = "/listar/{pagina}/{ordenarPor}/{sentidoOrdenacao}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadesOrdenadas(@PathVariable int aPagina, @PathVariable String aOrdenarPor, @PathVariable String aSentidoOrdenacao) {
        ParametrosConsulta parametros = new ParametrosConsulta(aPagina, aOrdenarPor, aSentidoOrdenacao);
        return service.listar(parametros);
    }

    @RequestMapping(value = "/listar/{pagina}/{ordenarPor}/{sentidoOrdenacao}/{palavraChave}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadesOrdenadasEComBusca(@PathVariable int aPagina, @PathVariable String aOrdenarPor, @PathVariable String aSentidoOrdenacao, @PathVariable String aPalavraChave) {
        ParametrosConsulta parametros = new ParametrosConsulta(aPagina, aOrdenarPor, aSentidoOrdenacao, aPalavraChave);
        return service.listar(parametros);
    }
}

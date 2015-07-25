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


public class ControllerBase <Entidade extends Object, ID extends Serializable, Service extends ServiceBase> {
    
    @Autowired
    public Service service;
    
    @RequestMapping(method = RequestMethod.POST)
    public void salvar(@RequestBody Entidade aEntidade){
        try {
            service.salvar(aEntidade);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar entidade, verifique os dados fornecidos!");
        }
    }
    
    @RequestMapping(value = "/{aEntidadeID}", method = RequestMethod.DELETE)
    public void remover(@PathVariable ID aEntidade){
        service.remover(aEntidade);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void editar(@RequestBody Entidade aEntidade){
        service.salvar(aEntidade);
    }
    
    @RequestMapping(value = "/{aId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getEntidadePorId(@PathVariable ID aId){
        return service.findByID(aId);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getEntidadesListagem() {
        return service.listarSemPaginacao();
    }
    
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadesPaginada() {
        return service.listar();
    }
    
    @RequestMapping(value = "/listar/{pagina}/{ordenarPor}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadeOrdenada(@PathVariable int pagina, @PathVariable String ordenarPor) {
        ParametrosConsulta parametros = new ParametrosConsulta(pagina, ordenarPor);
        return service.listar(parametros);
    }

    @RequestMapping(value = "/listar/{pagina}/{ordenarPor}/{palavraChave}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getEntidadeOrdenadaEComBusca(@PathVariable int pagina, @PathVariable String ordenarPor, @PathVariable String palavraChave) {
        ParametrosConsulta parametros = new ParametrosConsulta(pagina, ordenarPor, palavraChave);
        return service.listar(parametros);
    }
}

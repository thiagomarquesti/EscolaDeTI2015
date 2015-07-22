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
    public ServiceBase service;
    
    @RequestMapping(method = RequestMethod.POST)
    public void salvar(@RequestBody Entidade aEntidade){
        try {
            service.salvar(aEntidade);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar entidade, verifique os dados fornecidos!");
        }
    }
    
    @RequestMapping(value = "/{aEntidadeID}", method = RequestMethod.DELETE)
    public void remover(@PathVariable ID aUsuarioId){
        service.remover(aUsuarioId);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void editar(@RequestBody Entidade aEntidade){
        service.salvar(aEntidade);
    }
    
    @RequestMapping(value = "/{aId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getUsuarioPorId(@PathVariable ID aId){
        return service.findByID(aId);
    }
    
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public RetornoConsultaPaginada getUsuariosOrdenado() {
        return service.listar(null);
    }
    
    @RequestMapping(value = "/listar/{pagina}/{ordenarPor}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getUsuariosOrdenado(@PathVariable int pagina, @PathVariable String ordenarPor) {
        ParametrosConsulta parametros = new ParametrosConsulta(pagina, ordenarPor);
        return service.listar(parametros);
    }

    @RequestMapping(value = "/listar/{pagina}/{ordenarPor}/{palavraChave}", method = RequestMethod.GET)
    public RetornoConsultaPaginada getUsuariosOrdenadoEComBusca(@PathVariable int pagina, @PathVariable String ordenarPor, @PathVariable String palavraChave) {
        ParametrosConsulta parametros = new ParametrosConsulta(pagina, ordenarPor, palavraChave);
        return service.listar(parametros);
    }
}

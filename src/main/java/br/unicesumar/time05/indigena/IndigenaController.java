
package br.unicesumar.time05.indigena;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/indigena")
public class IndigenaController {

    @Autowired
    private IndigenaService service;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarIndigena(@RequestBody Indigena aIndigena) {
        service.salvar(aIndigena);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getIndigenas() {
        return service.getIndigenas();
    }

    @RequestMapping(value = "/{aCodigoAssindi}", method = RequestMethod.GET)
    public List<Map<String, Object>> getIndigenas(@PathVariable Long aCodigoAssindi) {
        return service.getIndigenas(aCodigoAssindi);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void alterar(@RequestBody Indigena indigena) {
        service.salvar(indigena);
    }

    @RequestMapping(value = "/{aCodigoAssindi}", method = RequestMethod.DELETE)
    public void deletar(@PathVariable Long aCodigoAssindi) {
        service.deletar(aCodigoAssindi);
    }
    
    
}

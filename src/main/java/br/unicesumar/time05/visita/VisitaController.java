/*package br.unicesumar.time05.visita;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/visita")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarVisita(@RequestBody Visita aVisita) {
        visitaService.salvarVisita(aVisita);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getVisitas() {
        return visitaService.getVisitas();
    }

    @RequestMapping(value = "/{aVisitaId}", method = RequestMethod.GET)
    public List<Map<String, Object>> getVisita(@PathVariable Long aVisitaId) {
        return visitaService.getVisitaById(aVisitaId);
    }

    @RequestMapping(value = "/{aVisitaId}", method = RequestMethod.DELETE)
    public void removerVisita(@PathVariable Long aVisitaId) {
        visitaService.removerVisita(aVisitaId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void editarVisita(@RequestBody Visita aVisita) {
        visitaService.salvarVisita(aVisita);
    }
}
*/

package br.unicesumar.time05.visita;

import classesBase.ControllerBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/visita")
public class VisitaController extends ControllerBase<Visita, Long, VisitaService>{
}
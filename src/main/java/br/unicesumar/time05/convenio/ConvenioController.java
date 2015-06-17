package br.unicesumar.time05.convenio;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/convenio")
public class ConvenioController {

    @Autowired
    ConvenioService convenioService;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarConvenio(@RequestBody Convenio aConvenio) {
        convenioService.salvarConvenio(aConvenio);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void editarConvenio(@RequestBody Convenio aConvenio) {
        convenioService.salvarConvenio(aConvenio);
    }

    @RequestMapping(value = "/{aIdConvenio}", method = RequestMethod.DELETE)
    public void editarConvenio(@PathVariable Long aIdConvenio) {
        convenioService.removerConvenio(aIdConvenio);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getConvenios() {
        return convenioService.getConvenios();
    }

    @RequestMapping(value = "/{aIdConvenio}", method = RequestMethod.GET)
    public List<Map<String, Object>> getConvenioById(@PathVariable Long aIdConvenio) {
        return convenioService.getConvenioById(aIdConvenio);
    }
}
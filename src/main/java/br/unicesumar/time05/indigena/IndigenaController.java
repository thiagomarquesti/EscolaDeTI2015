/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public void salvarIndigena(@RequestBody Indigena indigena) {
        service.salvar(indigena);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getIndigenas() {
        return service.getIndigenas();
    }

    @RequestMapping(value = "/{codigoAssindi}", method = RequestMethod.GET)
    public List<Map<String, Object>> getIndigenas(@PathVariable Long codigoAssindi) {
        return service.getIndigenas(codigoAssindi);
    }

    @RequestMapping(value = "/{codigoAssindi}", method = RequestMethod.PUT)
    public void alterar(@RequestBody Indigena indigena) {
        service.alterar(indigena);
    }

    @RequestMapping(value = "/{codigoAssindi}", method = RequestMethod.DELETE)
    public void deletar(@PathVariable Long codigoAssindi) {
        service.deletar(codigoAssindi);
    }

}

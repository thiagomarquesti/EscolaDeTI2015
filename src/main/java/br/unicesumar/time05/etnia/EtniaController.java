package br.unicesumar.time05.etnia;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/etnia")
public class EtniaController {

    @Autowired
    EtniaService etniaService;

    @RequestMapping(method = RequestMethod.POST)
    public void salvarEtnia(@RequestBody Etnia aEtnia){
        etniaService.salvarEtnia(aEtnia);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public void editarEtnia(@RequestBody Etnia aEtnia){
        etniaService.salvarEtnia(aEtnia);
    }
    
    @RequestMapping(value = "/{aIdEtnia}", method = RequestMethod.DELETE)
    public void editarEtnia(@PathVariable Long aIdEtnia){
        etniaService.removerEtnia(aIdEtnia);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Map<String, Object>> getEtnias(){
        return etniaService.getEtnias();
    }
    
    @RequestMapping(value = "/{aIdEtnia}", method = RequestMethod.GET)
    public List<Map<String, Object>> getEtniaById(@PathVariable Long aIdEtnia){
        return etniaService.getEtniaById(aIdEtnia);
    }
}

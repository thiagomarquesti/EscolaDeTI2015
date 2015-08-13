package br.unicesumar.time05.indigena;

import classesBase.ControllerBase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/indigena")
public class IndigenaController extends ControllerBase<Indigena, Long, IndigenaService> {

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public void salvar(@RequestBody CriarIndigena aEntidade) {
        try {
            service.salvar((CriarIndigena) aEntidade);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar entidade, verifique os dados fornecidos!");
        }
    }
}

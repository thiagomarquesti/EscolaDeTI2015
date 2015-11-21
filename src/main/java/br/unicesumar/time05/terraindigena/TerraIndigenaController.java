package br.unicesumar.time05.terraindigena;

import classesbase.ControllerBase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/terraIndigena")
public class TerraIndigenaController extends ControllerBase<TerraIndigena, Long, TerraIndigenaService> {

    @Override
    public TerraIndigena getObjeto(@PathVariable Long aId) {
        return (TerraIndigena) service.getObjeto(aId);
    }

    @Override
    public void salvar(TerraIndigena aEntidade) {
        try {
            service.salvar(aEntidade);
        } catch (Exception e) {
            String mensagem;
            if (e.getMessage().contains("constraint [nometerra]")) {
                mensagem = "Já existe um regitro com este nome.";
            }else{
                mensagem = "Algo deu errado. Tente novamente.";
            }
            throw new RuntimeException(mensagem);
        }
    }

}

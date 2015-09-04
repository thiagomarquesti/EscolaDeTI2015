package br.unicesumar.time05.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fotoUsuario")
public class UploadController {

    @RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
    public Map<String, String> getUrlFotoUsuario(@PathVariable Long idUsuario){
        Map<String, String> result = new HashMap<>();
        if(new File("src/main/webapp/fotos/users/"+idUsuario+".jpg").exists())
            result.put("foto", "fotos/users/"+idUsuario+".jpg");
        else
            result.put("foto", "fotos/default.png");
        
        return result;
    }
}

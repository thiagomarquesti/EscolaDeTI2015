package br.unicesumar.time05.pessoajuridica;

import classesbase.ControllerBase;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pessoa/juridica")
public class JuridicaController extends ControllerBase<PessoaJuridica, Long, JuridicaService>{

}

package br.unicesumar.time05.utils;

import br.unicesumar.time05.itemacesso.ItemAcesso;
import br.unicesumar.time05.itemacesso.ItemAcessoRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class Inicializador {
    @Autowired
    private ItemAcessoRepository repo;
    
    @PostConstruct
    public void inicializar(){
        List<ItemAcesso> itensAcesso = new ArrayList();
        
       
       ItemAcesso menu = new ItemAcesso("Menu", "/");
       repo.save(menu);
       menu.setSuperior(menu);
       itensAcesso.add(menu);
       itensAcesso.add(new ItemAcesso("Cadastro de Usuario","#/usuario/list", menu));
       //itensAcesso.add(new ItemAcesso("Cadastro de Usuario","#/usuario/list"));
       itensAcesso.add(new ItemAcesso("Cadastro de Perfil","#/usuario/list", menu));
       //itensAcesso.add(new ItemAcesso("Cadastro de Perfil","#/usuario/list"));
       for(ItemAcesso ia: itensAcesso){
           repo.save(ia);
        }
    }
}

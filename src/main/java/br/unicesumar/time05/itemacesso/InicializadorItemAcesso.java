package br.unicesumar.time05.itemacesso;

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
public class InicializadorItemAcesso {
    @Autowired
    private ItemAcessoRepository repo;
    
    @PostConstruct
    public void inicializar(){
    List<ItemAcesso> itensAcesso = new ArrayList();
        
       
       ItemAcesso menu = new ItemAcesso("Menu", "/");
       repo.save(menu);
       menu.setSuperior(menu);
       itensAcesso.add(menu); 
       
       ItemAcesso menuUsuario = new ItemAcesso("Cadastro de Usuario","", menu);
       itensAcesso.add(menuUsuario);
       itensAcesso.add(new ItemAcesso("Listar", "#/usuario/list", menuUsuario));
       itensAcesso.add(new ItemAcesso("Novo", "#/usuario/novo", menuUsuario));
       
       ItemAcesso menuPerfil = new ItemAcesso("Cadastro de Perfil","", menu); 
       itensAcesso.add(menuPerfil);
       itensAcesso.add(new ItemAcesso("Listar", "#/perfil/list", menuPerfil));
       itensAcesso.add(new ItemAcesso("Novo", "#/perfil/novo", menuPerfil));
       for(ItemAcesso ia: itensAcesso){
           repo.save(ia);
        } 
    }
}
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
    public void inicializar() {
        Long linhas = repo.count();
        List<ItemAcesso> itensAcesso = new ArrayList();
        ItemAcesso menu = new ItemAcesso("Menu", "/");
        if(linhas == 0){
            repo.save(menu);
            menu.setSuperior(menu);
            itensAcesso.add(menu);
        }

        ItemAcesso menuUsuario = new ItemAcesso("Cadastro de Usuario", "", menu);
        itensAcesso.add(menuUsuario);
        itensAcesso.add(new ItemAcesso("Listar", "#/usuario/list", menuUsuario));
        itensAcesso.add(new ItemAcesso("Novo", "#/usuario/novo", menuUsuario));

        ItemAcesso menuPerfil = new ItemAcesso("Cadastro de Perfil", "", menu);
        itensAcesso.add(menuPerfil);
        itensAcesso.add(new ItemAcesso("Listar", "#/perfil/list", menuPerfil));
        itensAcesso.add(new ItemAcesso("Novo", "#/perfil/novo", menuPerfil));
        
        if(linhas.intValue() < itensAcesso.size()){
            List<ItemAcesso> itens = itensAcesso.subList(linhas.intValue(), itensAcesso.size());
            for (ItemAcesso ia : itens) {
                repo.save(ia);
            }
        }
    }
}

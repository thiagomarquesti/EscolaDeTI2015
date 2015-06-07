package br.unicesumar.time05.itemacesso;

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
        List<ItemAcesso> itensAcesso = new ArrayList<>();

        itensAcesso = repo.findAll();

        ItemAcesso menu = new ItemAcesso("Menu", "/");
        if (!itensAcesso.contains(menu)) {
            repo.save(menu);
            itensAcesso.add(menu);
        }

        ItemAcesso menuUsuario = new ItemAcesso("Cadastro de Usuario", "", menu);
        if (!itensAcesso.contains(menuUsuario)) {
            itensAcesso.add(menuUsuario);
        }

        ItemAcesso menuUsuarioListar = new ItemAcesso("Listar", "#/usuario/list", menuUsuario);
        if (!itensAcesso.contains(menuUsuarioListar)) {
            itensAcesso.add(menuUsuarioListar);
        }

        ItemAcesso menuUsuarioNovo = new ItemAcesso("Novo", "#/usuario/novo", menuUsuario);
        if (!itensAcesso.contains(menuUsuarioNovo)) {
            itensAcesso.add(menuUsuarioNovo);
        }

        ItemAcesso menuPerfil = new ItemAcesso("Cadastro de Perfil", "", menu);
        if (!itensAcesso.contains(menuPerfil)) {
            itensAcesso.add(menuPerfil);
        }
        
        ItemAcesso menuPerfilListar = new ItemAcesso("Listar", "#/perfil/list", menuPerfil);
        if (!itensAcesso.contains(menuPerfilListar)){
            itensAcesso.add(menuPerfilListar);
        }
        
        ItemAcesso menuPerfilNovo = new ItemAcesso("Novo", "#/perfil/novo", menuPerfil);
        if (!itensAcesso.contains(menuPerfilNovo)){
            itensAcesso.add(menuPerfilNovo);
        }
        
        for (ItemAcesso ia : itensAcesso) {
            repo.save(ia);
        }
    }
}

package br.unicesumar.time05.itemacesso;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

        ItemAcesso menu = new ItemAcesso("Menu", "/","fa-bars");
        if (!itensAcesso.contains(menu)) {
            repo.save(menu);
            itensAcesso.add(menu);
        }

        ItemAcesso menuUsuario = new ItemAcesso("Gerenciar Usuário", "", "fa-user", menu);
        if (!itensAcesso.contains(menuUsuario)) {
            itensAcesso.add(menuUsuario);
        }

        ItemAcesso menuUsuarioListar = new ItemAcesso("Listar Usuário", "#/usuario/list", "fa-list-alt", menuUsuario);
        if (!itensAcesso.contains(menuUsuarioListar)) {
            itensAcesso.add(menuUsuarioListar);
        }

        ItemAcesso menuUsuarioNovo = new ItemAcesso("Novo Usuário", "#/usuario/novo", "fa-plus", menuUsuario);
        if (!itensAcesso.contains(menuUsuarioNovo)) {
            itensAcesso.add(menuUsuarioNovo);
        }

        ItemAcesso menuPerfil = new ItemAcesso("Gerenciar Perfil", "", "fa-pencil", menu);
        if (!itensAcesso.contains(menuPerfil)) {
            itensAcesso.add(menuPerfil);
        }
        
        ItemAcesso menuPerfilListar = new ItemAcesso("Listar Perfil", "#/perfil/list", "fa-list-alt", menuPerfil);
        if (!itensAcesso.contains(menuPerfilListar)){
            itensAcesso.add(menuPerfilListar);
        }
        
        ItemAcesso menuPerfilNovo = new ItemAcesso("Novo Perfil", "#/perfil/novo", "fa-plus", menuPerfil);
        if (!itensAcesso.contains(menuPerfilNovo)){
            itensAcesso.add(menuPerfilNovo);
        }
        
        for (ItemAcesso ia : itensAcesso) {
            repo.save(ia);
        }
    }
}

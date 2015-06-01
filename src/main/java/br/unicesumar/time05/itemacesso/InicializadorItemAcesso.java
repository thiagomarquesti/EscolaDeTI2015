package br.unicesumar.time05.itemacesso;

import br.unicesumar.time05.itemacesso.ItemAcesso;
import br.unicesumar.time05.itemacesso.ItemAcessoRepository;
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
    
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void inicializar() {
        if (repo.count()== 0) {
            
            repo.deleteAll();

            Long linhas = repo.count();
            List<ItemAcesso> itensAcesso = new ArrayList();
            ItemAcesso menu = new ItemAcesso(1l, "Menu", "/");
            repo.save(menu);
            menu.setSuperior(menu);
            itensAcesso.add(menu);

            ItemAcesso menuUsuario = new ItemAcesso(2l, "Cadastro de Usuario", "", menu);
            itensAcesso.add(menuUsuario);
            itensAcesso.add(new ItemAcesso(3l, "Listar Usuário", "#/usuario/list", menuUsuario));
            itensAcesso.add(new ItemAcesso(4l, "Novo Usuário", "#/usuario/novo", menuUsuario));

            ItemAcesso menuPerfil = new ItemAcesso(5l, "Cadastro de Perfil", "", menu);
            itensAcesso.add(menuPerfil);
            itensAcesso.add(new ItemAcesso(6l, "Listar Perfil", "#/perfil/list", menuPerfil));
            itensAcesso.add(new ItemAcesso(7l, "Novo Perfil", "#/perfil/novo", menuPerfil));
            for (ItemAcesso ia : itensAcesso) {
                repo.save(ia);
            }
        }
    }
}

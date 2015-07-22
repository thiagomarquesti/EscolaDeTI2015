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
        
        //USUARIO
        ItemAcesso menuUsuario = new ItemAcesso("Gerenciar Usuário", "", "fa-user", menu);
        if (!itensAcesso.contains(menuUsuario)) {
            itensAcesso.add(menuUsuario);
        }
        ItemAcesso menuUsuarioListar = new ItemAcesso("Listar Usuário", "#/Usuario/listar", "fa-list-alt", menuUsuario);
        if (!itensAcesso.contains(menuUsuarioListar)) {
            itensAcesso.add(menuUsuarioListar);
        }
        ItemAcesso menuUsuarioNovo = new ItemAcesso("Novo Usuário", "#/Usuario/novo", "fa-plus", menuUsuario);
        if (!itensAcesso.contains(menuUsuarioNovo)) {
            itensAcesso.add(menuUsuarioNovo);
        }
        
        //PERFIL
        ItemAcesso menuPerfil = new ItemAcesso("Gerenciar Perfil", "", "fa-pencil", menu);
        if (!itensAcesso.contains(menuPerfil)) {
            itensAcesso.add(menuPerfil);
        }
        ItemAcesso menuPerfilListar = new ItemAcesso("Listar Perfil", "#/Perfil/listar", "fa-list-alt", menuPerfil);
        if (!itensAcesso.contains(menuPerfilListar)){
            itensAcesso.add(menuPerfilListar);
        }
        ItemAcesso menuPerfilNovo = new ItemAcesso("Novo Perfil", "#/Perfil/novo", "fa-plus", menuPerfil);
        if (!itensAcesso.contains(menuPerfilNovo)){
            itensAcesso.add(menuPerfilNovo);
        }

//        //INDIGENA
//        ItemAcesso menuIndigena = new ItemAcesso("Gerenciar Indígena", "", "fa-user", menu);
//        if (!itensAcesso.contains(menuIndigena)) {
//            itensAcesso.add(menuIndigena);
//        }
//        ItemAcesso menuIndigenaListar = new ItemAcesso("Listar Indígena", "#/Indigena/listar", "fa-list-alt", menuIndigena);
//        if (!itensAcesso.contains(menuIndigenaListar)) {
//            itensAcesso.add(menuIndigenaListar);
//        }
//        ItemAcesso menuIndigenaNovo = new ItemAcesso("Novo Indígena", "#/Indigena/novo", "fa-plus", menuIndigena);
//        if (!itensAcesso.contains(menuIndigenaNovo)) {
//            itensAcesso.add(menuIndigenaNovo);
//        }
        
//        //CONVENIO
//        ItemAcesso menuConvenio = new ItemAcesso("Gerenciar Convênio", "", "fa-pencil", menu);
//        if (!itensAcesso.contains(menuConvenio)) {
//            itensAcesso.add(menuConvenio);
//        }
//        ItemAcesso menuConvenioListar = new ItemAcesso("Listar Convênio", "#/Convenio/listar", "fa-list-alt", menuConvenio);
//        if (!itensAcesso.contains(menuConvenioListar)){
//            itensAcesso.add(menuConvenioListar);
//        }
//        ItemAcesso menuConvenioNovo = new ItemAcesso("Novo Convênio", "#/Convenio/novo", "fa-plus", menuConvenio);
//        if (!itensAcesso.contains(menuConvenioNovo)){
//            itensAcesso.add(menuConvenioNovo);
//        }
//        
//        //ETNIA
//        ItemAcesso menuEtnia = new ItemAcesso("Gerenciar Etnia", "", "fa-pencil", menu);
//        if (!itensAcesso.contains(menuEtnia)) {
//            itensAcesso.add(menuEtnia);
//        }
//        ItemAcesso menuEtniaListar = new ItemAcesso("Listar Etnia", "#/Etnia/listar", "fa-list-alt", menuEtnia);
//        if (!itensAcesso.contains(menuEtniaListar)){
//            itensAcesso.add(menuEtniaListar);
//        }
//        ItemAcesso menuEtniaNovo = new ItemAcesso("Novo Etnia", "#/Etnia/novo", "fa-plus", menuEtnia);
//        if (!itensAcesso.contains(menuEtniaNovo)){
//            itensAcesso.add(menuEtniaNovo);
//        }
//        
//        //FUNCAO
//        ItemAcesso menuFuncao = new ItemAcesso("Gerenciar Funcao", "", "fa-pencil", menu);
//        if (!itensAcesso.contains(menuFuncao)) {
//            itensAcesso.add(menuFuncao);
//        }
//        ItemAcesso menuFuncaoListar = new ItemAcesso("Listar Funcao", "#/Funcao/listar", "fa-list-alt", menuFuncao);
//        if (!itensAcesso.contains(menuFuncaoListar)){
//            itensAcesso.add(menuFuncaoListar);
//        }
//        ItemAcesso menuFuncaoNovo = new ItemAcesso("Novo Funcao", "#/Funcao/novo", "fa-plus", menuFuncao);
//        if (!itensAcesso.contains(menuFuncaoNovo)){
//            itensAcesso.add(menuFuncaoNovo);
//        }
        
        
        for (ItemAcesso ia : itensAcesso) {
            repo.save(ia);
        }
        repo.flush();
    }
}

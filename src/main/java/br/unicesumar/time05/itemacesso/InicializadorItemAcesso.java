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

    private ItemAcesso getItemAcesso(List<ItemAcesso> lista, String nome, String rota) {

        for (ItemAcesso item : lista) {
            if (item.getNome().equals(nome) && item.getRota().equals(rota)) {
                return item;
            }
        }
        return null;
    }

    @PostConstruct
    public void inicializar() {

        List<ItemAcesso> itensAcesso = new ArrayList<>();

        itensAcesso = repo.findAll();

        ItemAcesso menu;
        menu = this.getItemAcesso(itensAcesso, "Menu", "/");
        if (menu == null) {
            menu = new ItemAcesso("Menu", "/", "fa-bars");
            repo.save(menu);
            itensAcesso.add(menu);
        }

        //ETNIA
        ItemAcesso menuEtnia;
        menuEtnia = this.getItemAcesso(itensAcesso, "Gerenciar Etnia", "");
        if (menuEtnia == null) {
            menuEtnia = new ItemAcesso("Gerenciar Etnia", "", "fa-flag", menu);
            itensAcesso.add(menuEtnia);
        }

        ItemAcesso menuEtniaListar;
        menuEtniaListar = this.getItemAcesso(itensAcesso, "Listar Etnia", "#/Etnia/listar");
        if (menuEtniaListar == null) {
            menuEtniaListar = new ItemAcesso("Listar Etnia", "#/Etnia/listar", "fa-list-alt", menuEtnia);
            itensAcesso.add(menuEtniaListar);
        }

        ItemAcesso menuEtniaNovo;
        menuEtniaNovo = this.getItemAcesso(itensAcesso, "Novo Etnia", "#/Etnia/novo");
        if (menuEtniaNovo == null) {
            menuEtniaNovo = new ItemAcesso("Novo Etnia", "#/Etnia/novo", "fa-plus", menuEtnia);
            itensAcesso.add(menuEtniaNovo);
        }
        
        //INDIGENA
        ItemAcesso menuIndigena;
        menuIndigena = this.getItemAcesso(itensAcesso, "Gerenciar Indígena", "");
        if (menuIndigena == null) {
            menuIndigena = new ItemAcesso("Gerenciar Indígena", "", "fa-users", menu);
            itensAcesso.add(menuIndigena);
        }
        
        ItemAcesso menuIndigenaListar;
        menuIndigenaListar = this.getItemAcesso(itensAcesso, "Listar Indígena", "#/Perfil/listar");
        if (menuIndigenaListar == null) {
            menuIndigenaListar = new ItemAcesso("Listar Indígena", "#/Indigena/listar", "fa-list-alt", menuIndigena);
            itensAcesso.add(menuIndigenaListar);
        }
        
        ItemAcesso menuIndigenaNovo;
        menuIndigenaNovo = this.getItemAcesso(itensAcesso, "Novo Indígena", "#/Perfil/novo");
        if (menuIndigenaNovo == null) {
            menuIndigenaNovo = new ItemAcesso("Novo Indígena", "#/Indigena/novo", "fa-plus", menuIndigena);
            itensAcesso.add(menuIndigenaNovo);
        }
        
        //CONVENIO
        ItemAcesso menuConvenio;
        menuConvenio = this.getItemAcesso(itensAcesso, "Gerenciar Convênio", "");
        if (menuConvenio == null) {
            menuConvenio = new ItemAcesso("Gerenciar Convênio", "", "fa-credit-card", menu);
            itensAcesso.add(menuConvenio);
        }

        ItemAcesso menuConvenioListar;
        menuConvenioListar = this.getItemAcesso(itensAcesso, "Listar Convênio", "#/Perfil/listar");
        if (menuConvenioListar == null) {
            menuConvenioListar = new ItemAcesso("Listar Convênio", "#/Convenio/listar", "fa-list-alt", menuConvenio);
            itensAcesso.add(menuConvenioListar);
        }

        ItemAcesso menuConvenioNovo;
        menuConvenioNovo = this.getItemAcesso(itensAcesso, "Novo Convênio", "#/Perfil/novo");
        if (menuConvenioNovo == null) {
            menuConvenioNovo = new ItemAcesso("Novo Convênio", "#/Convenio/novo", "fa-plus", menuConvenio);
            itensAcesso.add(menuConvenioNovo);
        }
        
        //USUARIO
        ItemAcesso menuUsuario;
        menuUsuario = this.getItemAcesso(itensAcesso, "Gerenciar Usuário", "");
        if (menuUsuario == null) {
            menuUsuario = new ItemAcesso("Gerenciar Usuário", "", "fa-user", menu);
            itensAcesso.add(menuUsuario);
        }
        
        ItemAcesso menuUsuarioListar;
        menuUsuarioListar = this.getItemAcesso(itensAcesso, "Listar Usuário", "#/Usuario/listar");
        if (menuUsuarioListar == null) {
            menuUsuarioListar = new ItemAcesso("Listar Usuário", "#/Usuario/listar", "fa-list-alt", menuUsuario);
            itensAcesso.add(menuUsuarioListar);
        }
        
        ItemAcesso menuUsuarioNovo;
        menuUsuarioNovo = this.getItemAcesso(itensAcesso, "Novo Usuário", "#/Usuario/novo");
        if (menuUsuarioNovo == null) {
            menuUsuarioNovo = new ItemAcesso("Novo Usuário", "#/Usuario/novo", "fa-plus", menuUsuario);
            itensAcesso.add(menuUsuarioNovo);
        }
        
        //PERFIL
        ItemAcesso menuPerfil;
        menuPerfil = this.getItemAcesso(itensAcesso, "Gerenciar Perfil", "");
        if (menuPerfil == null) {
            menuPerfil = new ItemAcesso("Gerenciar Perfil", "", "fa-pencil", menu);
            itensAcesso.add(menuPerfil);
        }
        
        ItemAcesso menuPerfilListar;
        menuPerfilListar = this.getItemAcesso(itensAcesso, "Listar Perfil", "#/Perfil/listar");
        if (menuPerfilListar == null) {
            menuPerfilListar = new ItemAcesso("Listar Usuário", "#/Perfil/listar", "fa-list-alt", menuPerfil);
            itensAcesso.add(menuPerfilListar);
        }
        
        ItemAcesso menuPerfilNovo;
        menuPerfilNovo = this.getItemAcesso(itensAcesso, "Novo Perfil", "#/Perfil/novo");
        if (menuPerfilNovo == null) {
            menuPerfilNovo = new ItemAcesso("Novo Usuário", "#/Perfil/novo", "fa-plus", menuPerfil);
            itensAcesso.add(menuPerfilNovo);
        }
        
        //FUNCAO
        ItemAcesso menuFuncao;
        menuFuncao = this.getItemAcesso(itensAcesso, "Gerenciar Função", "");
        if (menuFuncao == null) {
            menuFuncao = new ItemAcesso("Gerenciar Função", "", "fa-puzzle-piece", menu);
            itensAcesso.add(menuFuncao);
        }

        ItemAcesso menuFuncaoListar;
        menuFuncaoListar = this.getItemAcesso(itensAcesso, "Listar Função", "#/Funcao/listar");
        if (menuFuncaoListar == null) {
            menuFuncaoListar = new ItemAcesso("Listar Função", "#/Funcao/listar", "fa-list-alt", menuFuncao);
            itensAcesso.add(menuFuncaoListar);
        }

        ItemAcesso menuFuncaoNovo;
        menuFuncaoNovo = this.getItemAcesso(itensAcesso, "Novo Função", "#/Funcao/novo");
        if (menuFuncaoNovo == null) {
            menuFuncaoNovo = new ItemAcesso("Novo Função", "#/Funcao/novo", "fa-plus", menuFuncao);
            itensAcesso.add(menuFuncaoNovo);
        }
        
        for (ItemAcesso ia : itensAcesso) {
            repo.save(ia);
        }
//        repo.flush();
    }
}

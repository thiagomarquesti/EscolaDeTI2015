package br.unicesumar.time05.itemacesso;

import br.unicesumar.time05.consultapersonalizada.QueryPersonalizada;
import br.unicesumar.time05.cidade.CidadeRepository;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcessoRepository;
import br.unicesumar.time05.uf.UFRepository;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
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

    @Autowired
    private CidadeRepository cidRepo;

    @Autowired
    private UFRepository UfRepo;
    @Autowired
    private PerfilDeAcessoRepository perfilRepo;

    @Autowired
    protected QueryPersonalizada query;

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
        carregarUF();
        carregarEstados();
        carregarConvenios();
        carregarTerraIndigena();

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
            menuEtniaListar = new ItemAcesso("Listar Etnia", "#/Etnia/listar", "fa-list", menuEtnia);
            itensAcesso.add(menuEtniaListar);
        }

        ItemAcesso menuEtniaNovo;
        menuEtniaNovo = this.getItemAcesso(itensAcesso, "Novo Etnia", "#/Etnia/novo");
        if (menuEtniaNovo == null) {
            menuEtniaNovo = new ItemAcesso("Cadastrar Etnia", "#/Etnia/nova", "fa-plus", menuEtnia);
            itensAcesso.add(menuEtniaNovo);
        }

        //TERRA INDIGENA
        ItemAcesso menuTerraIndigena;
        menuTerraIndigena = this.getItemAcesso(itensAcesso, "Gerenciar Terra Indígena", "");
        if (menuTerraIndigena == null) {
            menuTerraIndigena = new ItemAcesso("Gerenciar Terra Indígena", "", "fa-leaf", menu);
            itensAcesso.add(menuTerraIndigena);
        }

        ItemAcesso menuTerraIndigenaListar;
        menuTerraIndigenaListar = this.getItemAcesso(itensAcesso, "Listar Terra Indígena", "#/TerraIndigena/listar");
        if (menuTerraIndigenaListar == null) {
            menuTerraIndigenaListar = new ItemAcesso("Listar Terra Indígena", "#/TerraIndigena/listar", "fa-list", menuTerraIndigena);
            itensAcesso.add(menuTerraIndigenaListar);
        }

        ItemAcesso menuTerraIndigenaNovo;
        menuTerraIndigenaNovo = this.getItemAcesso(itensAcesso, "Novo Terra Indígena", "#/TerraIndigena/nova");
        if (menuTerraIndigenaNovo == null) {
            menuTerraIndigenaNovo = new ItemAcesso("Cadastrar Terra Indígena", "#/TerraIndigena/nova", "fa-plus", menuTerraIndigena);
            itensAcesso.add(menuTerraIndigenaNovo);
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
            menuIndigenaListar = new ItemAcesso("Listar Indígena", "#/Indigena/listar", "fa-list", menuIndigena);
            itensAcesso.add(menuIndigenaListar);
        }

        ItemAcesso menuIndigenaNovo;
        menuIndigenaNovo = this.getItemAcesso(itensAcesso, "Novo Indígena", "#/Perfil/novo");
        if (menuIndigenaNovo == null) {
            menuIndigenaNovo = new ItemAcesso("Cadastrar Indígena", "#/Indigena/novo", "fa-plus", menuIndigena);
            itensAcesso.add(menuIndigenaNovo);
        }
        
        //FAMILIA
        ItemAcesso menuFamilia;
        menuFamilia = this.getItemAcesso(itensAcesso, "Gerenciar Família", "");
        if (menuFamilia == null) {
            menuFamilia = new ItemAcesso("Gerenciar Família", "", "fa-sitemap", menu);
            itensAcesso.add(menuFamilia);
        }

        ItemAcesso menuFamiliaListar;
        menuFamiliaListar = this.getItemAcesso(itensAcesso, "Listar Família", "#/Familia/listar");
        if (menuFamiliaListar == null) {
            menuFamiliaListar = new ItemAcesso("Listar Família", "#/Familia/listar", "fa-list", menuFamilia);
            itensAcesso.add(menuFamiliaListar);
        }

        ItemAcesso menuFamiliaNova;
        menuFamiliaNova = this.getItemAcesso(itensAcesso, "Nova Família", "#/Familia/nova");
        if (menuFamiliaNova == null) {
            menuFamiliaNova = new ItemAcesso("Cadastrar Família", "#/Familia/nova", "fa-plus", menuFamilia);
            itensAcesso.add(menuFamiliaNova);
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
            menuConvenioListar = new ItemAcesso("Listar Convênio", "#/Convenio/listar", "fa-list", menuConvenio);
            itensAcesso.add(menuConvenioListar);
        }

        ItemAcesso menuConvenioNovo;
        menuConvenioNovo = this.getItemAcesso(itensAcesso, "Novo Convênio", "#/Perfil/novo");
        if (menuConvenioNovo == null) {
            menuConvenioNovo = new ItemAcesso("Cadastrar Convênio", "#/Convenio/novo", "fa-plus", menuConvenio);
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
            menuUsuarioListar = new ItemAcesso("Listar Usuário", "#/Usuario/listar", "fa-list", menuUsuario);
            itensAcesso.add(menuUsuarioListar);
        }

        ItemAcesso menuUsuarioNovo;
        menuUsuarioNovo = this.getItemAcesso(itensAcesso, "Novo Usuário", "#/Usuario/novo");
        if (menuUsuarioNovo == null) {
            menuUsuarioNovo = new ItemAcesso("Cadastrar Usuário", "#/Usuario/novo", "fa-plus", menuUsuario);
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
            menuPerfilListar = new ItemAcesso("Listar Perfil", "#/Perfil/listar", "fa-list", menuPerfil);
            itensAcesso.add(menuPerfilListar);
        }

        ItemAcesso menuPerfilNovo;
        menuPerfilNovo = this.getItemAcesso(itensAcesso, "Novo Perfil", "#/Perfil/novo");
        if (menuPerfilNovo == null) {
            menuPerfilNovo = new ItemAcesso("Cadastrar Perfil", "#/Perfil/novo", "fa-plus", menuPerfil);
            itensAcesso.add(menuPerfilNovo);
        }
        
        //EVENTOS
        ItemAcesso menuEventos;
        menuEventos = this.getItemAcesso(itensAcesso, "Gerenciar Eventos", "");
        if (menuEventos == null) {
            menuEventos = new ItemAcesso("Gerenciar Eventos", "", "fa-calendar", menu);
            itensAcesso.add(menuEventos);
        }

        ItemAcesso menuEventosListar;
        menuEventosListar = this.getItemAcesso(itensAcesso, "Listar Eventos", "#/Eventos/listar");
        if (menuEventosListar == null) {
            menuEventosListar = new ItemAcesso("Listar Eventos", "#/Eventos/listar", "fa-list-alt", menuEventos);
            itensAcesso.add(menuEventosListar);
        }

        ItemAcesso menuEventosNovo;
        menuEventosNovo = this.getItemAcesso(itensAcesso, "Novo Eventos", "#/Evento/novo");
        if (menuEventosNovo == null) {
            menuEventosNovo = new ItemAcesso("Cadastrar Eventos", "#/Eventos/novo", "fa-plus", menuEventos);
            itensAcesso.add(menuEventosNovo);
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
            menuFuncaoListar = new ItemAcesso("Listar Função", "#/Funcao/listar", "fa-list", menuFuncao);
            itensAcesso.add(menuFuncaoListar);
        }

        ItemAcesso menuFuncaoNovo;
        menuFuncaoNovo = this.getItemAcesso(itensAcesso, "Novo Função", "#/Funcao/novo");
        if (menuFuncaoNovo == null) {
            menuFuncaoNovo = new ItemAcesso("Cadastrar Função", "#/Funcao/nova", "fa-plus", menuFuncao);
            itensAcesso.add(menuFuncaoNovo);
        }

        ItemAcesso menuEstadia;
        menuEstadia = this.getItemAcesso(itensAcesso, "Gerenciar Estadia", "");
        if (menuEstadia == null) {
            menuEstadia = new ItemAcesso("Gerenciar Estadia", "", "fa-sitemap", menu);
            itensAcesso.add(menuEstadia);
        }

        ItemAcesso menuEstadiaListar;
        menuEstadiaListar = this.getItemAcesso(itensAcesso, "Listar Estadia", "#/Estadia/listar");
        if (menuEstadiaListar == null) {
            menuEstadiaListar = new ItemAcesso("Listar Estadia", "#/Estadia/listar", "fa-list-alt", menuEstadia);
            itensAcesso.add(menuEstadiaListar);
        }

        ItemAcesso menuEstadiaNovo;
        menuEstadiaNovo = this.getItemAcesso(itensAcesso, "Nova Estadia", "#/Estadia/novo");
        if (menuEstadiaNovo == null) {
            menuEstadiaNovo = new ItemAcesso("Cadastrar Estadia", "#/Estadia/nova", "fa-plus", menuEstadia);
            itensAcesso.add(menuEstadiaNovo);
        }

        
        
        
        //RELATORIOS
        ItemAcesso menuRelatorios;
        menuRelatorios = this.getItemAcesso(itensAcesso, "Relatórios", "");
        if (menuRelatorios == null) {
            menuRelatorios = new ItemAcesso("Relatórios", "", "fa-list ", menu);
            itensAcesso.add(menuRelatorios);
        }

        ItemAcesso menuRelatoriosFamilias;
        menuRelatoriosFamilias = this.getItemAcesso(itensAcesso, "Relatório de Famílias", "#/Relatorios/familias");
        if (menuRelatoriosFamilias == null) {
            menuRelatoriosFamilias = new ItemAcesso("Relatório de Famílias", "#/Relatorios/familias", "fa-list", menuRelatorios);
            itensAcesso.add(menuRelatoriosFamilias);
        }
        
        //----------------------------------------------------
        //----------------------------------------------------
        //----------------------------------------------------

        for (ItemAcesso ia : itensAcesso) {
            repo.save(ia);
        }

        if (perfilRepo.count() == 0) {
            PerfilDeAcesso perfilAdm = new PerfilDeAcesso("Administrador", new HashSet<>(repo.findAll()));
            perfilRepo.save(perfilAdm);
        }

    }

    public void carregarUF() {
        final String FILE_NAME_UF = "src/main/java/SCRIPTS/uf.txt";
        carregarScript(new File(FILE_NAME_UF));
    }

    public void carregarEstados() {
        final String FILE_NAME_CIDADES = "src/main/java/SCRIPTS/cidades.txt";
        carregarScript(new File(FILE_NAME_CIDADES));
    }

    public void carregarConvenios() {
        final String FILE_NAME_CONVENIO = "src/main/java/SCRIPTS/convenio.txt";
        carregarScript(new File(FILE_NAME_CONVENIO));
    }

    public void carregarTerraIndigena() {
        final String FILE_NAME_TERRA = "src/main/java/SCRIPTS/terraindigena.txt";
        carregarScript(new File(FILE_NAME_TERRA));
    }

    public void carregarScript(File arquivo) {
        try {
            FileInputStream fileInputStream = new FileInputStream(arquivo);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                try {
                    query.execute(linha);
                } catch (Exception e) {
                }
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (Exception ex) {
            throw new RuntimeException("Falha ao carregar script");
        }
    }
}

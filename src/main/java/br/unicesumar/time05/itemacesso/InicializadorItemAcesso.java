package br.unicesumar.time05.itemacesso;


import br.unicesumar.time05.cidade.Cidade;
import br.unicesumar.time05.cidade.CidadeRepository;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcesso;
import br.unicesumar.time05.perfildeacesso.PerfilDeAcessoRepository;
import br.unicesumar.time05.uf.UF;
import br.unicesumar.time05.uf.UFRepository;
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
            menuEtniaNovo = new ItemAcesso("Cadastrar Etnia", "#/Etnia/nova", "fa-plus", menuEtnia);
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
            menuIndigenaNovo = new ItemAcesso("Cadastrar Indígena", "#/Indigena/novo", "fa-plus", menuIndigena);
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
            menuUsuarioListar = new ItemAcesso("Listar Usuário", "#/Usuario/listar", "fa-list-alt", menuUsuario);
            itensAcesso.add(menuUsuarioListar);
        }

        ItemAcesso menuUsuarioNovo;
        menuUsuarioNovo = this.getItemAcesso(itensAcesso, "Novo Usuário", "#/Usuario/novo");
        if (menuUsuarioNovo == null) {
            menuUsuarioNovo = new ItemAcesso("Cadastrar Usuário", "#/Usuario/novo", "fa-plus", menuUsuario);
            itensAcesso.add(menuUsuarioNovo);
        }

//        //PERFIL
        ItemAcesso menuPerfil;
        menuPerfil = this.getItemAcesso(itensAcesso, "Gerenciar Perfil", "");
        if (menuPerfil == null) {
            menuPerfil = new ItemAcesso("Gerenciar Perfil", "", "fa-pencil", menu);
            itensAcesso.add(menuPerfil);
            InsereParana();
        }

        ItemAcesso menuPerfilListar;
        menuPerfilListar = this.getItemAcesso(itensAcesso, "Listar Perfil", "#/Perfil/listar");
        if (menuPerfilListar == null) {
            menuPerfilListar = new ItemAcesso("Listar Perfil", "#/Perfil/listar", "fa-list-alt", menuPerfil);
            itensAcesso.add(menuPerfilListar);
        }

        ItemAcesso menuPerfilNovo;
        menuPerfilNovo = this.getItemAcesso(itensAcesso, "Novo Perfil", "#/Perfil/novo");
        if (menuPerfilNovo == null) {
            menuPerfilNovo = new ItemAcesso("Cadastrar Perfil", "#/Perfil/novo", "fa-plus", menuPerfil);
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
            menuFuncaoNovo = new ItemAcesso("Cadastrar Função", "#/Funcao/nova", "fa-plus", menuFuncao);
            itensAcesso.add(menuFuncaoNovo);
        }

        for (ItemAcesso ia : itensAcesso) {
            repo.save(ia);
        }

        if (perfilRepo.count() == 0) {
            PerfilDeAcesso perfilAdm = new PerfilDeAcesso("Administrador", new HashSet<>(repo.findAll()));
            perfilRepo.save(perfilAdm);
        }
        
    }

    private void InsereParana() {
        UF uf = new UF(41l, "PARANÁ", "PR");
        UfRepo.save(uf);
        cidRepo.save(new Cidade(4100608, "ALTO PARANÁ", uf));
        cidRepo.save(new Cidade(4100905, "AMAPORÃ", uf));
        cidRepo.save(new Cidade(4106704, "CRUZEIRO DO SUL", uf));
        cidRepo.save(new Cidade(4107108, "DIAMANTE DO NORTE", uf));
        cidRepo.save(new Cidade(4108908, "GUAIRAÇÁ", uf));
        cidRepo.save(new Cidade(4110300, "INAJÁ", uf));
        cidRepo.save(new Cidade(4111308, "ITAÚNA DO SUL", uf));
        cidRepo.save(new Cidade(4112603, "JARDIM OLINDA", uf));
        cidRepo.save(new Cidade(4113502, "LOANDA", uf));
        cidRepo.save(new Cidade(4115002, "MARILENA", uf));
        cidRepo.save(new Cidade(4115903, "MIRADOR", uf));
        cidRepo.save(new Cidade(4116505, "NOVA ALIANÇA DO IVAÍ", uf));
        cidRepo.save(new Cidade(4117107, "NOVA LONDRINA", uf));
        cidRepo.save(new Cidade(4118006, "PARAÍSO DO NORTE", uf));
        cidRepo.save(new Cidade(4118105, "PARANACITY", uf));
        cidRepo.save(new Cidade(4118303, "PARANAPOEMA", uf));
        cidRepo.save(new Cidade(4118402, "PARANAVAÍ", uf));
        cidRepo.save(new Cidade(4119707, "PLANALTINA DO PARANÁ", uf));
        cidRepo.save(new Cidade(4120200, "PORTO RICO", uf));
        cidRepo.save(new Cidade(4121000, "QUERÊNCIA DO NORTE", uf));
        cidRepo.save(new Cidade(4123303, "SANTA CRUZ DE MONTE CASTELO", uf));
        cidRepo.save(new Cidade(4123709, "SANTA ISABEL DO IVAÍ", uf));
        cidRepo.save(new Cidade(4123956, "SANTA MÔNICA", uf));
        cidRepo.save(new Cidade(4124202, "SANTO ANTÔNIO DO CAIUÁ", uf));
        cidRepo.save(new Cidade(4124608, "SÃO CARLOS DO IVAÍ", uf));
        cidRepo.save(new Cidade(4124905, "SÃO JOÃO DO CAIUÁ", uf));
        cidRepo.save(new Cidade(4125902, "SÃO PEDRO DO PARANÁ", uf));
        cidRepo.save(new Cidade(4126702, "TAMBOARA", uf));
        cidRepo.save(new Cidade(4127304, "TERRA RICA", uf));
        cidRepo.save(new Cidade(4100509, "ALTÔNIA", uf));
        cidRepo.save(new Cidade(4100707, "ALTO PIQUIRI", uf));
        cidRepo.save(new Cidade(4103370, "BRASILÂNDIA DO SUL", uf));
        cidRepo.save(new Cidade(4103479, "CAFEZAL DO SUL", uf));
        cidRepo.save(new Cidade(4106605, "CRUZEIRO DO OESTE", uf));
        cidRepo.save(new Cidade(4107256, "DOURADINA", uf));
        cidRepo.save(new Cidade(4107520, "ESPERANÇA NOVA", uf));
        cidRepo.save(new Cidade(4108320, "FRANCISCO ALVES", uf));
        cidRepo.save(new Cidade(4109906, "ICARAÍMA", uf));
        cidRepo.save(new Cidade(4110607, "IPORÃ", uf));
        cidRepo.save(new Cidade(4111555, "IVATÉ", uf));
        cidRepo.save(new Cidade(4114708, "MARIA HELENA", uf));
        cidRepo.save(new Cidade(4115101, "MARILUZ", uf));
        cidRepo.save(new Cidade(4117206, "NOVA OLÍMPIA", uf));
        cidRepo.save(new Cidade(4118857, "PEROBAL", uf));
        cidRepo.save(new Cidade(4118907, "PÉROLA", uf));
        cidRepo.save(new Cidade(4125357, "SÃO JORGE DO PATROCÍNIO", uf));
        cidRepo.save(new Cidade(4126900, "TAPIRA", uf));
        cidRepo.save(new Cidade(4128104, "UMUARAMA", uf));
        cidRepo.save(new Cidade(4128625, "ALTO PARAÍSO", uf));
        cidRepo.save(new Cidade(4128807, "XAMBRÊ", uf));
        cidRepo.save(new Cidade(4105508, "CIANORTE", uf));
        cidRepo.save(new Cidade(4105607, "CIDADE GAÚCHA", uf));
        cidRepo.save(new Cidade(4109104, "GUAPOREMA", uf));
        cidRepo.save(new Cidade(4110409, "INDIANÓPOLIS", uf));
        cidRepo.save(new Cidade(4112405, "JAPURÁ", uf));
        cidRepo.save(new Cidade(4113007, "JUSSARA", uf));
        cidRepo.save(new Cidade(4122602, "RONDON", uf));
        cidRepo.save(new Cidade(4125555, "SÃO MANOEL DO PARANÁ", uf));
        cidRepo.save(new Cidade(4126108, "SÃO TOMÉ", uf));
        cidRepo.save(new Cidade(4126801, "TAPEJARA", uf));
        cidRepo.save(new Cidade(4127908, "TUNEIRAS DO OESTE", uf));
        cidRepo.save(new Cidade(4100459, "ALTAMIRA DO PARANÁ", uf));
        cidRepo.save(new Cidade(4103008, "BOA ESPERANÇA", uf));
        cidRepo.save(new Cidade(4103909, "CAMPINA DA LAGOA", uf));
        cidRepo.save(new Cidade(4108601, "GOIOERÊ", uf));
        cidRepo.save(new Cidade(4112207, "JANIÓPOLIS", uf));
        cidRepo.save(new Cidade(4112959, "JURANDA", uf));
        cidRepo.save(new Cidade(4116109, "MOREIRA SALES", uf));
        cidRepo.save(new Cidade(4116802, "NOVA CANTU", uf));
        cidRepo.save(new Cidade(4120655, "QUARTO CENTENÁRIO", uf));
        cidRepo.save(new Cidade(4121356, "RANCHO ALEGRE D OESTE", uf));
        cidRepo.save(new Cidade(4128005, "UBIRATÃ", uf));
        cidRepo.save(new Cidade(4101705, "ARARUNA", uf));
        cidRepo.save(new Cidade(4102505, "BARBOSA FERRAZ", uf));
        cidRepo.save(new Cidade(4104303, "CAMPO MOURÃO", uf));
        cidRepo.save(new Cidade(4106555, "CORUMBATAÍ DO SUL", uf));
        cidRepo.save(new Cidade(4107504, "ENGENHEIRO BELTRÃO", uf));
        cidRepo.save(new Cidade(4107553, "FAROL", uf));
        cidRepo.save(new Cidade(4107702, "FÊNIX", uf));
        cidRepo.save(new Cidade(4110805, "IRETAMA", uf));
        cidRepo.save(new Cidade(4113734, "LUIZIANA", uf));
        cidRepo.save(new Cidade(4114005, "MAMBORÊ", uf));
        cidRepo.save(new Cidade(4118808, "PEABIRU", uf));
        cidRepo.save(new Cidade(4121109, "QUINTA DO SOL", uf));
        cidRepo.save(new Cidade(4122503, "RONCADOR", uf));
        cidRepo.save(new Cidade(4127205, "TERRA BOA", uf));
        cidRepo.save(new Cidade(4101150, "ÂNGULO", uf));
        cidRepo.save(new Cidade(4102109, "ASTORGA", uf));
        cidRepo.save(new Cidade(4102208, "ATALAIA", uf));
        cidRepo.save(new Cidade(4103404, "CAFEARA", uf));
        cidRepo.save(new Cidade(4105102, "CENTENÁRIO DO SUL", uf));
        cidRepo.save(new Cidade(4105904, "COLORADO", uf));
        cidRepo.save(new Cidade(4108106, "FLÓRIDA", uf));
        cidRepo.save(new Cidade(4109203, "GUARACI", uf));
        cidRepo.save(new Cidade(4110003, "IGUARAÇU", uf));
        cidRepo.save(new Cidade(4110904, "ITAGUAJÉ", uf));
        cidRepo.save(new Cidade(4111902, "JAGUAPITÃ", uf));
        cidRepo.save(new Cidade(4113601, "LOBATO", uf));
        cidRepo.save(new Cidade(4113809, "LUPIONÓPOLIS", uf));
        cidRepo.save(new Cidade(4114104, "MANDAGUAÇU", uf));
        cidRepo.save(new Cidade(4116307, "MUNHOZ DE MELO", uf));
        cidRepo.save(new Cidade(4116406, "NOSSA SENHORA DAS GRAÇAS", uf));
        cidRepo.save(new Cidade(4116901, "NOVA ESPERANÇA", uf));
        cidRepo.save(new Cidade(4120408, "PRESIDENTE CASTELO BRANCO", uf));
        cidRepo.save(new Cidade(4123402, "SANTA FÉ", uf));
        cidRepo.save(new Cidade(4123600, "SANTA INÊS", uf));
        cidRepo.save(new Cidade(4124509, "SANTO INÁCIO", uf));
        cidRepo.save(new Cidade(4128302, "UNIFLOR", uf));
        cidRepo.save(new Cidade(4100806, "ALVORADA DO SUL", uf));
        cidRepo.save(new Cidade(4102802, "BELA VISTA DO PARAÍSO", uf));
        cidRepo.save(new Cidade(4108007, "FLORESTÓPOLIS", uf));
        cidRepo.save(new Cidade(4116000, "MIRASELVA", uf));
        cidRepo.save(new Cidade(4120002, "PORECATU", uf));
        cidRepo.save(new Cidade(4120333, "PRADO FERREIRA", uf));
        cidRepo.save(new Cidade(4120507, "PRIMEIRO DE MAIO", uf));
        cidRepo.save(new Cidade(4126504, "SERTANÓPOLIS", uf));
        cidRepo.save(new Cidade(4107306, "DOUTOR CAMARGO", uf));
        cidRepo.save(new Cidade(4107801, "FLORAÍ", uf));
        cidRepo.save(new Cidade(4107900, "FLORESTA", uf));
        cidRepo.save(new Cidade(4111100, "ITAMBÉ", uf));
        cidRepo.save(new Cidade(4111605, "IVATUBA", uf));
        cidRepo.save(new Cidade(4117404, "OURIZONA", uf));
        cidRepo.save(new Cidade(4125308, "SÃO JORGE DO IVAÍ", uf));
        cidRepo.save(new Cidade(4114203, "MANDAGUARI", uf));
        cidRepo.save(new Cidade(4114807, "MARIALVA", uf));
        cidRepo.save(new Cidade(4115200, "MARINGÁ", uf));
        cidRepo.save(new Cidade(4117503, "PAIÇANDU", uf));
        cidRepo.save(new Cidade(4126256, "SARANDI", uf));
        cidRepo.save(new Cidade(4101408, "APUCARANA", uf));
        cidRepo.save(new Cidade(4101507, "ARAPONGAS", uf));
        cidRepo.save(new Cidade(4103503, "CALIFÓRNIA", uf));
        cidRepo.save(new Cidade(4103800, "CAMBIRA", uf));
        cidRepo.save(new Cidade(4112108, "JANDAIA DO SUL", uf));
        cidRepo.save(new Cidade(4114906, "MARILÂNDIA DO SUL", uf));
        cidRepo.save(new Cidade(4115754, "MAUÁ DA SERRA", uf));
        cidRepo.save(new Cidade(4117297, "NOVO ITACOLOMI", uf));
        cidRepo.save(new Cidade(4122701, "SABÁUDIA", uf));
        cidRepo.save(new Cidade(4103701, "CAMBÉ", uf));
        cidRepo.save(new Cidade(4109807, "IBIPORÃ", uf));
        cidRepo.save(new Cidade(4113700, "LONDRINA", uf));
        cidRepo.save(new Cidade(4119657, "PITANGUEIRAS", uf));
        cidRepo.save(new Cidade(4122404, "ROLÂNDIA", uf));
        cidRepo.save(new Cidade(4126678, "TAMARANA", uf));
        cidRepo.save(new Cidade(4103206, "BOM SUCESSO", uf));
        cidRepo.save(new Cidade(4103305, "BORRAZÓPOLIS", uf));
        cidRepo.save(new Cidade(4106852, "CRUZMALTINA", uf));
        cidRepo.save(new Cidade(4107603, "FAXINAL", uf));
        cidRepo.save(new Cidade(4113106, "KALORÉ", uf));
        cidRepo.save(new Cidade(4115507, "MARUMBI", uf));
        cidRepo.save(new Cidade(4122107, "RIO BOM", uf));
        cidRepo.save(new Cidade(4101655, "ARAPUÃ", uf));
        cidRepo.save(new Cidade(4101853, "ARIRANHA DO IVAÍ", uf));
        cidRepo.save(new Cidade(4104402, "CÂNDIDO DE ABREU", uf));
        cidRepo.save(new Cidade(4108551, "GODOY MOREIRA", uf));
        cidRepo.save(new Cidade(4108700, "GRANDES RIOS", uf));
        cidRepo.save(new Cidade(4111506, "IVAIPORÃ", uf));
        cidRepo.save(new Cidade(4112504, "JARDIM ALEGRE", uf));
        cidRepo.save(new Cidade(4113429, "LIDIANÓPOLIS", uf));
        cidRepo.save(new Cidade(4113759, "LUNARDELLI", uf));
        cidRepo.save(new Cidade(4114500, "MANOEL RIBAS", uf));
        cidRepo.save(new Cidade(4117271, "NOVA TEBAS", uf));
        cidRepo.save(new Cidade(4122172, "RIO BRANCO DO IVAÍ", uf));
        cidRepo.save(new Cidade(4122651, "ROSÁRIO DO IVAÍ", uf));
        cidRepo.save(new Cidade(4125001, "SÃO JOÃO DO IVAÍ", uf));
        cidRepo.save(new Cidade(4125803, "SÃO PEDRO DO IVAÍ", uf));
        cidRepo.save(new Cidade(4101903, "ASSAÍ", uf));
        cidRepo.save(new Cidade(4112702, "JATAIZINHO", uf));
        cidRepo.save(new Cidade(4117214, "NOVA SANTA BÁRBARA", uf));
        cidRepo.save(new Cidade(4121307, "RANCHO ALEGRE", uf));
        cidRepo.save(new Cidade(4123204, "SANTA CECÍLIA DO PAVÃO", uf));
        cidRepo.save(new Cidade(4124707, "SÃO JERÔNIMO DA SERRA", uf));
        cidRepo.save(new Cidade(4126009, "SÃO SEBASTIÃO DA AMOREIRA", uf));
        cidRepo.save(new Cidade(4128401, "URAÍ", uf));
        cidRepo.save(new Cidade(4100103, "ABATIÁ", uf));
        cidRepo.save(new Cidade(4101101, "ANDIRÁ", uf));
        cidRepo.save(new Cidade(4102406, "BANDEIRANTES", uf));
        cidRepo.save(new Cidade(4106001, "CONGONHINHAS", uf));
        cidRepo.save(new Cidade(4106407, "CORNÉLIO PROCÓPIO", uf));
        cidRepo.save(new Cidade(4111001, "ITAMBARACÁ", uf));
        cidRepo.save(new Cidade(4113403, "LEÓPOLIS", uf));
        cidRepo.save(new Cidade(4116604, "NOVA AMÉRICA DA COLINA", uf));
        cidRepo.save(new Cidade(4117008, "NOVA FÁTIMA", uf));
        cidRepo.save(new Cidade(4121901, "RIBEIRÃO DO PINHAL", uf));
        cidRepo.save(new Cidade(4123105, "SANTA AMÉLIA", uf));
        cidRepo.save(new Cidade(4123907, "SANTA MARIANA", uf));
        cidRepo.save(new Cidade(4124301, "SANTO ANTÔNIO DO PARAÍSO", uf));
        cidRepo.save(new Cidade(4126405, "SERTANEJA", uf));
        cidRepo.save(new Cidade(4102703, "BARRA DO JACARÉ", uf));
        cidRepo.save(new Cidade(4103602, "CAMBARÁ", uf));
        cidRepo.save(new Cidade(4111803, "JACAREZINHO", uf));
        cidRepo.save(new Cidade(4112900, "JUNDIAÍ DO SUL", uf));
        cidRepo.save(new Cidade(4121802, "RIBEIRÃO CLARO", uf));
        cidRepo.save(new Cidade(4124103, "SANTO ANTÔNIO DA PLATINA", uf));
        cidRepo.save(new Cidade(4106100, "CONSELHEIRO MAIRINCK", uf));
        cidRepo.save(new Cidade(4107009, "CURIÚVA", uf));
        cidRepo.save(new Cidade(4107751, "FIGUEIRA", uf));
        cidRepo.save(new Cidade(4109708, "IBAITI", uf));
        cidRepo.save(new Cidade(4111704, "JABOTI", uf));
        cidRepo.save(new Cidade(4112306, "JAPIRA", uf));
        cidRepo.save(new Cidade(4119202, "PINHALÃO", uf));
        cidRepo.save(new Cidade(4126207, "SAPOPEMA", uf));
        cidRepo.save(new Cidade(4104709, "CARLÓPOLIS", uf));
        cidRepo.save(new Cidade(4109005, "GUAPIRAMA", uf));
        cidRepo.save(new Cidade(4112801, "JOAQUIM TÁVORA", uf));
        cidRepo.save(new Cidade(4120705, "QUATIGUÁ", uf));
        cidRepo.save(new Cidade(4122909, "SALTO DO ITARARÉ", uf));
        cidRepo.save(new Cidade(4124004, "SANTANA DO ITARARÉ", uf));
        cidRepo.save(new Cidade(4125407, "SÃO JOSÉ DA BOA VISTA", uf));
        cidRepo.save(new Cidade(4126603, "SIQUEIRA CAMPOS", uf));
        cidRepo.save(new Cidade(4127809, "TOMAZINA", uf));
        cidRepo.save(new Cidade(4128500, "WENCESLAU BRAZ", uf));
        cidRepo.save(new Cidade(4110078, "IMBAÚ", uf));
        cidRepo.save(new Cidade(4117305, "ORTIGUEIRA", uf));
        cidRepo.save(new Cidade(4121703, "RESERVA", uf));
        cidRepo.save(new Cidade(4127106, "TELÊMACO BORBA", uf));
        cidRepo.save(new Cidade(4127502, "TIBAGI", uf));
        cidRepo.save(new Cidade(4128534, "VENTANIA", uf));
        cidRepo.save(new Cidade(4101606, "ARAPOTI", uf));
        cidRepo.save(new Cidade(4112009, "JAGUARIAÍVA", uf));
        cidRepo.save(new Cidade(4119400, "PIRAÍ DO SUL", uf));
        cidRepo.save(new Cidade(4126306, "SENGÉS", uf));
        cidRepo.save(new Cidade(4104659, "CARAMBEÍ", uf));
        cidRepo.save(new Cidade(4104907, "CASTRO", uf));
        cidRepo.save(new Cidade(4117701, "PALMEIRA", uf));
        cidRepo.save(new Cidade(4119905, "PONTA GROSSA", uf));
        cidRepo.save(new Cidade(4102000, "ASSIS CHATEAUBRIAND", uf));
        cidRepo.save(new Cidade(4107157, "DIAMANTE D OESTE", uf));
        cidRepo.save(new Cidade(4107538, "ENTRE RIOS DO OESTE", uf));
        cidRepo.save(new Cidade(4108205, "FORMOSA DO OESTE", uf));
        cidRepo.save(new Cidade(4108809, "GUAÍRA", uf));
        cidRepo.save(new Cidade(4110656, "IRACEMA DO OESTE", uf));
        cidRepo.save(new Cidade(4112751, "JESUÍTAS", uf));
        cidRepo.save(new Cidade(4114609, "MARECHAL CÂNDIDO RONDON", uf));
        cidRepo.save(new Cidade(4115358, "MARIPÁ", uf));
        cidRepo.save(new Cidade(4115853, "MERCEDES", uf));
        cidRepo.save(new Cidade(4117222, "NOVA SANTA ROSA", uf));
        cidRepo.save(new Cidade(4117453, "OURO VERDE DO OESTE", uf));
        cidRepo.save(new Cidade(4117909, "PALOTINA", uf));
        cidRepo.save(new Cidade(4118451, "PATO BRAGADO", uf));
        cidRepo.save(new Cidade(4120853, "QUATRO PONTES", uf));
        cidRepo.save(new Cidade(4123501, "SANTA HELENA", uf));
        cidRepo.save(new Cidade(4125456, "SÃO JOSÉ DAS PALMEIRAS", uf));
        cidRepo.save(new Cidade(4125753, "SÃO PEDRO DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4127403, "TERRA ROXA", uf));
        cidRepo.save(new Cidade(4127700, "TOLEDO", uf));
        cidRepo.save(new Cidade(4127957, "TUPÃSSI", uf));
        cidRepo.save(new Cidade(4101051, "ANAHY", uf));
        cidRepo.save(new Cidade(4103057, "BOA VISTA DA APARECIDA", uf));
        cidRepo.save(new Cidade(4103354, "BRAGANEY", uf));
        cidRepo.save(new Cidade(4103453, "CAFELÂNDIA", uf));
        cidRepo.save(new Cidade(4104055, "CAMPO BONITO", uf));
        cidRepo.save(new Cidade(4104600, "CAPITÃO LEÔNIDAS MARQUES", uf));
        cidRepo.save(new Cidade(4104808, "CASCAVEL", uf));
        cidRepo.save(new Cidade(4105003, "CATANDUVAS", uf));
        cidRepo.save(new Cidade(4106308, "CORBÉLIA", uf));
        cidRepo.save(new Cidade(4107124, "DIAMANTE DO SUL", uf));
        cidRepo.save(new Cidade(4109302, "GUARANIAÇU", uf));
        cidRepo.save(new Cidade(4109757, "IBEMA", uf));
        cidRepo.save(new Cidade(4110052, "IGUATU", uf));
        cidRepo.save(new Cidade(4113452, "LINDOESTE", uf));
        cidRepo.save(new Cidade(4116703, "NOVA AURORA", uf));
        cidRepo.save(new Cidade(4123824, "SANTA LÚCIA", uf));
        cidRepo.save(new Cidade(4124020, "SANTA TEREZA DO OESTE", uf));
        cidRepo.save(new Cidade(4127858, "TRÊS BARRAS DO PARANÁ", uf));
        cidRepo.save(new Cidade(4105300, "CÉU AZUL", uf));
        cidRepo.save(new Cidade(4108304, "FOZ DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4110953, "ITAIPULÂNDIA", uf));
        cidRepo.save(new Cidade(4115606, "MATELÂNDIA", uf));
        cidRepo.save(new Cidade(4115804, "MEDIANEIRA", uf));
        cidRepo.save(new Cidade(4116059, "MISSAL", uf));
        cidRepo.save(new Cidade(4121257, "RAMILÂNDIA", uf));
        cidRepo.save(new Cidade(4124053, "SANTA TEREZINHA DE ITAIPU", uf));
        cidRepo.save(new Cidade(4125704, "SÃO MIGUEL DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4126355, "SERRANÓPOLIS DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4128559, "VERA CRUZ DO OESTE", uf));
        cidRepo.save(new Cidade(4101002, "AMPÉRE", uf));
        cidRepo.save(new Cidade(4102752, "BELA VISTA DA CAROBA", uf));
        cidRepo.save(new Cidade(4104501, "CAPANEMA", uf));
        cidRepo.save(new Cidade(4119004, "PÉROLA D OESTE", uf));
        cidRepo.save(new Cidade(4119806, "PLANALTO", uf));
        cidRepo.save(new Cidade(4120358, "PRANCHITA", uf));
        cidRepo.save(new Cidade(4121406, "REALEZA", uf));
        cidRepo.save(new Cidade(4123808, "SANTA IZABEL DO OESTE", uf));
        cidRepo.save(new Cidade(4102604, "BARRACÃO", uf));
        cidRepo.save(new Cidade(4103024, "BOA ESPERANÇA DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4103156, "BOM JESUS DO SUL", uf));
        cidRepo.save(new Cidade(4106571, "CRUZEIRO DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4107207, "DOIS VIZINHOS", uf));
        cidRepo.save(new Cidade(4107405, "ENÉAS MARQUES", uf));
        cidRepo.save(new Cidade(4107850, "FLOR DA SERRA DO SUL", uf));
        cidRepo.save(new Cidade(4108403, "FRANCISCO BELTRÃO", uf));
        cidRepo.save(new Cidade(4114351, "MANFRINÓPOLIS", uf));
        cidRepo.save(new Cidade(4115408, "MARMELEIRO", uf));
        cidRepo.save(new Cidade(4116950, "NOVA ESPERANÇA DO SUDOESTE", uf));
        cidRepo.save(new Cidade(4117255, "NOVA PRATA DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4119251, "PINHAL DE SÃO BENTO", uf));
        cidRepo.save(new Cidade(4121604, "RENASCENÇA", uf));
        cidRepo.save(new Cidade(4122800, "SALGADO FILHO", uf));
        cidRepo.save(new Cidade(4123006, "SALTO DO LONTRA", uf));
        cidRepo.save(new Cidade(4124400, "SANTO ANTÔNIO DO SUDOESTE", uf));
        cidRepo.save(new Cidade(4125209, "SÃO JORGE D OESTE", uf));
        cidRepo.save(new Cidade(4128609, "VERÊ", uf));
        cidRepo.save(new Cidade(4103222, "BOM SUCESSO DO SUL", uf));
        cidRepo.save(new Cidade(4105409, "CHOPINZINHO", uf));
        cidRepo.save(new Cidade(4106506, "CORONEL VIVIDA", uf));
        cidRepo.save(new Cidade(4111209, "ITAPEJARA D OESTE", uf));
        cidRepo.save(new Cidade(4115309, "MARIÓPOLIS", uf));
        cidRepo.save(new Cidade(4118501, "PATO BRANCO", uf));
        cidRepo.save(new Cidade(4124806, "SÃO JOÃO", uf));
        cidRepo.save(new Cidade(4126272, "SAUDADE DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4126652, "SULINA", uf));
        cidRepo.save(new Cidade(4128708, "VITORINO", uf));
        cidRepo.save(new Cidade(4103040, "BOA VENTURA DE SÃO ROQUE", uf));
        cidRepo.save(new Cidade(4113254, "LARANJAL", uf));
        cidRepo.save(new Cidade(4115739, "MATO RICO", uf));
        cidRepo.save(new Cidade(4117800, "PALMITAL", uf));
        cidRepo.save(new Cidade(4119608, "PITANGA", uf));
        cidRepo.save(new Cidade(4123857, "SANTA MARIA DO OESTE", uf));
        cidRepo.save(new Cidade(4103958, "CAMPINA DO SIMÃO", uf));
        cidRepo.save(new Cidade(4104428, "CANDÓI", uf));
        cidRepo.save(new Cidade(4104451, "CANTAGALO", uf));
        cidRepo.save(new Cidade(4107546, "ESPIGÃO ALTO DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4108452, "FOZ DO JORDÃO", uf));
        cidRepo.save(new Cidade(4108650, "GOIOXIM", uf));
        cidRepo.save(new Cidade(4109401, "GUARAPUAVA", uf));
        cidRepo.save(new Cidade(4110201, "INÁCIO MARTINS", uf));
        cidRepo.save(new Cidade(4113304, "LARANJEIRAS DO SUL", uf));
        cidRepo.save(new Cidade(4115457, "MARQUINHO", uf));
        cidRepo.save(new Cidade(4117057, "NOVA LARANJEIRAS", uf));
        cidRepo.save(new Cidade(4119301, "PINHÃO", uf));
        cidRepo.save(new Cidade(4120150, "PORTO BARREIRO", uf));
        cidRepo.save(new Cidade(4120903, "QUEDAS DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4121752, "RESERVA DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4122156, "RIO BONITO DO IGUAÇU", uf));
        cidRepo.save(new Cidade(4127965, "TURVO", uf));
        cidRepo.save(new Cidade(4128658, "VIRMOND", uf));
        cidRepo.save(new Cidade(4105706, "CLEVELÂNDIA", uf));
        cidRepo.save(new Cidade(4106456, "CORONEL DOMINGOS SOARES", uf));
        cidRepo.save(new Cidade(4109658, "HONÓRIO SERPA", uf));
        cidRepo.save(new Cidade(4114401, "MANGUEIRINHA", uf));
        cidRepo.save(new Cidade(4117602, "PALMAS", uf));
        cidRepo.save(new Cidade(4107736, "FERNANDES PINHEIRO", uf));
        cidRepo.save(new Cidade(4108957, "GUAMIRANGA", uf));
        cidRepo.save(new Cidade(4110102, "IMBITUVA", uf));
        cidRepo.save(new Cidade(4110508, "IPIRANGA", uf));
        cidRepo.save(new Cidade(4111407, "IVAÍ", uf));
        cidRepo.save(new Cidade(4120606, "PRUDENTÓPOLIS", uf));
        cidRepo.save(new Cidade(4127007, "TEIXEIRA SOARES", uf));
        cidRepo.save(new Cidade(4110706, "IRATI", uf));
        cidRepo.save(new Cidade(4113908, "MALLET", uf));
        cidRepo.save(new Cidade(4121505, "REBOUÇAS", uf));
        cidRepo.save(new Cidade(4122008, "RIO AZUL", uf));
        cidRepo.save(new Cidade(4102901, "BITURUNA", uf));
        cidRepo.save(new Cidade(4106803, "CRUZ MACHADO", uf));
        cidRepo.save(new Cidade(4108502, "GENERAL CARNEIRO", uf));
        cidRepo.save(new Cidade(4118600, "PAULA FREITAS", uf));
        cidRepo.save(new Cidade(4118709, "PAULO FRONTIN", uf));
        cidRepo.save(new Cidade(4120309, "PORTO VITÓRIA", uf));
        cidRepo.save(new Cidade(4128203, "UNIÃO DA VITÓRIA", uf));
        cidRepo.save(new Cidade(4101309, "ANTÔNIO OLINTO", uf));
        cidRepo.save(new Cidade(4125100, "SÃO JOÃO DO TRIUNFO", uf));
        cidRepo.save(new Cidade(4125605, "SÃO MATEUS DO SUL", uf));
        cidRepo.save(new Cidade(4100202, "ADRIANÓPOLIS", uf));
        cidRepo.save(new Cidade(4105201, "CERRO AZUL", uf));
        cidRepo.save(new Cidade(4128633, "DOUTOR ULYSSES", uf));
        cidRepo.save(new Cidade(4113205, "LAPA", uf));
        cidRepo.save(new Cidade(4120101, "PORTO AMAZONAS", uf));
        cidRepo.save(new Cidade(4100400, "ALMIRANTE TAMANDARÉ", uf));
        cidRepo.save(new Cidade(4101804, "ARAUCÁRIA", uf));
        cidRepo.save(new Cidade(4102307, "BALSA NOVA", uf));
        cidRepo.save(new Cidade(4103107, "BOCAIÚVA DO SUL", uf));
        cidRepo.save(new Cidade(4104006, "CAMPINA GRANDE DO SUL", uf));
        cidRepo.save(new Cidade(4104204, "CAMPO LARGO", uf));
        cidRepo.save(new Cidade(4104253, "CAMPO MAGRO", uf));
        cidRepo.save(new Cidade(4105805, "COLOMBO", uf));
        cidRepo.save(new Cidade(4106209, "CONTENDA", uf));
        cidRepo.save(new Cidade(4106902, "CURITIBA", uf));
        cidRepo.save(new Cidade(4107652, "FAZENDA RIO GRANDE", uf));
        cidRepo.save(new Cidade(4111258, "ITAPERUÇU", uf));
        cidRepo.save(new Cidade(4114302, "MANDIRITUBA", uf));
        cidRepo.save(new Cidade(4119152, "PINHAIS", uf));
        cidRepo.save(new Cidade(4119509, "PIRAQUARA", uf));
        cidRepo.save(new Cidade(4120804, "QUATRO BARRAS", uf));
        cidRepo.save(new Cidade(4122206, "RIO BRANCO DO SUL", uf));
        cidRepo.save(new Cidade(4125506, "SÃO JOSÉ DOS PINHAIS", uf));
        cidRepo.save(new Cidade(4127882, "TUNAS DO PARANÁ", uf));
        cidRepo.save(new Cidade(4101200, "ANTONINA", uf));
        cidRepo.save(new Cidade(4109500, "GUARAQUEÇABA", uf));
        cidRepo.save(new Cidade(4109609, "GUARATUBA", uf));
        cidRepo.save(new Cidade(4115705, "MATINHOS", uf));
        cidRepo.save(new Cidade(4116208, "MORRETES", uf));
        cidRepo.save(new Cidade(4118204, "PARANAGUÁ", uf));
        cidRepo.save(new Cidade(4119954, "PONTAL DO PARANÁ", uf));
        cidRepo.save(new Cidade(4100301, "AGUDOS DO SUL", uf));
        cidRepo.save(new Cidade(4104105, "CAMPO DO TENENTE", uf));
        cidRepo.save(new Cidade(4119103, "PIÊN", uf));
        cidRepo.save(new Cidade(4121208, "QUITANDINHA", uf));
        cidRepo.save(new Cidade(4122305, "RIO NEGRO", uf));
        cidRepo.save(new Cidade(4127601, "TIJUCAS DO SUL", uf));
        
        
    }
}

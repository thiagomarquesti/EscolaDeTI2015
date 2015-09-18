module.config(function($routeProvider, $locationProvider) {  
//------- Rotas do Usuário --------
    $routeProvider
    .when('/', {
        templateUrl: 'views/home.html',
        controller: 'HomeController'
    }).when('/Usuario/listar', {
        templateUrl: 'views/usuariosListar.html',
        controller: 'UsuarioController'
    }).when('/Usuario/novo', {
        templateUrl: 'views/usuarioCadastrar.html',
        controller: 'UsuarioController'
    }).when('/Usuario/editar/:id', {
        templateUrl: 'views/usuarioCadastrar.html',
        controller: 'UsuarioController'
    }).when('/Usuario/editar', {
        templateUrl: 'views/usuarioCadastrar.html',
        controller: 'UsuarioController'
    })
//------- Rotas do Perfil --------
    .when('/Perfil/listar', {
        templateUrl: 'views/perfilListar.html',
        controller: 'PerfilController'
    }).when('/Perfil/novo', {
        templateUrl: 'views/perfilCadastrar.html',
        controller: 'PerfilController'
    }).when('/Perfil/editar/:id', {
        templateUrl: 'views/perfilCadastrar.html',
        controller: 'PerfilController'
    }).when('/Perfil/editar', {
        templateUrl: 'views/perfilCadastrar.html',
        controller: 'PerfilController'
//------- Convênio -------        
    }).when('/Convenio/novo', {
        templateUrl: 'views/convenioCadastrar.html',
        controller: 'ConvenioController'
    }).when('/Convenio/listar', {
        templateUrl: 'views/convenioListar.html',
        controller: 'ConvenioController'
    }).when('/Convenio/editar/:id', {
        templateUrl: 'views/convenioCadastrar.html',
        controller: 'ConvenioController'
    })
 //------- Rotas do Etnia --------   
    .when('/Etnia/nova', {
        templateUrl: 'views/etniaCadastrar.html',
        controller: 'EtniaController'
    }).when('/Etnia/listar', {
        templateUrl: 'views/etniaListar.html',
        controller: 'EtniaController'
    }).when('/Etnia/editar/:id', {
        templateUrl: 'views/etniaCadastrar.html',
        controller: 'EtniaController'
    })
    //------- Rotas do Função --------   
    .when('/Funcao/nova', {
        templateUrl: 'views/funcaoCadastrar.html',
        controller: 'FuncaoController'
    }).when('/Funcao/listar', {
        templateUrl: 'views/funcaoListar.html',
        controller: 'FuncaoController'
    }).when('/Funcao/editar/:id', {
        templateUrl: 'views/funcaoCadastrar.html',
        controller: 'FuncaoController'
    })
    //------- Rotas da Terra Indigena --------   
    .when('/TerraIndigena/nova', {
        templateUrl: 'views/terraCadastrar.html',
        controller: 'TerraController'
    }).when('/TerraIndigena/listar', {
        templateUrl: 'views/terraListar.html',
        controller: 'TerraController'
    }).when('/TerraIndigena/editar/:id', {
        templateUrl: 'views/terraCadastrar.html',
        controller: 'TerraController'
    })
    //------- Rotas do Indigena --------   
    .when('/Indigena/novo', {
        templateUrl: 'views/indigenaCadastrar.html',
        controller: 'IndigenaController'
    }).when('/Indigena/listar', {
        templateUrl: 'views/indigenaListar.html',
        controller: 'IndigenaController'
    }).when('/Indigena/editar/:id', {
        templateUrl: 'views/indigenaCadastrar.html',
        controller: 'IndigenaController'
    })
    //------- Rotas de Estadia de familia
    .when('/Estadia/listar', {
        templateUrl: 'views/estadiaListar.html',
        controller: 'EstadiaController'
    }).when('/Estadia/nova', {
        templateUrl: 'views/estadiaCadastrar.html',
        controller: 'EstadiaController'
    }).otherwise ({ redirectTo: '/' });    

    $locationProvider.html5Mode(false);
});
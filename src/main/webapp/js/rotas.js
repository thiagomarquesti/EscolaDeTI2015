module.config(function($routeProvider, $locationProvider) {
//------- Rotas do Usuário --------
    $routeProvider
    .when('/', {
        template: 'views/index.html',
        controller: ''
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
        templateUrl: 'views/listarPerfis.html',
        controller: 'PerfilController'
    }).when('/Perfil/novo', {
        templateUrl: 'views/cadPerfil.html',
        controller: 'PerfilController'
    }).when('/Perfil/editar/:id', {
        templateUrl: 'views/cadPerfil.html',
        controller: 'PerfilController'
    }).when('/Perfil/editar', {
        templateUrl: 'views/cadPerfil.html',
        controller: 'PerfilController'
//------- Convênio -------        
    }).when('/Convenios', {
        templateUrl: 'views/convenios.html',
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
    //------- Rotas do Indigena --------   
    .when('/Indigena/novo', {
        templateUrl: 'views/indigenaCadastrar.html',
        controller: 'IndigenaController'
    }).when('/Indigena/listar', {
        templateUrl: 'views/indigenaListar.html',
        controller: 'IndigenaController'
    }).when('/indigena/editar/:id', {
        templateUrl: 'views/indigenaCadastrar.html',
        controller: 'EtniaController'
    }).otherwise ({ redirectTo: '/' });

    $locationProvider.html5Mode(false);
});
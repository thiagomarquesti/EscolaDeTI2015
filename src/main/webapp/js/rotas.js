module.config(function($routeProvider, $locationProvider) {
//------- Rotas do Usuário --------
    $routeProvider
    .when('/', {
        template: '<h1>Página inicial</h1>',
        controller: 'UsuarioController'
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
//------- Rotas do Perfil --------
    }).when('/Perfil/listar', {
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
    }).when('/Etnias', {
        templateUrl: 'views/etnias.html',
        controller: 'EtniaController'
    }).otherwise ({ redirectTo: '/' });

    $locationProvider.html5Mode(false);
});
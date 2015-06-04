module.config(function($routeProvider, $locationProvider) {

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
    }).when('/Etnias', {
        templateUrl: 'views/etnias.html',
        controller: 'EtniaController'
    }).otherwise ({ redirectTo: '/' });

    $locationProvider.html5Mode(false);
});
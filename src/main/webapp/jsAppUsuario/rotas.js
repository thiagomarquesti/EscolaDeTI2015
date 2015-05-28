module.config(function($routeProvider, $locationProvider) {

    $routeProvider
    .when('/', {
        template: '<h1>Página inicial</h1>',
        controller: 'UsuarioController'
    }).when('/Usuario/listar', {
        templateUrl: 'views/listarUsuarios.html',
        controller: 'UsuarioController'
    }).when('/Usuario/novo', {
        templateUrl: 'views/cadUsuario.html',
        controller: 'UsuarioController'
    }).when('/Usuario/editar/:id', {
        templateUrl: 'views/cadUsuario.html',
        controller: 'UsuarioController'
    }).when('/Usuario/editar', {
        templateUrl: 'views/cadUsuario.html',
        controller: 'UsuarioController'
    });

    $locationProvider.html5Mode(false);
});
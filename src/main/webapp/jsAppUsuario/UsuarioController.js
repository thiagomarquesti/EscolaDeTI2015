module.controller("UsuarioController", ["$scope", "$http", "$routeParams", "$location", function($scope, $http, $routeParams, $location){
         
    function novoUsuario(){
        $scope.usuario = {
            nome : "",
            login : "",
            email : "",
            senha : "",
            status : "ATIVO"
        };
        $scope.isNovo = true;
    }

    $scope.salvar = function(){
        if($scope.isNovo){
            $http.post("/usuario", $scope.usuario)
               .success(function(){
                   alert("Usuário cadastrado com sucesso!");
                   $location.path("/Usuario/listar");
               })
               .error(deuErro);
        }
        else {
            $http.put("/usuario/", $scope.usuario)
               .success(function(){
                   alert("Usuário atualizado com sucesso!");
                   $location.path("/Usuario/listar");
               })
               .error(deuErro);
        }

    };

    $scope.atualizar = function(){
       $http.get("/usuario")
           .success(function(data){
               $scope.usuarios = data;
           })
           .error(deuErro);
    };

    function deuErro(){
        alert("Algo deu errado. Tente novamente.");
     }

    $scope.editar = function(usuario) {
        $location.path("/Usuario/editar/" + usuario.id);
        //$scope.isNovo = false;
    };

    $scope.carregar = function(){
        if($location.path() == "/Usuario/novo"){
            novoUsuario();
        }
        else {
            $http.get("/usuario/" + $routeParams.id)
                    .success(function(data){
                        $scope.usuario = data;
                        console.log(data);
                        $scope.isNovo = false;
                    })
                    .error(deuErro);
            }
    };

    $scope.reset = function(form) {
        if(form) {
          form.$setPristine();
          form.$setUntouched();
        }
        novoUsuario();
    };
}]);

module.directive('validPasswordC', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (viewValue, $scope) {
                var noMatch = viewValue != scope.formCad.senha.$viewValue;
                ctrl.$setValidity('noMatch', !noMatch);
            });
        }
    };
});
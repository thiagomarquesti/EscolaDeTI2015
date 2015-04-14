module.controller("LoginController", ["$scope", "$http", "$routeParams", "$location", function($scope, $http, $routeParams, $location){
         
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

    
}]);
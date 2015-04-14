module.controller("LoginController", ["$scope", "$http", "$routeParams", "$location", function($scope, $http, $routeParams, $location){
         
    
    function deuErro(){
        alert("Algo deu errado. Tente novamente.");
    }
    function erroLogin(){
        alert("Login ou senha incorretos. Tente novamente.");
    }

    $scope.verificaTelaLogin = function(){
         $http.get("/login/usuariologado")
           .success(function(data){
               if(0==0){
                   
               }
           })
           .error(deuErro);
    };
    
    $scope.logar = function(){
        $http.post("/login/efetuarlogin", $scope.login)
        .success(
            $location.url("/")
        )
           .error(erroLogin);
    };
}]);
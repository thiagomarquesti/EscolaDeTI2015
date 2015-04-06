
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
            
         //novoUsuario();
         
         $scope.salvar = function(){
             if($scope.isNovo){
                 $http.post("/usuario", $scope.usuario)
                    .success(function(){
                        alert("Usuário cadastrado com sucesso!");
                        $scope.atualizar();
                        novoUsuario();
                    })
                    .error(deuErro);
             }
             else {
                 $http.put("/usuario/", $scope.usuario)
                    .success(function(){
                        alert("Usuário atualizado com sucesso!");
                        $scope.atualizar();
                        novoUsuario();
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
         
         $scope.excluir = function(usuario){
             $http.delete("/usuario/" + usuario.id)
                .success(function(){
                    alert("Usuário excluído.");
                })
                .error(deuErro);
         };
                  
         function deuErro(){
            alert("Algo deu errado. Tente novamente.");
         }
         
    function alterar(usuario) {
        $location.path("/Usuario/editar" + usuario);
        //$scope.usuario = angular.copy(usuario);
        $scope.isNovo = false;
        
    };
       
    function deuErro(){
            alert("Algo deu errado. Tente novamente.");
         }
}]);
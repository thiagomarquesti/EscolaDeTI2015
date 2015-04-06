

module.controller("UsuarioController", ["$scope", "$http", function($scope, $http){
         
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
            
         novoUsuario();
         
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
                 $http.put("/usuario/" + $scope.usuario.id, $scope.usuario)
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
         $scope.atualizar();
         
         $scope.excluir = function(usuario){
             $http.delete("/usuario/" + usuario.id)
                .success(function(){
                    alert("Usuário excluído.");
                    $scope.atualizar();
                })
                .error(deuErro);
         };
         
         $scope.alterar = function(usuario) {
             $scope.usuario = angular.copy(usuario);
             $scope.isNovo = false;
         };
         
         function deuErro(){
            alert("Algo deu errado. Tente novamente.");
         }
         
    }]);

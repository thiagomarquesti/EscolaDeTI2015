module.controller("IndigenaController", ["$scope", "$http", "$routeParams", "$location", function($scope, $http, $routeParams, $location){
         
    function novoIndio(){
        $scope.indio = {
            nome : "",
            status : "ATIVO"
        };
        $scope.isNovoIndio = true;
    }
    
    $scope.salvarIndio = function(){
        if($scope.isNovo){
            $http.post("/indigena", $scope.indio)
               .success(function(){
                   toastr.success("Indígena cadastrado com sucesso!");
                       $location.path("/Indigena/listar"); 
               })
               .error(deuErro);
        }
        else {
            $http.put("/indigena/", $scope.indio)
               .success(function(){
                   toastr.success("Indígena atualizado com sucesso!");
                   $location.path("/Indigena/listar");
               })
               .error(deuErro);
        }

    };

    $scope.atualizarIndio = function(){
       $http.get("/indigena")
           .success(function(data){
               $scope.indio = data;
           })
           .error(deuErro);
    };

    $scope.editarIndio = function(indio) {
        $location.path("/Indigena/editar/" + indio.id);
    };
    
    $scope.carregarIndios = function(){
        if($location.path() === "/Indigena/novo"){
            novoIndio();
        }
        else {
            $http.get("/indigena/" + $routeParams.id)
                    .success(function(data){
                        $scope.indio = data[0];
                        $scope.isNovoIndio = false;
                    })
                    .error(deuErro);
        }
    };

    $scope.reset = function(form) {
        if(form) {
          form.$setPristine();
          form.$setUntouched();
        }
        novoIndio();
    };
    
    
    $scope.logout = function(){
        $http.get("/login/usuariologado")
           .success(function(data){
               console.log(data.login);
               var dadosLogin = {"login": data.login, "senha" : data.senha };
               $http.post("/login/efetuarlogout", dadosLogin)
                .success(function() {
                    window.location.href="/login.html";
                }
                )
                .error(deuErro);
           })
           .error(deuErro);
    };
    
    function deuErro(){
        toastr.error("Algo deu errado. Tente novamente.");
    }
}]);

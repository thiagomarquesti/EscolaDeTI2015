module.controller("EtniaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {
    
    $scope.busca = {};
    $scope.placeHolder = "Buscar etnia";
    $scope.ent = $rootScope.ent = "etnia";
    $scope.campoPrincipal = 'descricao';
        
    $scope.atualizarListagens = function(qtdePorPag, pag, campo, string, troca, paro){
        if (campo == null || campo == "") { campo = $scope.campoPrincipal; }
        $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
        atualizaScope;
    };
    
    function atualizaScope() {
        $scope = $rootScope;
    }
    
    $rootScope.atualizarListagens = $scope.atualizarListagens;
    
    $scope.registrosPadrao = function() {
        $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
        $rootScope.numregistros = $scope.busca.numregistros;
    };
    
    $scope.fazPesquisa = function(registros, string){
        $rootScope.string = string;
        $scope.atualizarListagens(registros, 1, $scope.campoAtual, string, $rootScope.ent, 0, false);
    };
    
    $scope.deletarEtnia = function(etnia) {
        $http.delete("/etnia/" + etnia.idetnia)
            .success(function (status) {
                toastr.success("Etnia "+ etnia.descricao +" deletada com sucesso.");
                $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoPrincipal,'', '', false);
            })
            .error(deuErro);
    };
    
    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.", "Erro");
    }

    function novaEtnia() {
        $scope.etnia = {
            descricao: ""
        };
        $scope.isNovaEtnia = true;
    }
    
    $scope.novaEtnia = function() {
        novaEtnia();
    };
    
    $scope.carregarEtnia = function(){
        if($location.path() === "/Etnia/nova"){
            novaEtnia();
        }
        else {
            $http.get("/etnia/" + $routeParams.id)
                    .success(function(data){
                        console.log(data);
                        $scope.etnia = data;
                        $scope.isNovaEtnia = false;
                    })
                    .error(deuErro);
        }
    };
    
    $scope.todasEtnias = function(){
        $http.get("/etnia")
            .success(function (data) {
                $scope.etnias = data;
//                console.log($scope.etnias);
            })
            .error(deuErro);
    };
    
    $scope.editarEtnia = function(etnia) {
        $location.path("/Etnia/editar/" + etnia.idetnia);
    };

        $scope.salvarEtnia = function () {
        if ($scope.isNovaEtnia) {
            $http.post("/etnia", $scope.etnia)
                    .success(function () {
                        $location.path("/Etnia/listar");
                        toastr.success("Etnia inserida com sucesso!");
                    })
                    .error(deuErro);
        }
        else {
            $http.put("/etnia/", $scope.etnia)
                    .success(function () {
                        $location.path("/Etnia/listar");
                        toastr.success("Etnia atualizada com sucesso!");
                    })
                    .error(deuErro);
        }
        
    };
}]);
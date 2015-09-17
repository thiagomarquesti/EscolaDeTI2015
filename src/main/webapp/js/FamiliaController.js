module.controller("FamiliaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {

    $scope.busca = {};
    $scope.placeHolder = "Buscar família";
    $scope.ent = $rootScope.ent = "familia";
    $scope.campoPrincipal = 'nome';
    $rootScope.tipoOrdem = 'asc';
        
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
        
    function novaFamilia() {
        $scope.familia = {
            "nomefamilia": "",
            "representante": "",
            "telefone": "",
            "membros": []
        };
        $scope.isNovaFamilia = true;
    } 
    
    $scope.carregarFamilia = function () {
        if ($location.path() === "/Familia/nova") {
            novaFamilia();
        }
        else {
            $timeout(function () {
                $http.get("/familia/obj/" + $routeParams.id)
                    .success(function(data) {
                        var dados = data;
                        dados.nomefamilia = data.nome;
                        dados.representante = data.representante.codigoassindi;
                        dados.telefone = data.telefone.telefone;
                        dados.membros = data.membros;
                        
                        $scope.familia = dados;
                        $scope.isNovaFamilia = false;
                    })
                    .error(deuErro);
            }, 100);
        }
    };
    
    $scope.salvarFamilia = function () {
        var familiaCompleta = {
            nomefamilia: $scope.familia.nomefamilia ,
            representante: $scope.familia.representante,
            telefone: {
                telefone: $scope.familia.telefone
            },
            membros: $scope.familia.membros
        };
        
        if ($scope.isNovaFamilia) {
                $http.post("/familia", familiaCompleta)
                    .success(function () {
                        toastr.success("Família "+ familiaCompleta.nomefamilia +" cadastrada com sucesso!");
                        $location.path("/Familia/listar");
                    })
                    .error(erroCadastraFamilia);
        }
        else {
            familiaCompleta.idfamilia = $routeParams.id;
            $http.put("/familia", familiaCompleta)
                .success(function () {
                    toastr.success("Família atualizada com sucesso!");
                    $location.path("/Familia/listar");
                })
                .error(deuErro);
        }
    };
    
    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.");
    }
    
    function erroCadastraFamilia() {
        toastr.error("Não foi possível cadastrar a família, verifique os dados fornecidos. ","Erro");
    }
    
}]);

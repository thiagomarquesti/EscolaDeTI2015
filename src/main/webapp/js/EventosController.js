module.controller("EventosController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {
    $scope.busca = {};
    $scope.placeHolder = "Buscar Eventos";
    $scope.ent = $rootScope.ent = "descricao";
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

    function novoEvento() {
        $scope.evento = {
            descricao: ""
        };
        $scope.isNovoEvento = true;
    }

    $scope.novoEvento = function(){
        novoEvento();
    };

    $scope.carregarEvento = function () {
        if ($location.path() === "/Evento/novo") {
            novoEvento();
        }
        else {
            $http.get("/evento/" + $routeParams.id)
                    .success(function (data) {
                        $scope.evento = data[0];
                        $scope.isNovoEvento = false;
                    })
                    .error(deuErro);
        }
    };

    $scope.atualizarEvento = function () {
        $http.get("/evento")
                .success(function (data) {
                    $scope.evento = data;
                })
                .error(deuErro);
    };

    $scope.editarEvento = function (evento) {
        $location.path("/Evento/editar/" + evento.idevento);
    };

    $scope.salvarEvento = function () {
        if ($scope.isNovoEvento) {
            $http.post("/evento", $scope.evento)
                    .success(function () {
                        $location.path("/Eventoo/listar");
                        toastr.success("Evento inserido com sucesso!");
                    })
                    .error(deuErroSalvar);
        }
        else {
            $http.put("/evento/", $scope.evento)
                    .success(function () {
                        $location.path("/Evento/listar");
                        toastr.success("Evento atualizado com sucesso!");
                    })
                    .error(deuErroAtualizacao);
        }
    };

    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.");
    }

    function deuErroAtualizacao() {
        toastr.error("Atenção, erro ao tentar editar o convênio, verifique!");
    }

    function deuErroSalvar() {
        toastr.error("Atenção, erro ao tentar salvar o convênio, verifique!");
    }

}]);
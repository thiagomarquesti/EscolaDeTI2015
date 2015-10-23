module.controller("VisitaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', 'ServiceFuncoes', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {
        $scope.busca = {};
        $scope.placeHolder = "Buscar visita";
        $scope.ent = $rootScope.ent = "visita";
        $scope.campoPrincipal = 'datavisita';
        $scope.isNovaEstadia = true;
    
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
    
        function novaVisita() {
            $scope.visita = {
                datavisita : "",
                horavisita : "",
                horaentrada : "",
                horasaida : "",
                observacao : "",
                quantidadedevisitantes : "",
                seriecurso : "",
                visitarealizada : "",
                pessoaresponsavel : {  },
                entidade : { } 
            };
            $scope.isNovaVisita = true;
        }
        ;
        $scope.novaVisita = function () {
            novaVisita();
        };
        $scope.carregarVisita = function () {
            if ($location.path() === "/Visita/nova") {
                novaVisita();
            }
        };
    


$scope.editarVisita = function (visita) {
    $location.path("/Visita/editar/" + visita.idvisita);
};

$scope.deletarVisita = function (visita) {
    $http.delete("/visita/" + visita.idvisita)
            .success(function () {
                toastr.success("Visita " + visita.responsavel + " excluído com sucesso.");
                $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoPrincipal, '', '', $rootScope.ent, false);
            }).error(deuErroDeletar);
};

function deuErro() {
    toastr.error("Algo deu errado. Tente novamente.");
}

function deuErroAtualizacao() {
    toastr.error("Atenção, erro ao tentar editar a visita, verifique!");
}

function deuErroSalvar() {
    toastr.error("Atenção, erro ao tentar salvar a visita, verifique!");
}

function deuErroDeletar() {
    toastr.error("Atenção, erro ao tentar deletar a visita, verifique!");
}

$scope.salvarVisita = function (flag) {
    if (flag == "modal")
        $scope.isNovaVisita = true;
    if ($scope.isNovaVisita) {
        $http.post("/visita", $scope.visita)
                .success(function () {
                    if (flag == "cad")
                        $location.path("/Visita/listar");
                    else {
                        novaVisita();
                        $scope.getVisitas();
                    }
                    toastr.success("Visita inserida com sucesso!");
                })
                .error(deuErroSalvar);
    }
    else {
        $http.put("/visita/", $scope.visita)
                .success(function () {
                    $location.path("/Visita/listar");
                    toastr.success("Visita atualizada com sucesso!");
                })
                .error(deuErroAtualizacao);
    }
};
    
    $scope.listarFisicas = function(){
        $http.get('/pessoa/fisica/getfisicas')
                .success(function(data) {
                    $scope.fisicas = data;
                })
                .error(deuErro);
    };
    
    $scope.listarJuridicas = function(){
        $http.get('/pessoa/juridica')
                .success(function(data) {
                    $scope.juridicas = data;
            console.log($scope.juridicas);
                })
                .error(deuErro);
    };

    $scope.dateToData = function (valor) {
            var data = "";
            if (valor != null && valor != "" && valor != undefined) {
                data = ServiceFuncoes.dateToData(valor);
            }
            return data;
        };

}]);
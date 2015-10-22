module.controller("VisitaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {
        $scope.busca = {};
        $scope.placeHolder = "Buscar visita";
        $scope.ent = $rootScope.ent = "visita";
        $scope.campoPrincipal = 'nome';
        $scope.isNovaEstadia = true;

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
    

$scope.atualizarVisitas = function () {
    $http.get("/visita")
            .success(function (data) {
                $scope.visitas = data;
            })
            .error(deuErro);
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

}]);
module.controller("VisitaController", ["$scope", "$http", "$routeParams", "$location", function ($scope, $http, $routeParams, $location) {
	
        function novaVisita() {
            $scope.visita = {
                nome: "",
                quantidadeDeVisitantes: 0,
                responsavel: ""
            };
            $scope.isNovaVisita = true;
        };
        $scope.novaVisita = function () {
            novaVisita();
        };
        $scope.carregarVisita = function () {
            if ($location.path() === "/Visita/nova") {
                novaVisita();
            }
        };
    }]);
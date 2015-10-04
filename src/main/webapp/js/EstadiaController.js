module.controller("EstadiaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {
        $scope.busca = {};
        $scope.placeHolder = "Buscar estadia";
        $scope.ent = $rootScope.ent = "estadia";
        $scope.campoPrincipal = 'familia';
        $scope.isNovaEstadia = true;

//        $scope.atualizarListagens = function (qtdePorPag, pag, campo, string, troca, paro) {
//            if (campo == null || campo == "") {
//                campo = $scope.campoPrincipal;
//            }
//            $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
//            atualizaScope;
//        };

        function atualizaScope() {
            $scope = $rootScope;
        }
        $rootScope.atualizarListagens = $scope.atualizarListagens;

//        $scope.registrosPadrao = function () {
//            $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
//            $rootScope.numregistros = $scope.busca.numregistros;
//        };


    }]);
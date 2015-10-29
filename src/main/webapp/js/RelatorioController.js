module.controller("RelatorioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {
  
  function atualizaScope() {
        $scope = $rootScope;
    }
  
  $scope.gerarRelatorio = function () {
            
            $http.post("/rel", OcorrenciaCompleta)
                    .success(function () {
                        toastr.success("Criado Relatório.", "Salvo");
                        $scope.getOcorrencias();
                    })
                    .error(deuErro);
        };     
        
}]);
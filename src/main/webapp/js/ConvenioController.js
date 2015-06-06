module.controller("ConvenioController", ["$scope", "$http", function ($scope, $http) {

        function deuErro() {
            alert("Algo deu errado. Tente novamente.");
        }
        function novoConvenio() {
            $scope.convenio = {
                nome: ""
            };
            $scope.isNovoConvenio = true;
        }

        $scope.atualizarConvenio = function () {
            $http.get("/convenio")
                    .success(function (data) {
                        $scope.convenio = data;
                    })
                    .error(deuErro);
        };
        $scope.atualizarConvenio();

        $scope.alterarConvenio = function (convenio) {
            $scope.convenio = angular.copy(convenio);
            $scope.isNovoConvenio = false;
        };
}]);
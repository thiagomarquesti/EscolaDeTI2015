module.controller("EtniaController", ["$scope", "$http", function ($scope, $http) {

        function deuErro() {
            alert("Algo deu errado. Tente novamente.");
        }
        function erroLogin() {
            alert("Login ou senha incorretos. Tente novamente.");
        }

        function novaEtnia() {
            $scope.etnia = {
                nome: ""
            };
            $scope.isNovaEtnia = true;
        }

        $scope.atualizarEtnias = function () {
            $http.get("/etnias")
                    .success(function (data) {
                        $scope.etnias = data;
                    })
                    .error(deuErro);
        };
        $scope.atualizarEtnias();

        $scope.alterarEtnia = function (etnia) {
            $scope.etnia = angular.copy(etnia);
            $scope.isNovaEtnia = false;
        };



    }]);
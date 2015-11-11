module.controller("RelatorioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {

        function atualizaScope() {
            $scope = $rootScope;
        }

        $scope.idadeRange = {
            min: 10,
            max: 50,
            ceil: 120,
            floor: 0
        };

        $scope.novoRelIndigena = function () {
            $scope.relatorioindigena = {
                datainicial: "",
                datafinal: "",
                idademax: "",
                idademin: "",
                familia: "",
                etnia: "",
                escolaridade: "",
                estadocivil: "",
                generomasc: "",
                generofem: "",
                terraindigena: "",
                convenio: ""
            };
        };

        $scope.novoRelColaborador = function () {
            $scope.relatoriocolaborador = {
                idademax: "",
                idademin: "",
                generomasc: "",
                generofem: "",
                idfuncao: ""
            };
        };

        $scope.getFamilias = function () {
            $http.get("/familia")
                    .success(function (data) {
                        $scope.familias = data;
                    }).error(deuErro);
        };

        $scope.getEtnias = function () {
            $http.get("/etnia")
                    .success(function (data) {
                        $scope.etnias = "";
                        $scope.etnias = data;
                    })
                    .error(deuErro);
        };

        $scope.getTerras = function () {
            $http.get("/terraIndigena")
                    .success(function (data) {
                        $scope.terras = data;
//                        console.log($scope.terras);
                    })
                    .error(deuErro);
        };

        $scope.getConvenios = function () {
            $http.get("/convenio")
                    .success(function (data) {
//                        console.log(data);
                        $scope.convenios = data;
                    })
                    .error(deuErro);
        };

        $scope.getFuncoes = function () {
            $http.get("/funcao")
                    .success(function (data) {
                        $scope.funcoes = data;
                    })
                    .error(deuErro);
        };

        $scope.formatoSlice = function (value) {
            return value.toString();
        };

        $scope.indigenaPdf = function () {
            $scope.relatorioindigena.idademax = $scope.idadeRange.max;
            $scope.relatorioindigena.idademin = $scope.idadeRange.min;
            console.log($scope.relatorioindigena);
        };

        $scope.gerarRelatorio = function () {

            $http.post("/rel", OcorrenciaCompleta)
                    .success(function () {
                        toastr.success("Criando Relatório.");
                        $scope.getOcorrencias();
                    })
                    .error(deuErro);
        };

        function deuErro() {
            toastr.error("Algo deu errado, tente novamente.", "Ops!");
        }

    }]);
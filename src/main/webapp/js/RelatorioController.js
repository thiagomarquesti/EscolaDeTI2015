module.controller("RelatorioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', '$window', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, $window) {

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
        $scope.novoRelEstadia = function () {
            $scope.relatorioestadia = {
                datainicial: "",
                datafinal: "",
                familia: "",
                etnia: "",
                terraindigena: "",
                representante: ""
            };
        };
        $scope.novoRelVisita = function () {
            $scope.relatoriovisita = {
                datainicial: "",
                datafinal: "",
                entidade: "",
                responsavel: "",
                realizada: ""
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
        $scope.carregarMembros = function (id) {
            if (id) {
                $http.get("/familia/membrosPorFamilia/" + id)
                        .success(function (data) {
                            $scope.membros = data;
                        }).error(deuErro);
            }
        };
        $scope.listarFisicas = function () {
            $http.get('/pessoa/fisica/getFisicas')
                    .success(function (data) {
                        $scope.fisicas = data;
                    })
                    .error(deuErro);
        };
        $scope.listarJuridicas = function () {
            $http.get('/pessoa/juridica')
                    .success(function (data) {
                        $scope.juridicas = data;
                    })
                    .error(deuErro);
        };
        $scope.formatoSlice = function (value) {
            return value.toString();
        };
        $scope.indigenaPdf = function () {
            $scope.relatorioindigena.idademax = $scope.idadeRange.max;
            $scope.relatorioindigena.idademin = $scope.idadeRange.min;

            var parametros = {};
            parametros.generos = ($scope.relatorioindigena.generofem) ? $scope.relatorioindigena.generofem : "";
            if (parametros.generos != "")
                parametros.generos += ($scope.relatorioindigena.generomasc) ? ", " + $scope.relatorioindigena.generomasc : "";
            else
                parametros.generos = ($scope.relatorioindigena.generomasc) ? $scope.relatorioindigena.generomasc : null;
            parametros.dataini = $scope.relatorioindigena.datainicial;
            parametros.datafim = $scope.relatorioindigena.datafinal;
            parametros.familias = ($scope.relatorioindigena.familia) ? $scope.relatorioindigena.familia : null;
            parametros.etnias = ($scope.relatorioindigena.etnia) ? $scope.relatorioindigena.etnia : null;
            parametros.escolaridades = ($scope.relatorioindigena.escolaridade) ? $scope.relatorioindigena.escolaridade : null;
            parametros.estadoscivis = ($scope.relatorioindigena.estadocivil) ? $scope.relatorioindigena.estadocivil : null;
            parametros.idadeini = $scope.idadeRange.min;
            parametros.idadefim = $scope.idadeRange.max;
            parametros.convenios = ($scope.relatorioindigena.convenio)?$scope.relatorioindigena.convenio:null;
            console.log(parametros);
            $http.post("/indigena/relatorio/EXCEL", parametros)
                    .success(function (data) {
                        $window.open(data.url);
                    })
                    .error(deuErro);
        };
        
        $scope.colaboradorPdf = function () {
            $scope.relatoriocolaborador.idademax = $scope.idadeRange.max;
            $scope.relatoriocolaborador.idademin = $scope.idadeRange.min;
            console.log($scope.relatoriocolaborador);
        };
        $scope.estadiaPdf = function () {
            console.log($scope.relatorioestadia);
        };
        $scope.visitaPdf = function () {
            console.log($scope.relatoriovisita);
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
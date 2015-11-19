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

        $scope.escolaridades = [
            {valor: "NENHUM", nome: "NENHUM"},
            {valor: "FUNDAMENTALINCOMPLETO", nome: "Fundamental incompleto"},
            {valor: "FUNDAMENTALCOMPLETO", nome: "Fundamental completo"},
            {valor: "MEDIOINCOMPLETO", nome: "Médio incompleto"},
            {valor: "MEDIOCOMPLETO", nome: "Médio completo"},
            {valor: "SUPERIORINCOMPLETO", nome: "Superior incompleto"},
            {valor: "SUPERIORCOMPLETO", nome: "Superior completo"}
        ];

        $scope.estadosCivil = [
            {valor: "SOLTEIRO", nome: "Solteiro"},
            {valor: "CASADO", nome: "Casado"},
            {valor: "DIVORCIADO", nome: "Divorciado"},
            {valor: "VIUVO", nome: "Viúvo"}
        ];

        $scope.novoRelIndigena = function () {
            $scope.relatorioindigena = {
                dataini: null,
                datafim: null,
                idadefim: "",
                idadeini: "",
                familias: null,
                etnias: null,
                escolaridades: null,
                estadoscivis: null,
                generos: "",
                terrasindigena: null,
                convenios: null
            }
            ;
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
                dataini: null,
                datafim: null,
                familias: null,
                etnias: null,
                terrasindigena: null,
                representantes: null
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
        $scope.indigenaRel = function (tipo) {
            var parametro = {
                dataini: null,
                datafim: null,
                idadefim: $scope.idadeRange.max,
                idadeini: $scope.idadeRange.min,
                familias: null,
                etnias: null,
                escolaridades: null,
                estadoscivis: null,
                generos: null,
                terrasindigena: null,
                convenios: null
            };

            parametro.dataini = ($scope.relatorioindigena.dataini) ? $scope.relatorioindigena.dataini : null;
            parametro.datafim = ($scope.relatorioindigena.datafim) ? $scope.relatorioindigena.datafim : null;

            if ($scope.generofem || $scope.generomasc) {
                parametro.generos = [];
                if ($scope.generofem) {
                    parametro.generos[0] = "FEMININO";
                }

                if ($scope.generomasc) {

                    if (parametro.generos == null) {
                        parametro.generos[0] = "MASCULINO";
                    } else {
                        parametro.generos[1] = "MASCULINO";
                    }
                }
            }
            if ($scope.relatorioindigena.convenios != null && $scope.relatorioindigena.convenios != "") {
                parametro.convenios = [];
                for (x = 0; x < $scope.relatorioindigena.convenios.length; x++) {
                    parametro.convenios[x] = $scope.relatorioindigena.convenios[x].idconvenio;
                }
            } else
                $scope.relatorioindigena.convenios = null;

            if ($scope.relatorioindigena.familias != null && $scope.relatorioindigena.familias != "") {
                parametro.familias = [];
                for (x = 0; x < $scope.relatorioindigena.familias.length; x++) {
                    parametro.familias[x] = $scope.relatorioindigena.familias[x].idfamilia;
                }
            } else
                $scope.relatorioindigena.familias = null;

            if ($scope.relatorioindigena.etnias != null && $scope.relatorioindigena.etnias != "") {
                parametro.etnias = [];
                for (x = 0; x < $scope.relatorioindigena.etnias.length; x++) {
                    parametro.etnias[x] = $scope.relatorioindigena.etnias[x].idetnia;
                }
            } else
                $scope.relatorioindigena.etnias = null;

            if ($scope.relatorioindigena.escolaridades != null && $scope.relatorioindigena.escolaridades != "") {
                parametro.escolaridades = [];
                for (x = 0; x < $scope.relatorioindigena.escolaridades.length; x++) {
                    parametro.escolaridades[x] = $scope.relatorioindigena.escolaridades[x].valor;
                }
            } else
                $scope.relatorioindigena.escolaridades = null;

            if ($scope.relatorioindigena.estadoscivis != null && $scope.relatorioindigena.estadoscivis != "") {
                parametro.estadoscivis = [];
                for (x = 0; x < $scope.relatorioindigena.estadoscivis.length; x++) {
                    parametro.estadoscivis[x] = $scope.relatorioindigena.estadoscivis[x].valor;
                }
            } else
                $scope.relatorioindigena.estadoscivis = null;

            if ($scope.relatorioindigena.terrasindigena != null && $scope.relatorioindigena.terrasindigena != "") {
                parametro.terrasindigena = [];
                for (x = 0; x < $scope.relatorioindigena.terrasindigena.length; x++) {
                    parametro.terrasindigena[x] = $scope.relatorioindigena.terrasindigena[x].idterraindigena;
                }
            } else
                $scope.relatorioindigena.terrasindigena = null;

            console.log(parametro);
            $http.post("/indigena/relatorio/" + tipo, parametro)
                    .success(function (data) {
                        $window.open(data.url);
                    })
                    .error(function (err) {
                        console.log(err);
                    });
        };

        $scope.colaboradorRel = function () {
            $scope.relatoriocolaborador.idademax = $scope.idadeRange.max;
            $scope.relatoriocolaborador.idademin = $scope.idadeRange.min;
            console.log($scope.relatoriocolaborador);
        };
        $scope.estadiaRel = function (tipo) {
            var parametro = {
                dataini: null,
                datafim: null,
                familias: null,
                etnias: null,
                terrasindigena: null,
                representantes: null
            };
            parametro.dataini = ($scope.relatorioestadia.dataini) ? $scope.relatorioestadia.dataini : null;
            parametro.datafim = ($scope.relatorioestadia.datafim) ? $scope.relatorioestadia.datafim : null;

            if ($scope.relatorioestadia.familias != null && $scope.relatorioestadia.familias != "") {
                parametro.familias = [];
                for (x = 0; x < $scope.relatorioestadia.familias.length; x++) {
                    parametro.familias[x] = $scope.relatorioestadia.familias[x].idfamilia;
                }
            } else
                $scope.relatorioestadia.familias = null;

            if ($scope.relatorioestadia.etnias != null && $scope.relatorioestadia.etnias != "") {
                parametro.etnias = [];
                for (x = 0; x < $scope.relatorioestadia.etnias.length; x++) {
                    parametro.etnias[x] = $scope.relatorioestadia.etnias[x].idetnia;
                }
            } else
                $scope.relatorioestadia.etnias = null;

            if ($scope.relatorioestadia.terrasindigena != null && $scope.relatorioestadia.terrasindigena != "") {
                parametro.terrasindigena = [];
                for (x = 0; x < $scope.relatorioestadia.terrasindigena.length; x++) {
                    parametro.terrasindigena[x] = $scope.relatorioestadia.terrasindigena[x].idterraindigena;
                }
            } else
                $scope.relatorioestadia.terrasindigena = null;

            if ($scope.relatorioestadia.representantes != null && $scope.relatorioestadia.representantes != "") {
                parametro.representantes = [];
                for (x = 0; x < $scope.relatorioestadia.representantes.length; x++) {
                    parametro.representantes[x] = $scope.relatorioestadia.representantes[x].idrepresentante;
                }
            } else
                $scope.relatorioestadia.representantes = null;

            console.log(parametro);
            $http.post("/estadia/relatorio/" + tipo, parametro)
                    .success(function (data) {
                        $window.open(data.url);
                    })
                    .error(deuErro);
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

        function dataToDate(valor) {
            var date = new Date(valor);
            var data = date.getFullYear() + "-" + (date.getMonth() + 1) + '-' + date.getDate();
            return data;
        }
    }]);
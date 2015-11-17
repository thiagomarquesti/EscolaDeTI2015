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
                datainicial: null,
                datafinal: null,
                familia: null,
                etnia: null,
                terraindigena: null,
                representante: null
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

            parametro.dataini = $scope.relatorioindigena.dataini;//?dataToDate($scope.relatorioindigena.dataini):null;
            parametro.datafim = ($scope.relatorioindigena.datafim) ? dataToDate($scope.relatorioindigena.datafim) : null;
            parametro.generos = ($scope.generofem) ? "FEMININO" : "";
            if (parametro.generos != "")
                parametro.generos += ($scope.generomasc) ? ", " + "MASCULINO" : "";
            else
                parametro.generos = ($scope.generomasc) ? "MASCULINO" : null;

            if ($scope.relatorioindigena.convenios != null && $scope.relatorioindigena.convenios != "") {
                parametro.convenios = "";
                for (x = 0; x < $scope.relatorioindigena.convenios.length; x++) {
                    if (x == $scope.relatorioindigena.convenios.length - 1) {
                        parametro.convenios += $scope.relatorioindigena.convenios[x].descricao;
                    } else {
                        parametro.convenios += $scope.relatorioindigena.convenios[x].descricao + ",";
                    }
                }
            } else
                $scope.relatorioindigena.convenios = null;

            if ($scope.relatorioindigena.familias != null && $scope.relatorioindigena.familias != "") {
                parametro.familias = "";
                for (x = 0; x < $scope.relatorioindigena.familias.length; x++) {
                    if (x == $scope.relatorioindigena.familias.length - 1) {
                        parametro.familias += $scope.relatorioindigena.familias[x].idfamilia;
                    } else {
                        parametro.familias += $scope.relatorioindigena.familias[x].idfamilia + ",";
                    }
                }
            } else
                $scope.relatorioindigena.familias = null;

            if ($scope.relatorioindigena.etnias != null && $scope.relatorioindigena.etnias != "") {
                parametro.etnias = "";
                for (x = 0; x < $scope.relatorioindigena.etnias.length; x++) {
                    if (x == $scope.relatorioindigena.etnias.length - 1) {
                        parametro.etnias += $scope.relatorioindigena.etnias[x].idetnia;
                    } else {
                        parametro.etnias += $scope.relatorioindigena.etnias[x].idetnia + ",";
                    }
                }
            } else
                $scope.relatorioindigena.etnias = null;

            if ($scope.relatorioindigena.escolaridades != null && $scope.relatorioindigena.escolaridades != "") {
                parametro.escolaridades = "";
                for (x = 0; x < $scope.relatorioindigena.escolaridades.length; x++) {
                    if (x == $scope.relatorioindigena.escolaridades.length - 1) {
                        parametro.escolaridades += $scope.relatorioindigena.escolaridades[x].valor;
                    } else {
                        parametro.escolaridades += $scope.relatorioindigena.escolaridades[x].valor + ",";
                    }
                }
            } else
                $scope.relatorioindigena.escolaridades = null;

            if ($scope.relatorioindigena.estadoscivis != null && $scope.relatorioindigena.estadoscivis != "") {
                parametro.estadoscivis = "";
                for (x = 0; x < $scope.relatorioindigena.estadoscivis.length; x++) {
                    if (x == $scope.relatorioindigena.estadoscivis.length - 1) {
                        parametro.estadoscivis += $scope.relatorioindigena.estadoscivis[x].valor;
                    } else {
                        parametro.estadoscivis += $scope.relatorioindigena.estadoscivis[x].valor + ",";
                    }
                }
            } else
                $scope.relatorioindigena.estadoscivis = null;

            if ($scope.relatorioindigena.terrasindigena != null && $scope.relatorioindigena.terrasindigena != "") {
                parametro.terrasindigena = "";
                for (x = 0; x < $scope.relatorioindigena.terrasindigena.length; x++) {
                    if (x == $scope.relatorioindigena.terrasindigena.length - 1) {
                        parametro.terrasindigena += $scope.relatorioindigena.terrasindigena[x].idterraindigena;
                    } else {
                        parametro.terrasindigena += $scope.relatorioindigena.terrasindigena[x].idterraindigena + ",";
                    }
                }
            } else
                $scope.relatorioindigena.terrasindigena = null;

            console.log(parametro);
            $http.post("/indigena/relatorio/" + tipo, parametro)
                    .success(function (data) {
                        $window.open(data.url);
                    })
                    .error(deuErro);
        };

        $scope.colaboradorRel = function () {
            $scope.relatoriocolaborador.idademax = $scope.idadeRange.max;
            $scope.relatoriocolaborador.idademin = $scope.idadeRange.min;
            console.log($scope.relatoriocolaborador);
        };
        $scope.estadiaRel = function (tipo) {
            console.log($scope.relatorioestadia);
            $http.post("/estadia/relatorio/" + tipo, $scope.relatorioestadia)
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
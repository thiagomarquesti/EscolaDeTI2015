module.controller("ConvenioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar convênio";
        $scope.ent = $rootScope.ent = "convenio";
        $scope.campoPrincipal = 'descricao';

        $scope.atualizarListagens = function (qtdePorPag, pag, campo, string, troca, paro) {
            if (campo == null || campo == "") {
                campo = $scope.campoPrincipal;
            }
            $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
            atualizaScope;
        };

        function atualizaScope() {
            $scope = $rootScope;
        }

        $rootScope.atualizarListagens = $scope.atualizarListagens;

        $scope.registrosPadrao = function () {
            $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
            $rootScope.numregistros = $scope.busca.numregistros;
        };

        $scope.fazPesquisa = function (registros, string) {
            $rootScope.string = string;
            $scope.atualizarListagens(registros, 1, $scope.campoAtual, string, $rootScope.ent, 0, false);
        };

        function novoConvenio() {
            $scope.convenio = {
                nome: ""
            };
            $scope.isNovoConvenio = true;
        }

        $scope.novoConvenio = function () {
            novoConvenio();
        };

        $scope.carregarConvenio = function () {
            if ($location.path() === "/Convenio/novo") {
                novoConvenio();
            }
            else {
                $http.get("/convenio/" + $routeParams.id)
                        .success(function (data) {
                            console.log(data);
                            $scope.convenios = data;
                            $scope.isNovoConvenio = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.atualizarConvenios = function () {
            $http.get("/convenio")
                    .success(function (data) {
                        $scope.convenios = data;
                    })
                    .error(deuErro);
        };

        $scope.editarConvenio = function (convenio) {
            $location.path("/Convenio/editar/" + convenio.idconvenio);
        };

        $scope.deletarConvenio = function (convenio) {
            $http.delete("/convenio/" + convenio.idconvenio)
                    .success(function () {
                        toastr.success("Convênio " + convenio.descricao + " excluído com sucesso.");
                        $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoPrincipal, '', '', $rootScope.ent, false);
                    }).error(deuErroDeletar);
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function deuErroAtualizacao() {
            toastr.error("Atenção, erro ao tentar editar o convênio, verifique!");
        }

        function deuErroSalvar() {
            toastr.error("Atenção, erro ao tentar salvar o convênio, verifique!");
        }

        function deuErroDeletar() {
            toastr.error("Atenção, erro ao tentar deletar o convênio, verifique!");
        }
        $scope.salvarConvenio = function (flag) {
            if (flag == "modal")
                $scope.isNovoConvenio = true;
            if ($scope.isNovoConvenio) {
                $http.post("/convenio", $scope.convenio)
                        .success(function () {
                            if (flag == "cad")
                                $location.path("/Convenio/listar");
                            else {
                                novoConvenio();
                                $scope.getConvenios();
                            }
                            toastr.success("Convênio inserido com sucesso!");
                        })
                        .error(deuErroSalvar);
            }
            else {
                $http.put("/convenio/", $scope.convenio)
                        .success(function () {
                            $location.path("/Convenio/listar");
                            toastr.success("Convênio atualizado com sucesso!");
                        })
                        .error(deuErroAtualizacao);
            }
        };
    }]);

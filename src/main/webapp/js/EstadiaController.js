module.controller("EstadiaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {
        $scope.busca = {};
        $scope.placeHolder = "Buscar estadia";
        $scope.ent = $rootScope.ent = "estadia";
        $scope.campoPrincipal = 'nomefamilia';
        $scope.isNovaEstadia = true;

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

        function novaEstadia() {
            $scope.estadia = {
                idestadia: "",
                dataentrada: "",
                datasaida: "",
                observacoesentrada: "",
                observacoessaida: "",
                familia: "",
                representante: "",
                membros: []
            };
            $scope.isNovaEstadia = true;
        }

        $scope.saidaEstadiaNova = function () {
            console.log("asdasd");
            $scope.saida = {
                idestadia: "",
                datasaida: "",
                observacoessaida: ""
            };
        };

        $scope.novaEstadia = function () {
            novaEstadia();
        };

        $scope.carregarEstadia = function () {
            if ($location.path() === "/Estadia/nova") {
                novaEstadia();
            } else {
                $http.get("/estadia/obj/" + $routeParams.id)
                        .success(function (data) {
                            novaEstadia();
                            $scope.estadia.idestadia = $routeParams.id;
                            var d = new Date(data.dataentrada);
                            $scope.estadia.dataentrada = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                            d = (data.datasaida) ? new Date(data.datasaida) : "";
                            console.log(data.datasaida);
                            $scope.estadia.datasaida = (data.datasaida) ? new Date(d.getTime() + (d.getTimezoneOffset() * 60000)) : "";
                            console.log($scope.estadia.datasaida);
                            $scope.estadia.observacoesentrada = data.observacoesentrada;
                            $scope.estadia.observacoessaida = data.observacoessaida;
                            $scope.estadia.familia = data.familia.idfamilia;
                            $scope.carregarMembros($scope.estadia.familia);
                            getSelects($routeParams.id);
                            $scope.estadia.representante.telefone = data.representante.telefone.telefone;

                            $scope.isNovaEstadia = false;
                        })
                        .error(deuErro);
            }
        };

        function getSelects(id) {
            $http.get("/estadia/getRepresentante/" + id).success(function (data) {
                $scope.estadia.representante = data;
            }).error(deuErro);

            $http.get("/estadia/getMembros/" + id).success(function (data) {
                $scope.estadia.membros = data;
            }).error(deuErro);

        }

        $scope.salvarDataSaida = function () {
            $scope.saida.idestadia = $routeParams.id;
            console.log($scope.saida);
            $http.post("/estadia/saida", $scope.saida)
                    .success(function () {
                        toastr.success("Atualizado com sucesso!");
                    })
                    .error(deuErroSalvar);
        };

        $scope.atualizarEstadia = function () {
            $http.get("/estadia")
                    .success(function (data) {
                        $scope.estadia = data;
                    })
                    .error(deuErro);
        };

        $scope.editarEstadia = function (idestadia) {
            $location.path("/Estadia/editar/" + idestadia);
        };

        $scope.dateToData = function (valor) {

            var date = new Date(valor);
            date = new Date(date.getTime() + (date.getTimezoneOffset() * 60000));
            var dia = (date.getDate() < 10) ? "0" + date.getDate() : date.getDate();
            var data = dia + "/" + (((date.getMonth() + 1) < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + date.getFullYear();
            return (valor != null) ? data : "";
        };


        $scope.salvarEstadia = function () {
            var estadiaCorreto = {
                idestadia: $scope.estadia.idestadia,
                dataentrada: $scope.estadia.dataentrada,
                datasaida: $scope.estadia.datasaida,
                observacoesentrada: $scope.estadia.observacoesentrada,
                observacoessaida: $scope.estadia.observacoessaida,
                familia: {idfamilia: $scope.estadia.familia},
                representante: $scope.estadia.representante,
                membros: $scope.estadia.membros
            };
            if ($scope.isNovaEstadia) {
                console.log($scope.estadia);
                console.log($scope.estadia.datasaida);
                $http.post("/estadia", estadiaCorreto)
                        .success(function () {
                            $location.path("/Estadia/listar");
                            toastr.success("Estadia inserido com sucesso!");
                        })
                        .error(deuErroSalvar);
            } else {
                $http.put("/estadia", estadiaCorreto)
                        .success(function () {
                            $location.path("/Estadia/listar");
                            toastr.success("Estadia inserido com sucesso!");
                        })
                        .error(deuErroSalvar);
            }
        };

        $scope.deletarEstadia = function (estadia) {
            $http.delete("/estadia/" + estadia.idestadia)
                    .success(function () {
                        toastr.success("Estadia " + estadia.idestadia + " excluído com sucesso.");
                        $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoPrincipal, '', '', $rootScope.ent, false);
                    }).error(deuErroDeletar);
        };

        $scope.carregarFamilias = function () {
            $http.get("/familia")
                    .success(function (data) {
                        $scope.familia = data;
                    }).error(deuErro);
        };

        $scope.carregarMembros = function (id) {
            $http.get("/familia/membrosPorFamilia/" + id)
                    .success(function (data) {
                        $scope.itens = data;
                    }).error(deuErro);
        };

        function dataToDate(valor) {
            var date = new Date(valor);
            var data = date.getFullYear() + "-" + (date.getMonth() + 1) + '-' + date.getDate();
            return data;
        }

        $scope.getIdEstadia = function (idestadia) {
            $routeParams.id = idestadia;
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function deuErroAtualizacao() {
            toastr.error("Atenção, erro ao tentar editar a estadia, verifique!");
        }

        function deuErroSalvar() {
            toastr.error("Atenção, erro ao tentar salvar a estadia, verifique!");
        }

    }]);
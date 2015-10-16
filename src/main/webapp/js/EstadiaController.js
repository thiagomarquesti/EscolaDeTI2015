module.controller("EstadiaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {
        $scope.busca = {};
        $scope.placeHolder = "Buscar estadia";
        $scope.ent = $rootScope.ent = "estadia";
        $scope.campoPrincipal = 'familia';
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
                observacoessaida:"",
                idfamilia:"",
                membros:[]
            };
            $scope.isNovaEstadia = true;
        }

        $scope.novaEstadia = function () {
            novaEstadia();
        };

        $scope.carregarEstadia = function () {
            if ($location.path() === "/Estadia/nova") {
                novaEstadia();
            }
            else {
                $http.get("/estadia/" + $routeParams.id)
                        .success(function (data) {
                            $scope.estadia = data[0];
                            $scope.isNovaEstadia = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.atualizarEstadia = function () {
            $http.get("/estadia")
                    .success(function (data) {
                        $scope.estadia = data;
                    })
                    .error(deuErro);
        };

        $scope.editarEstadia = function (estadia) {
            $location.path("/Estadia/editar/" + estadia.idestadia);
        };

        $scope.dateToData = function (valor) {
            var date = new Date(valor);
            var dia = (date.getDate()<10)?"0"+date.getDate():date.getDate();
            var data = dia + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
            return data;
        };


        $scope.salvarEstadia = function () {
            var estadiaCorreto = {
                idestadia: $scope.estadia.idestadia,
                dataentrada: $scope.estadia.dataentrada,
                datasaida: $scope.estadia.datasaida,
                observacoesentrada: $scope.estadia.observacoesentrada,
                observacoessaida:$scope.estadia.observacoessaida,
                idfamilia:"",
                membros: $scope.familia.membros
            };
            console.log(estadiaCorreto);
              $http.post("/estadia", estadiaCorreto)
                    .success(function () {
                        $location.path("/Estadia/listar");
                        toastr.success("Estadia inserido com sucesso!");
                    })
                    .error(deuErroSalvar);

        };

        $scope.deletarEstadia = function (estadia) {
            $http.delete("/estadia/" + estadia.idestadia)
                    .success(function () {
                        toastr.success("Estadia " + estadia.idestadia + " excluído com sucesso.");
                        $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoPrincipal, '', '', $rootScope.ent, false);
                    }).error(deuErroDeletar);
        };

        function dataToDate(valor) {
            var date = new Date(valor);
            var data = date.getFullYear() + "-" + (date.getMonth() + 1) + '-' + date.getDate();
            return data;
        }

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
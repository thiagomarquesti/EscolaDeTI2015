module.controller("ConvenioController", ["$scope", "$http", "$routeParams", "$location", function ($scope, $http, $routeParams, $location) {

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

        function novoConvenio() {
            $scope.convenio = {
                nome: ""
            };
            $scope.isNovoConvenio = true;
        }

        $scope.novoConvenio = function(){
            novoConvenio();
        }
        
        $scope.carregarConvenio = function () {
            if ($location.path() === "/Convenio/novo") {
                novoConvenio();
            }
            else {
                $http.get("/convenio/" + $routeParams.id)
                        .success(function (data) {
                            $scope.convenio = data[0];
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
        }

        $scope.deletarConvenio = function (convenio) {
            $http.delete("/convenio/" + convenio.idconvenio)
                    .success(function () {
                        toastr.success("Convênio " + convenio.descricao + " excluído com sucesso.");
                        $scope.atualizarConvenios();
                    }).error(deuErroDeletar);
        }

        $scope.salvarConvenio = function () {
            if ($scope.isNovoConvenio) {
                $http.post("/convenio", $scope.convenio)
                        .success(function () {
                            $location.path("/Convenio/listar");
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
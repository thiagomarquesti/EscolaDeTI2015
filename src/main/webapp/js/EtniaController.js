module.controller("EtniaController", ["$scope", "$http", "$routeParams", "$location", function ($scope, $http, $routeParams, $location) {


        $scope.etnias = [];

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function novaEtnia() {
            $scope.etnia = {
                descricao: ""
            };
            $scope.isNovaEtnia = true;
        }

        $scope.novaEtnia = function () {
            novaEtnia();
        };

        $scope.carregarEtnia = function () {
            if ($location.path() === "/Etnia/nova") {
                novaEtnia();
            }
            else {
                $http.get("/etnia/" + $routeParams.id)
                        .success(function (data) {
                            console.log(data);
                            $scope.etnia = data;
                            $scope.isNovaEtnia = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.todasEtnias = function () {
            $http.get("/etnia")
                    .success(function (data) {
                        $scope.etnias = data;
                    })
                    .error(deuErro);
        };

        $scope.atualizarEtnias = function (reg, pag, campo, order, string, paro) {
            if (reg == null || reg == "") {
                reg = 10;
            }
            if (pag == null || pag == "") {
                pag = 1;
            }
            if (campo == null || campo == "") {
                campo = "descricao";
            }
            if (order != "asc" && order != "desc") {
                order = "asc";
            }
            if (string == null) {
                string = "";
            }
//      if(order == "desc"){ $scope.tipoOrdem == true; } else { $scope.tipoOrdem == false; }
            $http.get("/etnia/listar/" + reg + "/" + pag + "/" + campo + "/" + order + "/" + string)
                    .success(function (data) {
                        $scope.etnias = data;
                        console.log(data);
                        console.log("/etnia/listar/" + pag + "/" + campo + "/" + order + "/" + string);

                        if (!paro) {
                            atualizaPaginacao(data.quantidadeDePaginas, pag, campo, order, string, false);
                        }

                    })
                    .error(deuErro);
        };

        $scope.trocaOrdem = function (string) {
            if ($scope.tipoOrdem == true) {
                $scope.tipoOrdem = false;
                var ordem = "asc";
            }
            else {
                $scope.tipoOrdem = true;
                var ordem = "desc";
            }
            $scope.atualizarEtnias("", "", ordem, string, true);
        };

        function atualizaPaginacao(qtde, pag, campo, order, string, paro) {
            $('#paginacao').bootpag({
                total: qtde,
                page: pag,
                maxVisible: 5
            }).on('page', function (event, num) {
                paro = true;
                $scope.atualizarEtnias(10, num, campo, order, string, paro);

            });
        }

        $scope.editarEtnia = function (etnia) {
            $location.path("/Etnia/editar/" + etnia.idetnia);
        };

        $scope.deletarEtnia = function (etnia) {
            $http.delete("/etnia/" + etnia.idetnia)
                    .success(function (status) {
                        toastr.success("Etnia " + etnia.descricao + " deletada com sucesso.");
                        $scope.atualizarEtnias();
                    })
                    .error(deuErro);
        };

        $scope.salvarEtnia = function (flag) {
            if (flag == "modal")
                $scope.isNovaEtnia = true;
            if ($scope.isNovaEtnia) {
                $http.post("/etnia", $scope.etnia)
                        .success(function () {
                            console.log(flag);
                            if (flag == "cad")
                                $location.path("/Etnia/listar");
                            else {
                                novaEtnia();
                                $scope.getEtnias();
                            }
                            toastr.success("Etnia inserida com sucesso!");
                        })
                        .error(deuErro);
            }
            else {
                $http.put("/etnia/", $scope.etnia)
                        .success(function () {
                            console.log(flag);
//                            if (flag == "cad")
                            $location.path("/Etnia/listar");
                            toastr.success("Etnia atualizada com sucesso!");
                        })
                        .error(deuErro);
            }

        };
    }]);
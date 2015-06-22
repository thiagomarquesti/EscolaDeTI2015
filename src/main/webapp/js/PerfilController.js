module.controller("PerfilController", ["$scope", "$http", "$routeParams", "$location", function ($scope, $http, $routeParams, $location, $element, $attrs) {
        function novoPerfil() {
            $scope.perfil = {
                nome: "",
                itens: []
            };
            $scope.isNovo = true;
        }


        $scope.carregar = function () {
            $scope.itensAcesso();
            if ($location.path() === "/Perfil/novo") {
                novoPerfil();
            }
            else {
                $http.get("/perfildeacesso/" + $routeParams.idperfilacesso)
                        .success(function (data) {
                            $scope.perfil = data[0];
                            $scope.isNovo = false;
                            $("#itensselecionados").delay(30000).select2();
                            $("#itensselecionados").select2().val();
                        })
                        .error(deuErro);
            }

            $("select").select2();

        };


        $scope.salvar = function () {
            if ($scope.isNovo) {
                $http.post("/perfildeacesso", createJsonPerfil($scope.isNovo))
                        .success(function () {
                            toastr.success("Perfil cadastrado com sucesso!");
                            $location.path("/Perfil/listar");
                        })
                        .error(deuErro);
            }
            else {
                $http.put("/perfildeacesso/", createJsonPerfil($scope.isNovo))
                        .success(function () {
                            toastr.success("Perfil atualizado com sucesso!");
                            $location.path("/Perfil/listar");
                        })
                        .error(deuErro);
            }

        };

        $scope.atualizar = function () {
            $http.get("/perfildeacesso")
                    .success(function (data) {
                        $scope.perfis = data;
                    })
                    .error(deuErro);
        };

        $scope.itensAcesso = function () {
            $http.get("/itemacesso")
                    .success(function (data) {
                        //console.log(data) 
                        $scope.itens = data;
                    })
                    .error(deuErro);
            if ($scope.isNovo == false) {
                $http.get("/perfildeacesso/itensdeacesso/" + $routeParams.idperfildeacesso)
                        .success(function (data) {
                            //console.log(data) 
                            $scope.itensDoPerfil = data;
                            $("#itensselecionados").select().val();
                        })
                        .error(function () {
                            toastr.error("TESTE");
                        });
                //.error(deuErro);
            }
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        $scope.editar = function (perfil) {
            $location.path("/Perfil/editar/" + perfil.idperfilacesso);
        };

        $scope.excluir = function (perfil) {
            $http.delete("/perfildeacesso/" + perfil.idperfilacesso).success(function () {
                toastr.success("O Perfil foi excluido com sucesso", "Perfil Excluido");
                $scope.atualizar();
            }).error(function () {
                toastr.error("Não foi possível excluir o perfil", "Houve um erro");
            });
        };

        $scope.selected = function (itemId) {
            var itens = $scope.itensDoPerfil;
            retorno = false;
            for (i = 0; i < itens.length; i++) {
                if (itens[i].iditemacesso === itemId) {
                    retorno = true;
                }
            }
            return retorno;
        };

        function createJsonPerfil(novo) {

            var perfil = "" + $(".itemAcesso").val();
            if (perfil === "null") {
                perfil = "";
            }
            if (novo) {
                perfil = '{"nome": "' + $scope.perfil.nome + '" , "itens": [' + perfil + ']};';
            } else {
                perfil = '{"idperfildeacesso":' + $scope.perfil.idperfildeacesso + ', "nome": "' + $scope.perfil.nome + '", "itens": [' + perfil + ']};';
            }
            return perfil;
        }

    }]);



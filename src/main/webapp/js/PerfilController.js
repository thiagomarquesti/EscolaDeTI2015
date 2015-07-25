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
                $http.get("/perfildeacesso/" + $routeParams.id)
                        .success(function (data) {
                            $scope.perfil = data;
                            $scope.isNovo = false;
//                            $("#itensselecionados").delay(30000).select2();
//                            $("#itensselecionados").select2().val();
                        })
                        .error(deuErro);
            }


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
                $http.get("/perfildeacesso/itensdeacesso/" + $routeParams.id)
                        .success(function (data) {
                            //console.log(data) 
                            $scope.itensDoPerfil = data;
//                            $("#itensselecionados").select2().val();
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
            $location.path("/Perfil/editar/" + perfil.idperfildeacesso);
        };

        $scope.excluir = function (perfil) {
            $http.delete("/perfildeacesso/" + perfil.idperfildeacesso).success(function () {
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
            console.log($scope.perfil.itensselecionados);
            
//            var perfil = "" + $(".itemAcesso").val();
//            if (perfil === "null") {
//                perfil = "";
//            }
            if (novo) {
                console.log('1');
                perfil = '{"nome": "' + $scope.perfil.nome + 
                          ', "itens": ' + $scope.perfil.itensselecionados + '}';
            } else {
                console.log('2');
//                if ($scope.perfil.itensselecionados.length() == 0){
//                    perfil = '{"idperfildeacesso":' + $scope.perfil.idperfildeacesso + 
//                            ', "nome": "' + $scope.perfil.nome + 
//                            '}';
//                }
//                else{
//                    alert($scope.perfil.itensselecionados.length());
//                    for (var i = 0, max = $scope.perfil.itensselecionados.length(); i < max; i++) {
//                        itens = '{ "iditemacesso": ' + $scope.perfil.itensselecionados.iditemacesso + 
//                                            ', "nome": ' + $scope.perfil.itensselecionados.nome +
//                                            ', "rota": ' + $scope.perfil.itensselecionados.rota +
//                                            ', "icone": ' + $scope.perfil.itensselecionados.icone +
//                                            ', "superior_id": ' + $scope.perfil.itensselecionados.superior_id + '}';
//                    };
//                    alert(itens);
                    perfil = '{"idperfildeacesso":' + $scope.perfil.idperfildeacesso + 
                            ', "nome": "' + $scope.perfil.nome + 
                            ', "itens": ' + $scope.perfil.itensselecionados + 
                                '}';
                    //alert(perfil);
//                }
            }
            console.log(perfil);
            return perfil;
        }

    }]);



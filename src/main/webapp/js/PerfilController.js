module.controller("PerfilController", ["$scope", "$http", "$routeParams", "$location", function ($scope, $http, $routeParams, $location, $element, $attrs) {
        function novoPerfil() {
            $scope.perfil = {
                nome: "",
                itens: []
            };
            $scope.isNovo = true;
        }
        
        
        $scope.carregar = function () {
            //$("#itensselecionados").select2();
//            angular.element($("#itensselecionados").select2());
            //console.log(999);
            //console.log(angular.element(document.querySelector('#itensselecionados').innerHTML));
//            console.log($("#itensselecionados").select());
            console.log($("#itensselecionados").select().val());
            //$("#itensselecionados".itemAcesso).select2();
            if ($location.path() === "/Perfil/novo") {
                novoPerfil();
                //$("select").select2('val', 'All');
//                $("select").select2();
            }
            else {
                $http.get("/perfildeacesso/" + $routeParams.id)
                        .success(function (data) {
                            $scope.perfil = data[0];
                            $scope.isNovo = false;
//                            $("select").select2(data);
                        })
                        .error(deuErro);
            }
            
//            sleep(1000);
            $("select").select2();
            console.log($("#itensselecionados").select2().val());

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
            if (!$scope.isNovo) {
                $http.get("/perfildeacesso/itensdeacesso/" + $routeParams.id)
                        .success(function (data) {
                            //console.log(data) 
                            $scope.itensDoPerfil = data;
                            //alert(data.val());
                        })
                        .error(function () {
                            toastr.error("TESTE");
                        })
                //.error(deuErro);
            }
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        $scope.editar = function (perfil) {
            $location.path("/Perfil/editar/" + perfil.id);
        };

        $scope.excluir = function (perfil) {
            $http.delete("/perfildeacesso/" + perfil.id).success(function () {
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
                if (itens[i].id === itemId) {
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
                perfil = '{"id":' + $scope.perfil.id + ', "nome": "' + $scope.perfil.nome + '", "itens": [' + perfil + ']};';
            }
            return perfil;
        }

    }]);



module.controller("FuncaoController", ["$scope", "$http", "$routeParams", "$location", function ($scope, $http, $routeParams, $location) {

    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.");
    }

    function novaFuncao() {
        $scope.funcao = {
            descricao: ""
        };
        $scope.isNovaFuncao = true;
    }

    $scope.novaFuncao = function () {
        novaFuncao();
    }

    $scope.carregarFuncao = function () {
        if ($location.path() === "/Funcao/nova") {
            novaFuncao();
        }
        else {
            $http.get("/funcao/" + $routeParams.id)
                    .success(function (data) {
                        $scope.funcao = data[0];
                        $scope.isNovaFuncao = false;
                    })
                    .error(deuErro);
        }
    };

    $scope.atualizarFuncoes = function () {
        $http.get("/funcao")
                .success(function (data) {
                    $scope.funcoes = data;
                })
                .error(deuErro);
    };

    $scope.editarFuncao = function (funcao) {
        $location.path("/Funcao/editar/" + funcao.idfuncao);
        console.log(funcao.idfuncao);
    };

    $scope.deletarFuncao = function (funcao) {
        $http.delete("/funcao/" + funcao.idfuncao)
                .success(function (status) {
                    toastr.success("Funcao " + funcao.descricao + " deletada com sucesso.");
                    $scope.atualizarFuncoes();
                })
                .error(deuErro);
    };

    $scope.salvarFuncao = function () {


        $http.get("/funcao//verificarDescricao/" + $scope.funcao.descricao)
                .success(function (data) {
                    console.log(data);
                    if (data == false) {
                        toastr.error("Já existe uma função cadastrada com esse nome!");
                        $scope.funcao.descricao = "";
                        if(!$scope.isNovaFuncao){
                           $location.path("/Funcao/listar"); 
                           toastr.info("Não foram feitas modificações!");
                        }
                    }
                    else {
                        if ($scope.isNovaFuncao) {
                            $http.post("/funcao", $scope.funcao)
                                    .success(function () {
                                        $location.path("/Funcao/listar");
                                        toastr.success("Funcao inserida com sucesso!");
                                    })
                                    .error(deuErro);
                        }
                        else {
                            $http.put("/funcao/", $scope.funcao)
                                    .success(function () {
                                        $location.path("/Funcao/listar");
                                        toastr.success("Funcao atualizada com sucesso!");
                                    })
                                    .error(deuErro);
                        }
                    }
                }
                ).error(deuErro);



    };
}]);
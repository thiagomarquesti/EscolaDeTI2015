module.controller("PerfilController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {
        
    $scope.busca = {};
    $scope.placeHolder = "Buscar perfil";
    $scope.ent = $rootScope.ent = "perfildeacesso";
    $scope.campoPrincipal = 'nome';
    $rootScope.tipoOrdem = 'asc';
        
    $scope.atualizarListagens = function(qtdePorPag, pag, campo, string, troca, paro){
        if (campo == null || campo == "") { campo = $scope.campoPrincipal; }
        $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
        atualizaScope;
    };
    
    function atualizaScope() {
        $scope = $rootScope;
    }
    
    $rootScope.atualizarListagens = $scope.atualizarListagens;
    
    $scope.registrosPadrao = function() {
        $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
        $rootScope.numregistros = $scope.busca.numregistros;
    };
    
    $scope.fazPesquisa = function(registros, string){
        $rootScope.string = string;
        $scope.atualizarListagens(registros, 1, $scope.campoAtual, string, $rootScope.ent, 0, false);
    };
        
    $scope.excluir = function (perfil) {
        $http.delete("/perfildeacesso/" + perfil.idperfildeacesso).success(function () {
            toastr.success("O Perfil " + perfil.nome + " foi deletado com sucesso", "Perfil Excluído");
            $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoPrincipal,'', '', false);
        }).error(function () {
            toastr.error("Não foi possível excluir o perfil", "Houve um erro");
        });
    };    
        
    function novoPerfil() {
        $scope.perfil = {
            nome: "",
            itens: []
        };
        $scope.isNovo = true;
    }

    $scope.carregar = function () {
        //$scope.itensAcesso();
        if ($location.path() === "/Perfil/novo") {
            novoPerfil();
        }
        else {
            $http.get("/perfildeacesso/" + $routeParams.id)
                    .success(function (data) {
                        $scope.perfil = data[0];
                        $scope.isNovo = false;
                        $scope.itensAcesso();
                        //console.log(data);
                    })
                    .error(deuErro);
        }
    };

    $scope.salvar = function () {
        if ($scope.isNovo) {
            $http.post("/perfildeacesso/salvar", createJsonPerfil($scope.isNovo))
                .success(function () {
                    toastr.success("Perfil cadastrado com sucesso!");
                    $location.path("/Perfil/listar");
                })
                .error(deuErro);
        }
        else {
            $http.put("/perfildeacesso/alterar", createJsonPerfil($scope.isNovo))
                .success(function () {
                    toastr.success("Perfil atualizado com sucesso!");
                    $location.path("/Perfil/listar");
                })
                .error(deuErro);
        }
    };

    
        
    $scope.itensAcesso = function () {
        $http.get("/itemacesso")
                .success(function (data) {
                    //console.log(data) 
                    $scope.itens = data;
                })
                .error(deuErro);
        if ($scope.isNovo === false) {
            $http.get("/perfildeacesso/itensdeacesso/" + $routeParams.id)
                    .success(function (data) {
                        $scope.perfil.itensselecionados = data; //carrega itens gravados
                        //console.log(data);
                    })
                    .error(function () {
                        toastr.error(deuErro);
                    });
        }
    };

    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.");
    }

    $scope.editar = function (perfil) {
        $location.path("/Perfil/editar/" + perfil.idperfildeacesso);
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
        //console.log($scope.perfil.itensselecionados);
        var it = $scope.perfil.itensselecionados;
        var itens = "[";
        for (var i = 0; i < it.length; i++) {
            if (i === (it.length - 1)) {
                itens = itens + '{"iditemacesso":"' + it[i].iditemacesso + '","rota":"' + it[i].rota + '","nome":"' + it[i].nome + '","icone":"' + it[i].icone + '","superior_id":' + it[i].superior_id + '}]';
            } else {
                itens = itens + '{"iditemacesso":"' + it[i].iditemacesso + '","rota":"' + it[i].rota + '","nome":"' + it[i].nome + '","icone":"' + it[i].icone + '","superior_id":' + it[i].superior_id + '},';
            }
        }

        if (novo) {
            perfil = '{"nome": "' + $scope.perfil.nome + '"' +
                    ', "itens": ' + itens + ' }';
        } else {
            perfil = '{"idperfil":' + $scope.perfil.idperfildeacesso +
                    ', "nome": "' + $scope.perfil.nome + '"' +
                    ', "itens": ' + itens + '}';
//                alert($scope.perfil.itensselecionados);
//                }
        }
        //console.log(perfil);
        return perfil;
    }

}]);



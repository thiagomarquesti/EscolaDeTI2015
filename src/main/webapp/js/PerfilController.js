module.controller("PerfilController", ["$scope", "$http", "$routeParams", "$location", function ($scope, $http, $routeParams, $location, $element, $attrs) {
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

    $scope.atualizarPerfis = function (pag,campo,order,string, paro) {
        if(pag == null || pag == ""){ pag = 1; }
        if(campo == null || campo == ""){ campo = "nome"; }
        if(order != "asc" && order != "desc"){ order = "asc"; }
        if(string == null){ string = ""; }

        $http.get("/perfildeacesso/listar/"+pag+"/"+campo+"/"+order+"/"+string)
            .success(function (data) {
                $scope.perfis = data;
                console.log(data);
                console.log("/perfildeacesso/listar/"+pag+"/"+campo+"/"+order+"/"+string);

                if (!paro) { atualizaPaginacao(data.quantidadeDePaginas, pag, campo, order, string, false); }
                
            })
            .error(deuErro);
    };
    
    $scope.trocaOrdem = function(string){
        if($scope.tipoOrdem == true){
            $scope.tipoOrdem = false;
            var ordem = "asc";
        }
        else {
            $scope.tipoOrdem = true;
            var ordem = "desc";
        }
        $scope.atualizarPerfis("","", ordem ,string, true);
    };
    
    function atualizaPaginacao(qtde, pag, campo, order, string, paro){
        $('#paginacao').bootpag({
            total: qtde,
            page: pag,
            maxVisible:5
        }).on('page', function(event, num){
            paro = true;
            $scope.atualizarPerfis(num, campo, order, string, paro);
            
        });
    }
        
        
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

        $scope.excluir = function (perfil) {
            $http.delete("/perfildeacesso/" + perfil.idperfildeacesso).success(function () {
                toastr.success("O Perfil " + perfil.nome + " foi deletado com sucesso", "Perfil Excluído");
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
                console.log('1');
                perfil = '{"nome": "' + $scope.perfil.nome + '"' +
                        ', "itens": ' + itens + ' }';
            } else {
                console.log('2');
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



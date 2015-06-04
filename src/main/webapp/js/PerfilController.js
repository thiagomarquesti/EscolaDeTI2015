module.controller("PerfilController", ["$scope", "$http", "$routeParams", "$location", function ($scope, $http, $routeParams, $location) {

        function novoPerfil() {
            $scope.perfil = {
                nome: "",
                itens: []
            };
            $scope.isNovo = true;
        }
        
        
        $scope.carregar = function () {
            
            if ($location.path() === "/Perfil/novo") {
                novoPerfil();
            }
            else {
                $http.get("/perfildeacesso/" + $routeParams.id)
                        .success(function (data) {
                            $scope.perfil = data[0];
                            $scope.isNovo = false;
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
            if (!$scope.isNovo) {
                $http.get("/perfildeacesso/itensdeacesso/" + $routeParams.id)
                        .success(function (data) {
                            //console.log(data) 
                            $scope.itensDoPerfil = data;
                            //alert(data.val());
                        })
                        .error(deuErro);
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

//            var perfil = "" + $(".itemAcesso").val();
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
        
  $scope.disabled = undefined;
  $scope.searchEnabled = undefined;

  $scope.enable = function() {
    $scope.disabled = false;
  };

  $scope.disable = function() {
    $scope.disabled = true;
  };

  $scope.enableSearch = function() {
    $scope.searchEnabled = true;
  };

  $scope.disableSearch = function() {
    $scope.searchEnabled = false;
  };

  $scope.clear = function() {
    $scope.person.selected = undefined;
    $scope.address.selected = undefined;
    $scope.country.selected = undefined;
  };

  $scope.someGroupFn = function (item){

    if (item.name[0] >= 'A' && item.name[0] <= 'M')
        return 'From A - M';

    if (item.name[0] >= 'N' && item.name[0] <= 'Z')
        return 'From N - Z';

  };

  $scope.counter = 0;
  $scope.someFunction = function (item, model){
    $scope.counter++;
    $scope.eventResult = {item: item, model: model};
  };

  $scope.availableColors = $scope.itens;

  $scope.multipleDemo = {};
  $scope.multipleDemo.colors = $scope.itensDoPerfil;

  $scope.address = {};
  $scope.refreshAddresses = function(address) {
    var params = {address: address, sensor: false};
    return $http.get(
      'http://maps.googleapis.com/maps/api/geocode/json',
      {params: params}
    ).then(function(response) {
      $scope.addresses = response.data.results;
    });
  };
    }]);



module.controller("TerraController", ["$scope", "$http", "$routeParams", "$location", function($scope, $http, $routeParams, $location) {

    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.");
    }

    function novaTerra() {
        $scope.terra = {
            nometerra: "",
            cidade: ""
        };
        $scope.isNovaTerra = true;
    }
    
    $scope.novaTerra = function() {
        novaTerra();
    };
    
    $scope.todasCidades = function(){
        $http.get("/cidade")
            .success(function(data){
                //console.log(data);
                $scope.cidades = data;
            })
            .error(deuErro);
    };
    
    $scope.carregarTerra = function(){
        if($location.path() === "/TerraIndigena/nova"){
            novaTerra();
        }
        else {
            $http.get("/terraIndigena/obj/" + $routeParams.id)
                    .success(function(data){
                        $scope.terra = data;
                        $scope.terra.cidade = {
                            codigoibge: data.cidade.codigoIBGE,
                            nomecidade: data.cidade.descricao,
                            codigoestado: data.cidade.estado.codigoestado,
                            descricao: data.cidade.estado.descricao,
                            sigla: data.cidade.estado.sigla,
                        }   
                        $scope.isNovaTerra = false;
                    })
                    .error(deuErro);
        }
    };
    
    $scope.todasTerras = function(){
        $http.get("/terraIndigena")
            .success(function (data) {
                $scope.terras = data;
                console.log($scope.terras);
            })
            .error(deuErro);
    };
    
    $scope.editarTerra = function(terra) {
        $location.path("/TerraIndigena/editar/" + terra.idterraindigena);
    };
    
    $scope.deletarTerra = function (terra) {
        $http.delete("/terraIndigena/" + terra.idterraindigena)
                .success(function () {
                    toastr.success("Terra indígena "+ terra.nometerra +" deletada com sucesso.");
                    $scope.atualizarTerras();
                })
                .error(deuErro);
    };

    $scope.salvarTerra = function () {
        if($scope.terra.cidade == ""){
            toastr.error("A cidade não foi preenchida corretamente.","Atenção");
        }
        else{
            console.log($scope.terra.cidade);
            var terraCompleta = {
                nometerra: $scope.terra.nometerra,
                cidade: {
                    codigoIBGE: $scope.terra.cidade.codigoibge,
                    descricao: $scope.terra.cidade.nomecidade,
                    estado: {
                        codigoestado: $scope.terra.cidade.codigoestado,
                        descricao: $scope.terra.cidade.descricao,
                        sigla: $scope.terra.cidade.sigla
                    }
                }
            };
            console.log(terraCompleta);
            if ($scope.isNovaTerra) {
                $http.post("/terraIndigena", terraCompleta)
                        .success(function () {
                            $location.path("/TerraIndigena/listar");
                            toastr.success("Terra indígena inserida com sucesso!");
                        })
                        .error(deuErro);
            }
            else {
                terraCompleta.idterraindigena = $scope.terra.idterraindigena;
                $http.put("/terraIndigena", terraCompleta)
                        .success(function () {
                            $location.path("/TerraIndigena/listar");
                            toastr.success("Terra indígena atualizada com sucesso!");
                        })
                        .error(deuErro);
            }
        }
    };
    
    $scope.atualizarTerras = function (pag,campo,order,string, paro) {
        if(pag == null || pag == ""){ pag = 1; }
        if(campo == null || campo == ""){ campo = "nometerra"; }
        if(order != "asc" && order != "desc"){ order = "asc"; }
        if(string == null){ string = ""; }
//      if(order == "desc"){ $scope.tipoOrdem == true; } else { $scope.tipoOrdem == false; }
        $http.get("/terraIndigena/listar/"+pag+"/"+campo+"/"+order+"/"+string)
            .success(function (data) {
                $scope.terras = data;
                if (!paro) { atualizaPaginacao(data.quantidadeDePaginas, pag, campo, order, string, false); }
            })
            .error(deuErro);
    };

    $scope.trocaOrdem = function(campo, string){
        var ordem;
        if($scope.tipoOrdem == true){
            $scope.tipoOrdem = false;
            ordem = "asc";
        }
        else {
            $scope.tipoOrdem = true;
            ordem = "desc";
        }
        $scope.campoAtual = campo;
        $scope.atualizarTerras("",campo, ordem ,string, true);
    };
    
    function atualizaPaginacao(qtde, pag, campo, order, string, paro){
        $('#paginacao').bootpag({
            total: qtde,
            page: pag,
            maxVisible:5
        }).on('page', function(event, num){
            paro = true;
            $scope.atualizarTerras(num, campo, order, string, paro);
        });
    }
    
}]);
module.controller("TerraController", ["$scope", "$http", "$routeParams", "$location", function($scope, $http, $routeParams, $location) {

    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.");
    }

    function novaTerra() {
        $scope.terra = {
            nomeTerra: "",
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
            $http.get("/terraIndigena/" + $routeParams.id)
                    .success(function(data){
                        $scope.terra = data[0];
                        $scope.isNovaTerra = false;
                    })
                    .error(deuErro);
        }
    };
    
    $scope.todasTerras = function(){
        $http.get("/terraIndigena")
            .success(function (data) {
                $scope.terras = data;
                //console.log($scope.terras);
            })
            .error(deuErro);
    };
    
    $scope.editarTerra = function(terra) {
        $location.path("/TerraIndigena/editar/" + terra.idTerraIndigena);
    };
    
    $scope.deletarTerra = function (terra) {
        $http.delete("/terraIndigena/" + terra.idTerraIndigena)
                .success(function (status) {
                    toastr.success("Terra "+ terra.nomeTerra +" deletada com sucesso.");
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
            if ($scope.isNovaTerra) {
                $http.post("/terraIndigena", $scope.terra)
                        .success(function () {
                            $location.path("/TerraIndigena/listar");
                            toastr.success("Terra indígena inserida com sucesso!");
                        })
                        .error(deuErro);
            }
            else {
                $http.put("/terraIndigena/", $scope.terra)
                        .success(function () {
                            $location.path("/TerraIndigena/listar");
                            toastr.success("Terra indígena atualizada com sucesso!");
                        })
                        .error(deuErro);
            }
        }
    };
}]);
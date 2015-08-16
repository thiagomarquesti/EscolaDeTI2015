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
            $http.get("/terraIndigena/obj/" + $routeParams.id)
                    .success(function(data){
                        $scope.terra = data;
                        console.log(data);
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
                .success(function (status) {
                    toastr.success("Terra indígena "+ terra.nometerra +" deletada com sucesso.");
                    $scope.todasTerras();
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
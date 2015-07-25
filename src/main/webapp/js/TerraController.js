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
    
    $scope.carregarTerra = function(){
        if($location.path() === "/TerraIndigena/nova"){
            novaTerra();
        }
        else {
            $http.get("/terra/" + $routeParams.id)
                    .success(function(data){
                        $scope.terra = data[0];
                        $scope.isNovaTerra = false;
                    })
                    .error(deuErro);
        }
    };
    
    $scope.atualizarTerras = function () {
        $http.get("/terra")
                .success(function (data) {
                    $scope.terras = data;
                })
                .error(deuErro);
    };
    
    $scope.editarTerra = function(terra) {
        $location.path("/TerraIndigena/editar/" + terra.idTerraIndigena);
    };
    
    $scope.deletarTerra = function (terra) {
        $http.delete("/terra/" + terra.idTerraIndigena)
                .success(function (status) {
                    toastr.success("Terra "+ terra.nomeTerra +" deletada com sucesso.");
                    $scope.atualizarTerras();
                })
                .error(deuErro);
    };

    $scope.salvarTerra = function () {
        if ($scope.isNovaTerra) {
            $http.post("/terra", $scope.terra)
                    .success(function () {
                        $location.path("/TerraIndigena/listar");
                        toastr.success("Terra indígena inserida com sucesso!");
                    })
                    .error(deuErro);
        }
        else {
            $http.put("/terra/", $scope.terra)
                    .success(function () {
                        $location.path("/TerraIndigena/listar");
                        toastr.success("Terra indígena atualizada com sucesso!");
                    })
                    .error(deuErro);
        }
        
    };
}]);
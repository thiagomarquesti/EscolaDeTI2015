module.controller("EtniaController", ["$scope", "$http", function ($scope, $http) {

    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.");
    }

    function novaEtnia() {
        $scope.etnia = {
            descricao: ""
        };
        $scope.isNovaEtnia = true;
    }
    
    $scope.novaEtnia = function() {
        novaEtnia();
    }
    
    $scope.atualizarEtnias = function () {
        $http.get("/etnia")
                .success(function (data) {
                    $scope.etnias = data;
                })
                .error(deuErro);
    };
    

    $scope.alterarEtnia = function (etnia) {
        $scope.etnia = angular.copy(etnia);
        $scope.isNovaEtnia = false;
    };

    $scope.editarEtnia = function (etnia) {
        $http.get("/etnia/" + etnia.idetnia)
                .success(function (data) {
                    $scope.etnia = data[0];
                    $scope.isNovaEtnia = false;
                    $scope.atualizarEtnias();
                })
                .error(deuErro);
    };
    
    $scope.deletarEtnia = function (etnia) {
        $http.delete("/etnia/" + etnia.idetnia)
                .success(function (status) {
                    toastr.success("Etnia "+ etnia.descricao +" deletada com sucesso.");
                    $scope.atualizarEtnias();
                })
                .error(deuErro);
    };

    $scope.salvarEtnia = function () {
        if ($scope.isNovaEtnia) {
            $http.post("/etnia", $scope.etnia)
                    .success(function () {
                        toastr.success("Etnia inserida com sucesso!");
                        reset('formEtnia');
                    })
                    .error(deuErro);
        }
        else {
            $http.put("/etnia/", $scope.etnia)
                    .success(function () {
                        toastr.success("Etnia atualizada com sucesso!");
                        reset('formEtnia');
                    })
                    .error(deuErro);
        }
        
        $scope.atualizarEtnias();
        
    };
    
    function reset(form) {
        if(form) {
          $scope.form.$setPristine();
          $scope.form.$setUntouched();
        }
        novaEtnia();
        
        $scope.isNovaEtnia = true;
    };


}]);
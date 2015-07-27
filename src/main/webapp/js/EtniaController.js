module.controller("EtniaController", ["$scope", "$http", "$routeParams", "$location", function($scope, $http, $routeParams, $location) {

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
    
    $scope.carregarEtnia = function(){
        if($location.path() === "/Etnia/nova"){
            novaEtnia();
        }
        else {
            $http.get("/etnia/" + $routeParams.id)
                    .success(function(data){
                        $scope.etnia = data[0];
                        $scope.isNovaEtnia = false;
                    })
                    .error(deuErro);
        }
    };
    
    $scope.todasEtnias = function(){
        $http.get("/etnia")
            .success(function (data) {
                $scope.etnias = data;
            })
            .error(deuErro);
    };
    
    $scope.atualizarEtnias = function (pag,campo,string) {
        if(pag == null || pag == ""){ pag = 1; }
        if(campo == null || campo == ""){ campo = "descricao"; }
        var order = $scope.tipoOrdem; 
        if(string == null){ string = ""; }
        $http.get("/etnia/listar/"+pag+"/"+campo+"/"+order+"/"+string)
            .success(function (data) {
                $scope.etnias = data;
                console.log(data);
                console.log("/etnia/listar/"+pag+"/"+campo+"/"+order+"/"+string);
                
                $('#paginacao').bootpag({
                    total: data.quantidadeDePaginas,
                    page: pag,
                    maxVisible:5,
                });
                if(!$scope.paro){
                    $('#paginacao').bootpag().on('page', function(event, num){
                        $scope.atualizarEtnias(num, campo, string);
                        $scope.paro = true;
                    });
            }
            })
            .error(deuErro);
    };
    
    $scope.inverteTipo = function(){
       if($scope.tipoOrdem != "asc"){
            $scope.tipoOrdem = "asc";
        }
        else {
            $scope.tipoOrdem = "desc";
        } 
    };
    
    $scope.editarEtnia = function(etnia) {
        $location.path("/Etnia/editar/" + etnia.idetnia);
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
                        $location.path("/Etnia/listar");
                        toastr.success("Etnia inserida com sucesso!");
                    })
                    .error(deuErro);
        }
        else {
            $http.put("/etnia/", $scope.etnia)
                    .success(function () {
                        $location.path("/Etnia/listar");
                        toastr.success("Etnia atualizada com sucesso!");
                    })
                    .error(deuErro);
        }
        
    };
}]);
module.controller("OcorrenciaController", ["$scope", "$http", "$routeParams", function ($scope, $http, $routeParams) {
        function novaOcorrencia() {
            $scope.ocorrencia = {
                dataOcorrencia: "",
                dataBloqueio:"",
                descricao:""
            };
        }
        
        $scope.carregaOcorrencia = function (){
            novaOcorrencia();
        };
        
        $scope.getOcorrencias = function (){
            var oco = {
                dataOcorrencia: "12/05/2015",
                dataBloqueio:"12/06/2015",
                descricao:"Briga"
            };
//            $http.get("/asdasda").success(function (data){
                $scope.ocorrencias = oco;
//            }).error(deuErro);
        };
        
        $scope.deletarOcorrencia = function (){
          $http.delete();  
        };
        
        $scope.salvarOcorrencia = function (){
            $http.post();
        };
        
        function deuErro(){
            toastr.error("Deu erro!");
        }
}]);

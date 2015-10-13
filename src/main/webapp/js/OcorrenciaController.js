//module.controller("OcorrenciaController", ["$scope", "$http", "$routeParams", function ($scope, $http, $routeParams) {
//        function novaOcorrencia() {
//            $scope.ocorrencia = {
//                dataOcorrencia: "",
//                dataBloqueio:"",
//                descricao:""
//            };
//        }
//        
//        $scope.carregarOcorrencia = function (){
//            novaOcorrencia();
//        };
//        
//        $scope.getOcorrencias = function (){
//            $http.get("/ocorrencia").success(function (data){
//                $scope.ocorrencias = data;
//            }).error(deuErro);
//        };
//        
//        $scope.deletarOcorrencia = function (idOcorrencia){
//          $http.delete("/ocorrencia/"+idOcorrencia);  
//        };
//        
//        $scope.salvarOcorrencia = function (){
//            console.log($scope.ocorrencia);
//            $http.post("/ocorrencia",$scope.ocorrencia);
//            console.log($scope.ocorrencia + "deuc certo");
//        };
//        
//}]);

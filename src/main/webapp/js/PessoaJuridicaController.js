module.controller("OcorrenciaController", ["$scope", "$http", "$routeParams", function ($scope, $http, $routeParams) {
        function novaJuridica() {
            $scope.juridica = {
                nome: "",
                telefones: [{
                        telefone: ""
                    }, {
                        telefone: ""
                    }],
                email: {
                    email: ""
                },
                logradouro: "",
                numero: "",
                bairro: "",
                complemento: "",
                cep: "",
                codigoibge: "",
                codigoestado: "",
                cnpj: {
                    cnpj: ""
                },
                tipo: "JURIDICA"
            };
            $scope.isNovaJuridica = true;
        }

        $scope.carregar = function () {
            if ($location.path() === "/Juridica/novo") {
                novaJuridica();
            }
            else {
                $http.get("/juridica/obj/" + $routeParams.id).success(function (data) {
                    $scope.juridica = data;
                }).error(deuErro());
            }
        };
        
        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }
    }]);


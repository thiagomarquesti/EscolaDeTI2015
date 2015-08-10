module.controller("IndigenaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function ($scope, $http, $routeParams, $location, $timeout) {

        function novoIndio() {
            $scope.isNovoIndio = true;
            $scope.indio = {
                nome: "",
                cpf: "",
                etnia: "",
                genero: "",
                dataNascimento: "",
                conveniosselecionados:[],
                telefone: "",
                terraIndigena: "",
                escolaridade: "",
                estadoCivil: "",
                codigoSUS: ""

            };
        }

        $scope.salvarIndio = function () {
            $scope.indio.cpf = arrumaCPF($scope.indio.cpf);
            alert($scope.indio.cpf);
            $scope.indio.dataNascimento = dataToDate($scope.indio.dataNascimento);
            if ($scope.isNovoIndio) {

                $http.post("/indigena", $scope.indio)
                        .success(function () {
                            toastr.success("Indígena cadastrado com sucesso!");
                            $location.path("/Indigena/listar");
                        })
                        .error(deuErro);
            }
            else {
                $http.put("/indigena/", $scope.indio)
                        .success(function () {
                            toastr.success("Indígena atualizado com sucesso!");
                            $location.path("/Indigena/listar");
                        })
                        .error(deuErro);
            }

        };

        $scope.atualizarIndio = function () {
            $http.get("/indigena")
                    .success(function (data) {
                        $scope.indio = data;
                    })
                    .error(deuErro);
        };
       
        function arrumaCPF(arruma) {
            var str = arruma.replace(".", "");
            str = str.replace("-", "");
        }

        function dataToDate(dados) {
            var data = dados.substring(6, 10) + "-" + dados.substring(3, 5) + "-" + dados.substring(0, 2);
            return data;
        }


        $scope.editarIndio = function (indio) {
            $location.path("/Indigena/editar/" + indio.id);
        };

        $scope.carregarIndios = function () {
            if ($location.path() === "/Indigena/novo") {
                novoIndio();
            }
            else {
                $http.get("/indigena/" + $routeParams.id)
                        .success(function (data) {
                            $scope.indio = data[0];
                            $scope.isNovoIndio = false;
                        })
                        .error(deuErro);
            }
        };
        
        $scope.reset = function (form) {
            if (form) {
                form.$setPristine();
                form.$setUntouched();
            }
            novoIndio();
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        $scope.carregaScript = function (nScript) {
            $timeout(function () {
                var script = document.createElement('script');
                script.src = nScript + ".js";
                document.getElementsByTagName('head')[0].appendChild(script);
            }, 100);
        };

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        $scope.carregaScript = function (nScript) {
            $timeout(function () {
                var script = document.createElement('script');
                script.src = nScript + ".js";
                document.getElementsByTagName('head')[0].appendChild(script);
            }, 100);
        };

    }]);

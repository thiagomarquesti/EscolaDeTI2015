module.controller("IndigenaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function ($scope, $http, $routeParams, $location, $timeout) {

        function novoIndio() {
            $scope.indio = {
                nome: "",
                cpf: {
                    cpf: ""
                },
                etnia: {
                    idetnia: "",
                    descricao: ""
                },
                genero: "",
                dataNascimento: "",
                convenio: [
                    {
                        idconvenio: "",
                        descricao: ""
                    }
                ],
                telefone: {
                    telefone: ""
                },
                terraIndigena: {
                    nomeTerra: "",
                    cidade: {
                        codigoIBGE: "",
                        descricao: "",
                        estado: {
                            codigoestado: "",
                            descricao: "",
                            sigla: ""
                        }
                    }
                },
                escolaridade: "",
                estadoCivil: "",
                codigoSUS: ""

            };
            $scope.isNovoIndio = true;
        }

        $scope.salvarIndio = function () {
            $scope.indio.cpf.cpf = arrumaCPF($scope.indio.cpf.cpf);
            alert($scope.indio.cpf.cpf);
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

        function arrumaCPF(arruma) {
            var str = arruma.replace(".", "");
            str = str.replace("-", "");
        }

        function dataToDate(dados) {
            var data = dados.substring(6, 10) + "-" + dados.substring(3, 5) + "-" + dados.substring(0, 2);
            return data;
        }

        $scope.atualizarIndio = function () {
            $http.get("/indigena")
                    .success(function (data) {
                        $scope.indio = data;
                    })
                    .error(deuErro);
        };

        $scope.editarIndio = function (indio) {
            $location.path("/Indigena/editar/" + indio.id);
        };

        $scope.carregarIndios = function () {
            $scope.convenios();
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

        $scope.convenios = function () {
            $http.get("/convenio")
                    .success(function (data) {
//                        console.log(data) 
                        $scope.itens = data;
                    })
                    .error(deuErro);
//            if ($scope.isNovo == false) {
//                $http.get("/perfildeacesso/itensdeacesso/" + $routeParams.id)
//                        .success(function (data) {
//                            //console.log(data) 
//                            $scope.itensDoPerfil = data;
//                        })
//                        .error(function () {
//                            toastr.error("TESTE");
//                        });
//                //.error(deuErro);
//            }
        };
    }]);

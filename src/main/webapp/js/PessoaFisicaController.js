module.controller("PessoaFisicaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar pessoa";
        $scope.ent = $rootScope.ent = "fisica";
        $scope.campoPrincipal = 'nome';
        $rootScope.tipoOrdem = 'asc';

        $scope.atualizarListagens = function (qtdePorPag, pag, campo, string, troca, paro) {
            if (campo == null || campo == "") {
                campo = $scope.campoPrincipal;
            }
            $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
            atualizaScope;
        };

        function atualizaScope() {
            $scope = $rootScope;
        }

        $rootScope.atualizarListagens = $scope.atualizarListagens;

        $scope.registrosPadrao = function () {
            $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
            $rootScope.numregistros = $scope.busca.numregistros;
        };

        $scope.fazPesquisa = function (registros, string) {
            $rootScope.string = string;
            $scope.atualizarListagens(registros, 1, $scope.campoAtual, string, $rootScope.ent, 0, false);
        };

        function novaPessoaFisica() {
            $scope.fisica = {
                nome: "",
                telefones: [{
                        telefone: ""
                    }, {
                        telefone: ""
                    }],
                email: {
                    email: ""
                },
                idfuncao: "",
                logradouro: "",
                numero: "",
                bairro: "",
                complemento: "",
                cep: "",
                codigoibge: "",
                codigoestado: "",
                datanasc: "",
                cpf: {
                    cpf: ""
                },
                genero: "",
                tipo: "USUÁRIO",
                imgSrc: ""
            };
            $scope.isNovo = true;
        }

        function criarPessoaFisicaParaEditar() {
            $scope.fisica = {
                nome: "",
                telefones: [{
                        telefone: ""
                    }, {
                        telefone: ""
                    }],
                email: {
                    email: ""
                },
                idfuncao: "",
                logradouro: "",
                numero: "",
                bairro: "",
                complemento: "",
                cep: "",
                codigoibge: "",
                codigoestado: "",
                datanasc: "",
                cpf: {
                    cpf: ""
                },
                genero: "",
                tipo: "USUÁRIO",
                imgSrc: ""
            };
        }

        $scope.salvar = function () {
            if ($scope.isNovo) {
                console.log($scope.fisica);
                $http.post("/pessoa/fisica", $scope.fisica)
                        .success(function () {
                            toastr.success("Usuário cadastrado com sucesso!");
                            if ($location.path() === "/Fisica/nova") {
                                $location.path("/Pessoa/listar");
                            }
                            else {
                                window.location.href = "/login.html";
                            }
                        })
                        .error(deuErro);
            }
            else {
                console.log($scope.fisica);
                $scope.fisica.idpessoa = $routeParams.id;
                console.log($scope.fisica);
                $http.put("/pessoa/fisica", $scope.fisica)
                        .success(function () {
                            toastr.success("Pessoa atualizado com sucesso!");
                            $location.path("/Pessoa/listar");
                        })
                        .error(deuErro);
            }
        };


        $scope.editar = function (aId) {
            $location.path("/Fisica/editar/" + aId);
        };

        $scope.carregar = function () {
            if ($location.path() === "/Fisica/nova") {
                novaPessoaFisica();
            }
            else {
                $timeout(function () {
                    $http.get("/pessoa/fisica/obj/" + $routeParams.id)
                            .success(function (data) {
                                criarPessoaFisicaParaEditar();
                                $scope.fisica.idpessoa = $routeParams.id;
                                $scope.fisica.nome = data.nome;
                                $scope.fisica.cpf.cpf = (data.cpf != null) ? data.cpf.cpf : "";
                                $scope.fisica.genero = data.genero;
                                $scope.fisica.email.email = data.email.email;
                                var d = new Date(data.datanascimento);
                                $scope.fisica.datanasc = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                                $scope.fisica.cep = data.endereco.cep;
                                $scope.fisica.idfuncao = (data.funcao != null) ? data.funcao.idfuncao : "";
                                if (data.endereco != null && data.endereco.cidade != null && data.endereco.cidade.estado != null) {
                                    $scope.fisica.codigoestado = data.endereco.cidade.estado.codigoestado;
                                    $scope.fisica.codigoibge = data.endereco.cidade.codigoIBGE;
                                } else {
                                    $scope.fisica.codigoestado = "";
                                    $scope.fisica.codigoibge = "";
                                }
                                $scope.listarCidades();
                                $scope.fisica.logradouro = data.endereco.logradouro;
                                $scope.fisica.numero = data.endereco.numero;
                                $scope.fisica.complemento = data.endereco.complemento;
                                $scope.fisica.bairro = data.endereco.bairro;
                                $scope.fisica.telefones[0].telefone = (data.telefones[0] != undefined) ? data.telefones[0].telefone : "";
                                if (data.telefones[1] != undefined) {
                                    $scope.fisica.telefones[1].telefone = data.telefones[1].telefone;
                                }
                                $scope.fisica.tipo = data.tipo;
                                $scope.fisica.imgSrc = data.imgSrc;

                                $scope.isNovo = false;
                            }).error(deuErro);
                }, 100);
            }
        };

        $scope.reset = function (form) {
            if (form) {
                form.$setPristine();
                form.$setUntouched();
            }
            novaPessoaFisica()();
        };

        $scope.listarEstados = function () {
            $http.get("/uf/listar").success(function (data) {
                $scope.estados = data.listaDeRegistros;
//                console.log(data);
//                console.log($scope.estados.codigoestado);
            }).error(deuErro);
        };

        $scope.listarCidades = function () {
            if ($scope.fisica.codigoestado !== "" && $scope.fisica.codigoestado !== undefined) {
//                console.log($scope.fisica.codigoestado);
                $http.get("/cidade/listarPorCodigoEstado/" + $scope.fisica.codigoestado).success(function (data) {
                    $scope.cidades = data;
                }).error(deuErro);
            }
        };

        $scope.cidadeSelecionada = function (codigo) {
            if (codigo == $scope.fisica.codigoibge) {
                return true;
            } else {
                return false;
            }
        };

        function foto(id) {
            $http.get("/foto/user/" + id)
                    .success(function (data) {
                        $scope.urlFoto = data.foto;
                    }).error(deuErro);
        }
        ;

        $scope.webcamFoto = function () {
            $(document).ready(function () {
                canvas = document.getElementById('imgCanvas');
                $scope.fisica.imgSrc = canvas.src;
            });
            console.log($scope.fisica.imgSrc);
        };

        /*   SCRIPTS PARA CARREGAR OPTIONS DOS SELECTS    */
        $scope.getFuncoes = function () {
            $http.get("/funcao")
                    .success(function (data) {
                        $scope.funcoes = data;
                    })
                    .error(deuErro);
        };
        /*   FIM DOS SCRIPST   */
//-----------------AKI-------------------------------

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



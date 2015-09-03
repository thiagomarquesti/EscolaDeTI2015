module.controller("UsuarioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function ($scope, $http, $routeParams, $location, $timeout) {

        function novoUsuario() {
            $scope.usuario = {
                nome: "",
                telefones: [{
                        telefone: ""
                    },{
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
                login: "",
                senha: {
                    senha: ""
                },
                perfis: [],
                tipo: "USUÁRIO",
                status: "ATIVO"
            };
            $scope.isNovo = true;
        }

        function criarUsuarioParaEditar() {
            $scope.usuario = {
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
                status: ""
            };
        }

        function novoUsuarioAdmin() {
            $scope.usuario = {
                nome: "",
                email: "",
                login: "",
                senha: "",
                tipo: "USUÁRIO",
                status: "ATIVO"
            };
            $scope.isNovo = true;
        }
 
        $scope.novoAdmin = function () {
            novoUsuarioAdmin();
        };

        $scope.verificaLogado = function () {
            $http.get("/login/usuariologado")
                    .success(function (data) {
                        if (!data.idusuario) {
                            window.location.href = "/login.html";
                        }
                        else {
                            $scope.nomeUsuario = data.nome;
                            $scope.idUsuario = data.idusuario;
                        }
                    })
                    .error(function () {
                        window.location.href = "/login.html";
                    });
        };

        function removerMascara(aCampo) {
            console.log(aCampo);
            if (aCampo !== "" && aCampo !== null) {
                words = /\^|~|\?|,|\*|\.|\-|\(|\)/g;
                aCampo = aCampo.replace(words, "");
            }
            return aCampo;
        }
        ;

        $scope.salvar = function () {
            if ($scope.isNovo) {
                console.log($scope.usuario);
                $http.post("/usuario", $scope.usuario)
                        .success(function () {
                            toastr.success("Usuário cadastrado com sucesso!");
                            if ($location.path() === "/Usuario/novo") {
                                $location.path("/Usuario/listar");
                            }
                            else {
                                window.location.href = "/login.html";
                            }
                        })
                        .error(deuErro);
            }
            else {
                $scope.usuario.idpessoa = $routeParams.id;
                console.log($scope.usuario);
                $http.put("/usuario", $scope.usuario)
                        .success(function () {
                            toastr.success("Usuário atualizado com sucesso!");
                            $location.path("/Usuario/listar");
                        })
                        .error(deuErro);
            }
        };

        $scope.atualizar = function () {
            $http.get("/usuario")
                    .success(function (data) {
                        $scope.usuarios = data;
                    })
                    .error(deuErro);
        };

        $scope.editar = function (aId) {
            $location.path("/Usuario/editar/" + aId);
        };

        $scope.alteraStatus = function (id) {
            $http.put("/usuario/trocarStatusUsuario/" + id)
                    .success(function () {
                        $scope.atualizar();
                    })
                    .error(deuErro);
        };

        $scope.statusArray = {"ATIVO": "Acesso Liberado", "INATIVO": "Acesso Bloqueado", "": "Sem acesso"};
        $scope.corStatus = {"ATIVO": "success", "INATIVO": "danger", "": "info"};

        $scope.carregar = function () {
            if ($location.path() === "/Usuario/novo") {
                novoUsuario();
            }
            else {
                $timeout(function () {
                    $http.get("/usuario/obj/" + $routeParams.id)
                            .success(function (data) {
                                criarUsuarioParaEditar();

                                console.log(data);
                                $scope.usuario.nome = data.nome;
                                $scope.usuario.cpf.cpf = data.cpf.cpf;
                                $scope.usuario.genero = data.genero;
                                $scope.usuario.email.email = data.email.email;
                                var d = new Date(data.datanascimento);
                                $scope.usuario.datanasc = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                                $scope.usuario.cep = data.endereco.cep;
                                $scope.usuario.idfuncao = data.funcao.idfuncao;
                                $scope.usuario.codigoestado = data.endereco.cidade.estado.codigoestado;
                                $scope.listarCidades();
                                $scope.usuario.codigoibge = data.endereco.cidade.codigoIBGE;
                                $scope.usuario.logradouro = data.endereco.logradouro;
                                $scope.usuario.numero = data.endereco.numero;
                                $scope.usuario.complemento = data.endereco.complemento;
                                $scope.usuario.bairro = data.endereco.bairro;
                                $scope.usuario.telefones[0].telefone = data.telefones[0].telefone;
                                console.log(data.telefones[1].telefone);
                                $scope.usuario.telefones[1].telefone = data.telefones[1].telefone;
//                                if (data.telefones[0].telefone != "")
//                                    $scope.usuario.telefones[1].telefone = data.telefones[0].telefone;
                                $scope.usuario.status = data.status;
                                $scope.usuario.tipo = data.tipo;


//                                $scope.usuario.rsenha = $scope.usuario.senha;

                                $scope.isNovo = false;
                            }).error(deuErro);
                }, 100);
            }
        };

        $scope.callateinteval = function () {
            console.log("intervalo");
        };

        $scope.reset = function (form) {
            if (form) {
                form.$setPristine();
                form.$setUntouched();
            }
            novoUsuario();
        };

        $scope.listarEstados = function () {
            $http.get("/uf").success(function (data) {
                $scope.estados = data;
//                console.log(data);
//                console.log($scope.estados.codigoestado);
            }).error(deuErro);
        };

        $scope.listarCidades = function () {
            if ($scope.usuario.codigoestado !== "" && $scope.usuario.codigoestado !== undefined) {
                console.log($scope.usuario.codigoestado);
                $http.get("/cidade/listarPorCodigoEstado/" + $scope.usuario.codigoestado).success(function (data) {
                    $scope.cidades = data;
                }).error(deuErro);
            }
        };

        $scope.cidadeSelecionada = function (codigo) {
            if(codigo == $scope.usuario.codigoibge){
                return true;
            }else{
                return false;
            }
        };

        $scope.logout = function () {
            $http.get("/login/usuariologado")
                    .success(function (data) {
                        console.log(data.login);
                        var dadosLogin = {"login": data.login, "senha": data.senha};
                        $http.post("/login/efetuarlogout", dadosLogin)
                                .success(function () {
                                    window.location.href = "/login.html";
                                }
                                )
                                .error(deuErro);
                    })
                    .error(deuErro);
        };


        $scope.carregaitensAcesso = function () {
            $http.get("/login/usuariologado/itensdeacesso")
                    .success(function (data) {
                        $scope.itensAcesso = data;
//                console.log(data);
                        //alert("funcionou");
                    })
                    .error(erroListarItensAcessoDoMenu);
        };

        $scope.carregaPerfis = function () {
            $http.get("/perfildeacesso")
                    .success(function (data) {
                        $scope.perfis = data;
                    }).error(deuErro);
        };

//-----------------AKI-------------------------------
        function erroListarItensAcessoDoMenu() {
            alert("Atenção, erro ao subir os itens de acesso do usuário! Entre em contato com o Administrador!!");
        }


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

module.controller("UsuarioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope) {

    $scope.busca = {};
    $scope.placeHolder = "Buscar usuário/colaborador";
    $scope.ent = $rootScope.ent = "usuario";
    $scope.campoPrincipal = 'nome';
    $rootScope.tipoOrdem = 'asc';
        
    $scope.atualizarListagens = function(qtdePorPag, pag, campo, string, troca, paro){
        if (campo == null || campo == "") { campo = $scope.campoPrincipal; }
        $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
        atualizaScope;
    };
    
    function atualizaScope() {
        $scope = $rootScope;
    }
    
    $rootScope.atualizarListagens = $scope.atualizarListagens;
    
    $scope.registrosPadrao = function() {
        $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
        $rootScope.numregistros = $scope.busca.numregistros;
    };
    
    $scope.fazPesquisa = function(registros, string){
        $rootScope.string = string;
        $scope.atualizarListagens(registros, 1, $scope.campoAtual, string, $rootScope.ent, 0, false);
    };

    function novoUsuario() {
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
            login: "",
            senha: {
                senha: ""
            },
            perfis: [],
            tipo: "USUÁRIO",
            status: "ATIVO",
            imgSrc: ""
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
            status: "",
            imgSrc: ""
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
                        foto(data.idusuario);
                    }
                })
                .error(function () {
                    window.location.href = "/login.html";
                });
    };

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
            console.log($scope.usuario);
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


    $scope.editar = function (aId) {
        $location.path("/Usuario/editar/" + aId);
    };

    $scope.alteraStatus = function (id) {
        $http.put("/usuario/trocarStatusUsuario/" + id)
                .success(function () {
                    $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoPrincipal,'', '', false);
                })
                .error(deuErro);
    };

    $scope.statusArray = {"ATIVO": "Acesso Liberado", "INATIVO": "Acesso Bloqueado", "": "Sem acesso"};
    $scope.corStatus = {"ATIVO": "success", "INATIVO": "danger", "": "info"};

    $scope.validarLogin = function () {
        $scope.erroNoLogin = false;
        if ($scope.usuario.login.length !== 0) {
            $http.get('/usuario/verificarLogin/' + $scope.usuario.login).success(function (data) {
                $scope.existeLogin = data;
                $scope.erroNoLogin = ($scope.maxLogin || !$scope.existeLogin || $scope.minLogin);
                $scope.validarSenha();
                $scope.formCad.$valid = ((!$scope.erroNoLogin && !$scope.erroNaSenha));
            });

            if ($scope.usuario.login.length > 20) {
                $scope.maxLogin = true;
            } else {
                $scope.maxLogin = false;
            }

            if ($scope.usuario.login.length < 3) {
                $scope.minLogin = true;
            } else {
                $scope.minLogin = false;
            }
            $scope.erroNoLogin = ($scope.maxLogin || !$scope.existeLogin || $scope.minLogin);
            $scope.formCad.$valid = ((!$scope.erroNoLogin && !$scope.erroNaSenha));
//                        (!$scope.erroNoLogin || !($scope.senha.senha.length !== 0));
        } else {
            $scope.usuario.senha.senha = "";
            $scope.usuario.rsenha = "";

            $scope.formCad.$valid = true;
        }

    };

    $scope.validarSenha = function () {
        $scope.erroNaSenha = false;
        if ($scope.usuario.senha.senha.length !== 0) {
            $http.get('/usuario/verificarSenha/' + $scope.usuario.senha.senha).success(function (data) {
                $scope.senhaInvalida = data;
                if ($scope.usuario.senha.senha.length > 20) {
                    $scope.maxSenha = true;
                } else {
                    $scope.maxSenha = false;
                }

                if ($scope.usuario.senha.senha.length < 6) {
                    $scope.minSenha = true;
                } else {
                    $scope.minSenha = false;
                }
                $scope.erroNaSenha = ($scope.maxSenha || !$scope.senhaInvalida || $scope.minSenha);
                $scope.formCad.$valid = !($scope.erroNaSenha || $scope.erroNoLogin || !($scope.usuario.rsenha.length !== 0) || !($scope.usuario.login.length !== 0));
                $scope.validarSenhaConferem();
            });

        } else {
            $scope.formCad.$valid = ($scope.usuario.login.length === 0 && $scope.usuario.rsenha.length === 0);
        }
    };

    $scope.validarSenhaConferem = function () {
        $scope.erroSenhaDiferente = false;
        if ($scope.usuario.rsenha.length !== 0) {
            if ($scope.usuario.senha.senha === $scope.usuario.rsenha) {
                $scope.senhaConfere = false;
            } else {
                $scope.senhaConfere = true;
            }

            $scope.erroSenhaDiferente = $scope.senhaConfere;
            $scope.formCad.$valid = !($scope.erroSenhaDiferente || $scope.erroNoLogin || $scope.erroNaSenha || !($scope.usuario.login.length !== 0));
        } else {
            $scope.formCad.$valid = ($scope.usuario.login.length === 0 && $scope.usuario.senha.senha.length === 0);
            ;
        }
    };

    $scope.carregar = function () {
        if ($location.path() === "/Usuario/novo") {
            novoUsuario();
        }
        else {
            $timeout(function () {
                $http.get("/usuario/obj/" + $routeParams.id)
                        .success(function (data) {
                            criarUsuarioParaEditar();     
                            $scope.usuario.idusuario = $routeParams.id;
                            $scope.usuario.nome = data.nome;
                            $scope.usuario.cpf.cpf = (data.cpf != null) ? data.cpf.cpf : "";
                            $scope.usuario.genero = data.genero;
                            $scope.usuario.email.email = data.email.email;
                            var d = new Date(data.datanascimento);
                            $scope.usuario.datanasc = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                            $scope.usuario.cep = data.endereco.cep;
                            $scope.usuario.idfuncao = (data.funcao != null) ? data.funcao.idfuncao : "";
                            if (data.endereco != null && data.endereco.cidade != null && data.endereco.cidade.estado != null) {
                                $scope.usuario.codigoestado = data.endereco.cidade.estado.codigoestado;
                                $scope.usuario.codigoibge = data.endereco.cidade.codigoIBGE;
                            } else {
                                $scope.usuario.codigoestado = "";
                                $scope.usuario.codigoibge = "";
                            }
                            $scope.listarCidades();
                            $scope.usuario.logradouro = data.endereco.logradouro;
                            $scope.usuario.numero = data.endereco.numero;
                            $scope.usuario.complemento = data.endereco.complemento;
                            $scope.usuario.bairro = data.endereco.bairro;
                            $scope.usuario.telefones[0].telefone = (data.telefones[0]!=undefined)?data.telefones[0].telefone:"";
                            if (data.telefones[1] != undefined) {
                                $scope.usuario.telefones[1].telefone = data.telefones[1].telefone;
                            }
                            $scope.usuario.status = data.status;
                            $scope.usuario.tipo = data.tipo;
                            $scope.usuario.imgSrc = data.imgSrc;
//                                $scope.usuario.rsenha = $scope.usuario.senha;

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
        novoUsuario();
    };

    $scope.listarEstados = function () {
        $http.get("/uf/listar").success(function (data) {
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
        if (codigo == $scope.usuario.codigoibge) {
            return true;
        } else {
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

    function foto(id) {
        $http.get("/foto/user/" + id)
                .success(function (data) {
                    $scope.urlFoto = data.foto;
                }).error(deuErro);
    };

    $scope.webcamFoto = function () {
        $(document).ready(function () {
            canvas = document.getElementById('imgCanvas');
            $scope.usuario.imgSrc = canvas.src;
        });
        console.log($scope.usuario.imgSrc);
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

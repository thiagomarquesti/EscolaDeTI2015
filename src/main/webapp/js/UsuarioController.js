module.controller("UsuarioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function ($scope, $http, $routeParams, $location, $timeout) {

        function novoUsuario() {
            $scope.usuario = {
                nome: "",
                telefones: [{
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
                codigoIBGE: "",
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
//                $scope.usuario.cpf = removerMascara($scope.usuario.cpf.cpf);
//                $scope.usuario.endereco.cep = removerMascara($scope.usuario.endereco.cep);
//                $scope.usuario.telefones[0].telefone = removerMascara($scope.usuario.telefones[0].telefone);
//                $scope.usuario.telefones[1].telefone = removerMascara($scope.usuario.telefones[1].telefone);
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
                $http.put("/usuario/", $scope.usuario)
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
                $http.get("/usuario/" + $routeParams.id)
                        .success(function (data) {                           
                            novoUsuario();
                            
                            console.log(data[0]);
                            $scope.auxUsuario.nome = data[0].nome;
                            $scope.auxUsuario.cpf.cpf = data[0].cpf;
                            $scope.auxUsuario.genero = data[0].genero;
                            $scope.auxUsuario.email.email = data[0].email;
                            $scope.auxUsuario.idfuncao = data[0].idfuncao;
                            $scope.auxUsuario.datanasc = data[0].datanasc;
                            $scope.auxUsuario.cep = data[0].cep;
                            $scope.auxUsuario.codigoestado = data[0].codigoestado;
                            $scope.auxUsuario.codigoIBGE = data[0].codigoIBGE;
                            $scope.auxUsuario.logradouro = data[0].logradouro;
                            $scope.auxUsuario.numero = data[0].numero;
                            $scope.auxUsuario.complemento = data[0].complemento;
                            $scope.auxUsuario.bairro = data[0].bairro;
                            $scope.auxUsuario.telefones[0].telefone = data[0].telefone;
//                            $scope.auxUsuario.telefones[1].telefone = data[0].telefone;
                            $scope.auxUsuario.bairro = data[0].bairro;

                            $scope.usuario = $scope.auxUsuario;
                            $scope.usuario.rsenha = $scope.usuario.senha;
                            console.log($scope.usuario.cpf.cpf);
                            $scope.isNovo = false;
                        })
                        .error(deuErro);
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
            $http.get("/uf").success(function (data) {
                $scope.estados = data;
//                console.log(data);
//                console.log($scope.estados.codigoestado);
            }).error(deuErro);
        };

        $scope.listarCidades = function () {
            if ($scope.usuario.codigoestado != ""){
                console.log($scope.usuario.codigoestado);
                $http.get("/cidade/cidadePorEstado/" + $scope.usuario.codigoestado).success(function (data) {
                    $scope.cidades = data;
                    console.log(data);
                }).error(deuErro);
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

//    $scope.carregaPerfis = function(){
//        $http.get("/perfildeacesso")
//                .success(function(data){
//                    $scope.usuario.perfis = data;
//                }).error(deuErro());
//    };    
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

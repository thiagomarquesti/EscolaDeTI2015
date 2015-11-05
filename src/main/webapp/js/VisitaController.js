module.controller("VisitaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', 'ServiceFuncoes', function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {
    $scope.busca = {};
    $scope.placeHolder = "Buscar visita";
    $scope.ent = $rootScope.ent = "visita";
    $scope.campoPrincipal = 'datavisita';
    $scope.isNovaEstadia = true;

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

    function novaVisita() {
        $scope.visita = {
            datavisita: "",
            horavisita: "",
            horaentrada: "",
            horasaida: "",
            observacao: "",
            quantidadedevisitantes: "",
            seriecurso: "",
            visitarealizada: "",
            telefonevisita: "",
            pessoaresponsavel: {idpessoa: ""},
            entidade: {idpessoa: ""}
        };
        $scope.isNovaVisita = true;
    }
    ;
    $scope.novaVisita = function () {
        novaVisita();
    };

    $scope.carregarVisita = function () {
        if ($location.path() === "/Visita/nova") {
            novaVisita();
        }
        else {
            $timeout(function () {
                $http.get("/visita/obj/" + $routeParams.id)
                .success(function (data) {
                    var dados = data;
                    var d = new Date(data.datavisita);
                    dados.datavisita = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                    dados.horavisita = new Date(1970, 01, 01, data.horavisita.substring(0, 2), data.horavisita.substring(3, 5), 00, 00);
                    
                    if(dados.horaentrada) {
                        dados.horaentrada = new Date(1970, 01, 01, data.horaentrada.substring(0, 2), data.horaentrada.substring(3, 5), 00, 00);
                    }
                    if(dados.horasaida) {
                        dados.horasaida = new Date(1970, 01, 01, data.horasaida.substring(0, 2), data.horasaida.substring(3, 5), 00, 00);
                    }
                    $scope.visita = dados;
                    $scope.isNovaVisita = false;
                    $scope.pegarFone('fisica');
                    $scope.pegarFone('juridica');
                })
                .error(deuErro);
            }, 100);
        }
    };

    $scope.editarVisita = function (visita) {
        $location.path("/Visita/editar/" + visita.idvisita);
    };

    $scope.deletarVisita = function (visita) {
        $http.delete("/visita/" + visita.idvisita)
        .success(function () {
            toastr.success("Visita excluída com sucesso.");
            $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoAtual, '', '', $rootScope.ent, false);
        }).error(deuErroDeletar);
    };

    function deuErro() {
        toastr.error("Algo deu errado. Tente novamente.");
    }

    function deuErroAtualizacao() {
        toastr.error("Atenção, erro ao tentar editar a visita, verifique!");
    }

    function deuErroSalvar() {
        toastr.error("Atenção, erro ao tentar salvar a visita, verifique!");
    }

    function deuErroDeletar() {
        toastr.error("Atenção, erro ao tentar deletar a visita, verifique!");
    }

    $scope.salvarVisita = function () {
        
        var visitaCompleta = $scope.visita;
        
        var dVisita = new Date($scope.visita.datavisita);
        var dVisitaOK = dVisita.getFullYear() + "-" + (dVisita.getMonth() + 1) + '-' + dVisita.getDate();

        var hVisita = new Date($scope.visita.horavisita);
        var hVisitaOK = hVisita.getHours() + ":" + hVisita.getMinutes() + ":00";
        
        if($scope.visita.horaentrada) {
            var hEntrada = new Date($scope.visita.horaentrada);
            var hEntradaOK = hEntrada.getHours() + ":" + hEntrada.getMinutes() + ":00";
            visitaCompleta.horaentrada = hEntradaOK;
        }
        else{
            visitaCompleta.horaentrada = null;
        }
        
        if($scope.visita.horasaida){
            var hSaida = new Date($scope.visita.horasaida);
            var hSaidaOK = hSaida.getHours() + ":" + hSaida.getMinutes() + ":00";
            visitaCompleta.horasaida = hSaidaOK;
        }
        else {
            visitaCompleta.horasaida = null;
        }
        
        visitaCompleta.datavisita = dVisitaOK + "T00:00:00-03";
        visitaCompleta.horavisita = hVisitaOK;

        if (hSaidaOK) {
            visitaCompleta.visitarealizada = true;
        }
        else {
            visitaCompleta.visitarealizada = false;
        }
        
        console.log(visitaCompleta);
        
        if ($scope.isNovaVisita) {
            //console.log(visitaCompleta)
            $http.post("/visita", visitaCompleta)
                    .success(function () {
                        $location.path("/Visita/listar");
                        novaVisita();
                        toastr.success("Visita inserida com sucesso!");
                    })
                    .error(deuErroSalvar);
        }
        else {
            $http.put("/visita", $scope.visita)
                    .success(function () {
                        $location.path("/Visita/listar");
                        toastr.success("Visita atualizada com sucesso!");
                    })
                    .error(deuErroAtualizacao);
        }
    };

    $scope.listarFisicas = function () {
        $http.get('/pessoa/fisica/getFisicas')
                .success(function (data) {
                    $scope.fisicas = data;
                })
                .error(deuErro);
    };

    $scope.listarJuridicas = function () {
        $http.get('/pessoa/juridica')
                .success(function (data) {
                    $scope.juridicas = data;
                })
                .error(deuErro);
    };

    $scope.pegarFone = function (tipo) {
        if ($scope.visita.pessoaresponsavel.idpessoa) {
            if (tipo == 'fisica') {
                $http.get("/pessoa/fisica/obj/" + $scope.visita.pessoaresponsavel.idpessoa)
                        .success(function (data) {
                            $scope.telefoneFisica = data.telefone.telefone;
                        })
                        .error(deuErro);
            }
        }
        else {
            $scope.telefoneFisica = "";
        }
        if ($scope.visita.entidade.idpessoa) {
            if (tipo == 'juridica') {
                $http.get("/pessoa/juridica/obj/" + $scope.visita.entidade.idpessoa)
                        .success(function (data) {
                            $scope.telefoneJuridica = data.telefone.telefone;
                        })
                        .error(deuErro);
            }
        }
        else {
            $scope.telefoneJuridica = "";
        }


    };

    $scope.dateToData = function (valor) {
        var data = "";
        if (valor != null && valor != "" && valor != undefined) {
            data = ServiceFuncoes.dateToData(valor);
        }
        return data;
    };
}]);
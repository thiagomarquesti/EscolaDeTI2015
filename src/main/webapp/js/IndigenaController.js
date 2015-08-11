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
        var cpfSemPonto = tiraCaracter($scope.indio.cpf, ".");
        var cpfSemPonto = tiraCaracter($scope.indio.cpf, "-");
        var susSemEspaco = tiraCaracter($scope.indio.codigoSUS, " ");
        var dataNasc = dataToDate($scope.indio.dataNascimento);
        
        var indioCompleto = {
            nome : $scope.indio.nome ,
            cpf: {
                cpf:cpfSemPonto
            },
            etnia: $scope.indio.etnia ,
            genero: $scope.indio.genero,
            dataNascimento: dataNasc,
            convenio: $scope.indio.conveniosselecionados ,
            telefone: {
                telefone: $scope.indio.telefone
            },
            terraIndigena: $scope.indio.terraIndigena ,
            escolaridade: $scope.indio.escolaridade ,
            estadoCivil: $scope.indio.estadoCivil ,
            codigoSUS: susSemEspaco
        };
        
        console.log(indioCompleto);
        
        if ($scope.isNovoIndio) {
            $http.post("/indigena", indioCompleto)
                    .success(function () {
                        toastr.success("Indígena cadastrado com sucesso!");
                        $location.path("/Indigena/listar");
                    })
                    .error(erroCadastraIndio);
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

    $scope.atualizarIndigenas = function (pag,campo,order,string, paro) {
        if(pag == null || pag == ""){ pag = 1; }
        if(campo == null || campo == ""){ campo = "descricao"; }
        if(order != "asc" && order != "desc"){ order = "asc"; }
        if(string == null){ string = ""; }
//      if(order == "desc"){ $scope.tipoOrdem == true; } else { $scope.tipoOrdem == false; }
        $http.get("/indigena/listar/"+pag+"/"+campo+"/"+order+"/"+string)
            .success(function (data) {
                $scope.indigenas = data;
                console.log(data);
                console.log("/indigena/listar/"+pag+"/"+campo+"/"+order+"/"+string);

                if (!paro) { atualizaPaginacao(data.quantidadeDePaginas, pag, campo, order, string, false); }
                
            })
            .error(deuErro);
    };

    $scope.trocaOrdem = function(string){
        if($scope.tipoOrdem == true){
            $scope.tipoOrdem = false;
            var ordem = "asc";
        }
        else {
            $scope.tipoOrdem = true;
            var ordem = "desc";
        }
        $scope.atualizarIndigenas("","", ordem ,string, true);
    };
    
    function atualizaPaginacao(qtde, pag, campo, order, string, paro){
        $('#paginacao').bootpag({
            total: qtde,
            page: pag,
            maxVisible:5
        }).on('page', function(event, num){
            paro = true;
            $scope.atualizarIndigenas(num, campo, order, string, paro);
            
        });
    }

    function tiraCaracter(campo, oque) {
        var str = campo.split(oque).join("");
        return str;
    }

    function dataToDate(dados) {
        var data = dados.substring(6, 10) + "-" + dados.substring(3, 5) + "-" + dados.substring(0, 2);
        return data;
    }


    $scope.editarIndio = function (indio) {
        $location.path("/Indigena/editar/" + indio.id);
    };
    
    $scope.excluirIndio = function (indio){
        
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
    
    function erroCadastraIndio() {
        toastr.error("Não foi possível cadastrar o indígena. ","Erro");
    }

    $scope.carregaScript = function (nScript) {
        $timeout(function () {
            var script = document.createElement('script');
            script.src = nScript + ".js";
            document.getElementsByTagName('head')[0].appendChild(script);
        }, 100);
    };

}]);

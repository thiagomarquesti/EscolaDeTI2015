module.controller("IndigenaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function ($scope, $http, $routeParams, $location, $timeout) {

    function novoIndio() {
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
        $scope.isNovoIndio = true;
    }

    $scope.carregarIndio = function () {
        console.log($location.path());
        if ($location.path() === "/Indigena/novo") {
            novoIndio();
        }
        else {
            $timeout(function () {
                $http.get("/indigena/obj/" + $routeParams.id)
                    .success(function(data) {
                        console.log(data);
                        var dados = data;
                        dados.cpf = data.cpf.cpf;
                        dados.telefone = data.telefone.telefone;
                        dados.dataNascimento = dateToData(data.dataNascimento);
                        dados.etnia = data.etnia.idetnia;
                        dados.terraIndigena = data.terraIndigena.idTerraIndigena;
                        dados.conveniosselecionados = data.convenio;
                        
                        $scope.indio = dados;

                        $scope.isNovoIndio = false;
                    })
                    .error(deuErro);
            }, 100);
        }
    };
    
    $scope.salvarIndio = function () {
        var cpfSemPonto = tiraCaracter($scope.indio.cpf, ".");
        var cpfSemPonto = tiraCaracter(cpfSemPonto, "-");
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
        
        
        if ($scope.isNovoIndio) {
            $http.post("/indigena", indioCompleto)
                    .success(function () {
                        toastr.success("Indígena cadastrado com sucesso!");
                        $location.path("/Indigena/listar");
                    })
                    .error(erroCadastraIndio);
        }
        else {
            indioCompleto.codigoAssindi = $routeParams.id;
            console.log(indioCompleto);
            $http.put("/indigena", indioCompleto)
                    .success(function () {
                        toastr.success("Indígena atualizado com sucesso!");
                        $location.path("/Indigena/listar");
                    })
                    .error(deuErro);
        }

    };

    $scope.atualizarIndigenas = function (pag,campo,order,string, paro) {
        if(pag == null || pag == ""){ pag = 1; }
        if(campo == null || campo == ""){ campo = "nome"; }
        if(order != "asc" && order != "desc"){ order = "asc"; }
        if(string == null){ string = ""; }
//      if(order == "desc"){ $scope.tipoOrdem == true; } else { $scope.tipoOrdem == false; }
        $http.get("/indigena/listar/"+pag+"/"+campo+"/"+order+"/"+string)
            .success(function (data) {
                $scope.indigenas = data;
                //console.log(data);
                //console.log("/indigena/listar/"+pag+"/"+campo+"/"+order+"/"+string);

                if (!paro) { atualizaPaginacao(data.quantidadeDePaginas, pag, campo, order, string, false); }
                
            })
            .error(deuErro);
    };

    $scope.trocaOrdem = function(campo, string){
        if($scope.tipoOrdem == true){
            $scope.tipoOrdem = false;
            var ordem = "asc";
        }
        else {
            $scope.tipoOrdem = true;
            var ordem = "desc";
        }
        $scope.campoAtual = campo;
        $scope.atualizarIndigenas("",campo, ordem ,string, true);
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
    if (!$scope.isNovoIndio) {
        return campo;
        }
        var str = campo.split(oque).join("");
        return str;
    }

    function dataToDate(valor) {
        var data = valor.substring(6, 10) + "-" + valor.substring(3, 5) + "-" + valor.substring(0, 2);
        return data;
    }
    
    function dateToData(valor) {
        var data = valor.substring(8, 10) + "/" + valor.substring(5, 7) + "/" + valor.substring(0, 4);
        return data;
    }


    $scope.editarIndio = function (indio) {
        $location.path("/Indigena/editar/" + indio.codigo_assindi);
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
    
    $scope.ehMeninoMenina = {
         "MASCULINO" : {
             "icone" : "fa fa-male",
             "cor" : "#9CC7FF"
         },
         "FEMININO" : {
             "icone" : "fa fa-female",
             "cor" : "#FFC4C4"
         }
    };
    
    $scope.calculaIdade = function(data){
        
        var ano_aniversario = data.substring(0, 4);
        var mes_aniversario = data.substring(5,7);
        var dia_aniversario = data.substring(8,10);
        
        var d = new Date,
        ano_atual = d.getFullYear(),
        mes_atual = d.getMonth() + 1,
        dia_atual = d.getDate(),

        ano_aniversario = +ano_aniversario,
        mes_aniversario = +mes_aniversario,
        dia_aniversario = +dia_aniversario,

        quantos_anos = ano_atual - ano_aniversario;

        if (mes_atual < mes_aniversario || mes_atual == mes_aniversario && dia_atual < dia_aniversario) {
            quantos_anos--;
        }

        return quantos_anos < 0 ? 0 : quantos_anos;
    };
    
    $scope.carregaScript = function (nScript) {
        $timeout(function () {
            var script = document.createElement('script');
            script.src = nScript + ".js";
            document.getElementsByTagName('head')[0].appendChild(script);
        }, 100);
    };

}]);

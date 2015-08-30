module.controller("IndigenaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao) {

        $scope.placeHolder = "Buscar indígena";
        
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
    
    $scope.registrosPadrao = function() {
        $timeout( function(){ 
            if($scope.busca.numregistros === "") { 
                $scope.busca.numregistros = 10;
            }
            alert($scope.busca);
        },1000);
    };
    
    
    $scope.atualizarListagens = function(qtdePorPag, pag,campo,order,string, paro, entidade){
        entidade = 'indigena';
        ServicePaginacao.atualizarListagens(qtdePorPag, pag,campo,order,string, paro, entidade);
    };
    
    $scope.trocaOrdem = function(qtdePorPag, campo, string, entidade){
        ServicePaginacao.trocaOrdem(qtdePorPag, campo, string, entidade);
    };
    
    
    
    $scope.carregarIndio = function () {
        if ($location.path() === "/Indigena/novo") {
            novoIndio();
        }
        else {
            $timeout(function () {
                $http.get("/indigena/obj/" + $routeParams.id)
                    .success(function(data) {
                        var dados = data;
                        var d = new Date(data.dataNascimento);
                        dados.cpf = data.cpf.cpf;
                        dados.telefone = data.telefone.telefone;
                        dados.dataNascimento = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                        dados.etnia = data.etnia.idetnia;
                        dados.terraIndigena = data.terraIndigena.idterraindigena;
                        dados.conveniosselecionados = data.convenio;
                        $scope.indio = dados;
                        $scope.isNovoIndio = false;
                    })
                    .error(deuErro);
            }, 100);
        }
    };
    
    $scope.salvarIndio = function () {
//        var cpfSemPonto = tiraCaracter($scope.indio.cpf, ".");
//        var cpfSemPonto = tiraCaracter(cpfSemPonto, "-");
        var dataNasc = dataToDate($scope.indio.dataNascimento);
        var indioCompleto = {
            nome: $scope.indio.nome ,
            cpf: {
                cpf: $scope.indio.cpf
            },
            etnia: $scope.indio.etnia ,
            genero: $scope.indio.genero,
            dataNascimento: dataNasc + "T00:00:00-03",
            convenio: $scope.indio.conveniosselecionados ,
            telefone: {
                telefone: $scope.indio.telefone
            },
            terraIndigena: $scope.indio.terraIndigena ,
            escolaridade: $scope.indio.escolaridade ,
            estadoCivil: $scope.indio.estadoCivil ,
            codigoSUS: $scope.indio.codigoSUS
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

    function tiraCaracter(campo, oque) {
        var str = campo.split(oque).join("");
        return str;
    }

    function dataToDate(valor) {
        var date = new Date(valor);
        var data = date.getFullYear() + "-" + (date.getMonth() + 1) + '-' + date.getDate();
        return data;
    }
    
    $scope.editarIndio = function (indio) {
        $location.path("/Indigena/editar/" + indio.codigoassindi);
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

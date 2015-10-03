module.controller("IndigenaController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function ($scope, $http, $routeParams, $location, $timeout) {

        function novoIndio() {
            $scope.indio = {
                nome: "",
                cpf: "",
                etnia: "",
                genero: "",
                dataNascimento: "",
                conveniosselecionados: [],
                telefone: "",
                terraIndigena: "",
                escolaridade: "",
                estadoCivil: "",
                codigoSUS: "",
                imgSrc: ""
            };
            $scope.isNovoIndio = true;
        }

        $scope.carregarIndio = function () {
            if ($location.path() === "/Indigena/novo") {
                novoIndio();
            }
            else {
                $timeout(function () {
                    $http.get("/indigena/obj/" + $routeParams.id)
                            .success(function (data) {
                                var dados = data;
                                var d = new Date(data.dataNascimento);
                                dados.cpf = data.cpf.cpf;
                                dados.telefone = data.telefone.telefone;
                                dados.dataNascimento = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
                                dados.etnia = data.etnia.idetnia;
                                dados.terraIndigena = data.terraIndigena.idterraindigena;
                                dados.conveniosselecionados = data.convenio;
                                dados.imgSrc = data.imgSrc;
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
                nome: $scope.indio.nome,
                cpf: {
                    cpf: $scope.indio.cpf
                },
                etnia: $scope.indio.etnia,
                genero: $scope.indio.genero,
                dataNascimento: dataNasc + "T00:00:00-03",
                convenio: $scope.indio.conveniosselecionados,
                telefone: {
                    telefone: $scope.indio.telefone
                },
                terraIndigena: $scope.indio.terraIndigena,
                escolaridade: $scope.indio.escolaridade,
                estadoCivil: $scope.indio.estadoCivil,
                codigoSUS: $scope.indio.codigoSUS,
                imgSrc: $scope.indio.imgSrc
            };

            if ($scope.isNovoIndio) {
                console.log(indioCompleto);
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
        
        $scope.trocaOrdem = function (string) {
            if ($scope.tipoOrdem == true) {
                $scope.tipoOrdem = false;
                var ordem = "asc";
            }
            else {
                $scope.tipoOrdem = true;
                var ordem = "desc";
            }
            $scope.atualizarIndigenas("", "", ordem, string, true);
        };

        $scope.atualizarIndigenas = function (reg, pag, campo, order, string, paro) {
            if (reg == null || reg == "") {
                reg = 10;
            }
            if (pag == null || pag == "") {
                pag = 1;
            }
            if (campo == null || campo == "") {
                campo = "nome";
            }
            if (order != "asc" && order != "desc") {
                order = "asc";
            }
            if (string == null) {
                string = "";
            }
//      if(order == "desc"){ $scope.tipoOrdem == true; } else { $scope.tipoOrdem == false; }
            $http.get("/indigena/listar/" + reg + "/" + pag + "/" + campo + "/" + order + "/" + string)
                    .success(function (data) {
                        $scope.indigenas = data;
                        if (!paro) {
                            atualizaPaginacao(data.quantidadeDePaginas, pag, campo, order, string, false);
                        }
                    })
                    .error(deuErro);
        };

        $scope.trocaOrdem = function (campo, string) {
            if ($scope.tipoOrdem == true) {
                $scope.tipoOrdem = false;
                var ordem = "asc";
            }

            function tiraCaracter(campo, oque) {
                var str = campo.split(oque).join("");
                return str;
            }
            $scope.campoAtual = campo;
            $scope.atualizarIndigenas("", campo, ordem, string, true);
        };

        function atualizaPaginacao(qtde, pag, campo, order, string, paro) {
            $('#paginacao').bootpag({
                total: qtde,
                page: pag,
                maxVisible: 5
            }).on('page', function (event, num) {
                paro = true;
                $scope.atualizarIndigenas(reg, num, campo, order, string, paro);
            });
        }

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

        function erroCadastraIndio() {
            toastr.error("Não foi possível cadastrar o indígena. ", "Erro");
        }

        $scope.ehMeninoMenina = {
            "MASCULINO": {
                "icone": "fa fa-male",
                "cor": "#9CC7FF"
            },
            "FEMININO": {
                "icone": "fa fa-female",
                "cor": "#FFC4C4"
            }
        };

        function foto(id) {
            $http.get("/foto/indio/" + id)
                    .success(function (data) {
                        $scope.urlFoto = data.foto;
                    }).error(deuErro);
        };

        $scope.webcamFoto = function () {
            $(document).ready(function () {
                canvas = document.getElementById('imgCanvas');
                $scope.indio.imgSrc = canvas.src;
            });
            console.log($scope.indio.imgSrc);
        };

        $scope.calculaIdade = function (data) {

            var ano_aniversario = data.substring(0, 4);
            var mes_aniversario = data.substring(5, 7);
            var dia_aniversario = data.substring(8, 10);

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
        /*  SCRIPTS PARA CARREGAR OPTIONS DOS SELECTS  */
        $scope.getEtnias = function () {
            $http.get("/etnia")
                    .success(function (data) {
                        $scope.etnias = "";
                        $scope.etnias = data;
                    })
                    .error(deuErro);
        };
        
        $scope.getTerras = function () {
            $http.get("/terraIndigena")
                    .success(function (data) {
                        $scope.terras = data;
//                        console.log($scope.terras);
                    })
                    .error(deuErro);
        };
        
        $scope.getConvenios = function () {
            $http.get("/convenio")
                    .success(function (data) {
                        console.log(data);
                        $scope.convenios = data;
                    })
                    .error(deuErro);
        };
        /*  FIM DOS SCRIPTS  */
        $scope.carregaScript = function (nScript) {
            $timeout(function () {
                var script = document.createElement('script');
                script.src = nScript + ".js";
                document.getElementsByTagName('head')[0].appendChild(script);
            }, 100);
        };
        
        function novaOcorrencia() {
            $scope.ocorrencia = {
                dataOcorrencia: "",
                dataBloqueio: "",
                descricao: ""
            };
        }

        $scope.carregarOcorrencia = function () {
            novaOcorrencia();
        };

        $scope.getOcorrencias = function () {
            var url = ($routeParams.id == undefined) ? "" : "/ocorrencias/" + $routeParams.id;
            $http.get("/ocorrencia" + url).success(function (data) {
                $scope.ocorrencias = data;
                console.log("pego");
            }).error(deuErro);
        };

        $scope.getIdIndigena = function (indigena){
            $routeParams.id = indigena.codigoassindi;
        };

        $scope.deletarOcorrencia = function (idOcorrencia) {
            $http.delete("/ocorrencia/" + idOcorrencia + "/" + $routeParams.id)
            .success(function () {
                toastr.success("Ocorrência deletada com sucesso.", "Apagado");
                $scope.getOcorrencias();
            })
            .error(deuErro);
        };

        $scope.salvarOcorrencia = function () {
            var dataOcorrencia = dataToDate($scope.ocorrencia.dataOcorrencia);
            var dataBloqueio = ($scope.ocorrencia.dataBloqueio != "") ? dataToDate($scope.ocorrencia.dataBloqueio) : "";
            var OcorrenciaCompleta = {
                dataocorrencia: dataOcorrencia + "T00:00:00-03",
                databloqueio: (dataBloqueio != "") ? dataBloqueio + "T00:00:00-03" : null,
                descricao: $scope.ocorrencia.descricao,
                idIndigena: $routeParams.id
            };

            $http.post("/ocorrencia", OcorrenciaCompleta)
                    .success(function () {
                        toastr.success("Ocorrência salva com sucesso.", "Salvo");
                        $scope.getOcorrencias();
                    })
                    .error(deuErro);
        };
        
        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }
    }]);
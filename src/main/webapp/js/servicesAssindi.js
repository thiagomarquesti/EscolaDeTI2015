module.service('ServicePaginacao', ['$http','$rootScope', function ($http, $rootScope) {
    
    var todosDados = { 
        itens: []
//        campoAtual : "",
//        tipoOrdem : ""
    };
    
    var ordenacao;
    
    return {
        
        atualizarListagens : function(qtdePorPag, pag, campo, string, paro, entidade, troca) {
            if(pag == null || pag == ""){ pag = 1; }
            if($rootScope.tipoOrdem == null || $rootScope.tipoOrdem == ""){ $rootScope.tipoOrdem = "asc"; }
            if(troca === 'ok') { $rootScope.tipoOrdem = this.trocaOrdem($rootScope.tipoOrdem, troca); }
            if(string == null){ string = ""; }
            var busca = "/"+entidade+"/listar/"+qtdePorPag+"/"+pag+"/"+campo+"/"+$rootScope.tipoOrdem+"/"+string;
            console.log(busca);
            $http.get(busca)
                .success(function (data) {
                    todosDados.itens = data;
                    $rootScope.pagina = data.paginaAtual;
                    $rootScope.campoAtual = campo;
                    if (!paro) { atualizarPaginacao(data.quantidadeDePaginas, qtdePorPag, pag, campo, string, false, entidade); }
                })
                .error(deuErro);
            return todosDados;
        },
        
        registrosPadrao : function(numregistros) {
            if(!numregistros) { 
                numregistros = 10;
            }
            return numregistros;
        },
        
        trocaOrdem : function(ordem, troca) {
            var ordenacao;
            if(ordem == 'asc'){
                ordenacao = 'desc';
            }
            else {
                ordenacao = 'asc';
            }
            return ordenacao;
        }
    };
    
    function atualizarPaginacao(qtde, qtdePorPag, pag, campo, string, paro, entidade){
        $('#paginacao').bootpag({
            total: qtde,
            page: pag,
            maxVisible:5
        }).on('page', function(event, pag){
            paro = true;
            $rootScope.atualizarListagens(qtdePorPag, pag, campo, string, paro, entidade,0);
        });
    }
    
    function deuErro() {
        toastr.error("Erro na listagem", "Erro");
    }
        
}]);





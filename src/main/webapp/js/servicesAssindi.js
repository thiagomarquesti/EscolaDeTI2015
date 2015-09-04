module.service('ServicePaginacao', ['$http', function ($http) {
    
    var todosDados = { 
        itens: [],
        campoAtual : "",
        tipoOrdem : ""
    };
    
    return {
        
        atualizarListagens : function(qtdePorPag, pag, campo, order, string, paro, entidade) {
            if(qtdePorPag == null || qtdePorPag == ""){ qtdePorPag = 10; }
            if(pag == null || pag == ""){ pag = 1; }
            if(order == null || order == ""){ order = "asc"; }
            if(string == null){ string = ""; }
            todosDados.campoAtual = campo;
            todosDados.tipoOrdem = order;
            var busca = "/"+entidade+"/listar/"+qtdePorPag+"/"+pag+"/"+campo+"/"+order+"/"+string;
            console.log(busca);
            $http.get(busca)
                .success(function (data) {
                    if (!paro) { atualizarPaginacao(data.quantidadeDePaginas, qtdePorPag, pag, campo, order, string, false, entidade); }
                    todosDados.itens = data;
                    
                    //console.log(todosDados);
                    
                })
                .error(deuErro);
            return todosDados;
        },
        
        registrosPadrao : function(numregistros) {
            if(numregistros == null || numregistros == "") { 
                numregistros = 10;
            }
            return numregistros;
        },
        
         trocaOrdem : function(ordem, troca) {
            if(troca == 'ok'){
                if(ordem == 'asc'){
                    ordem = 'desc';

                }
                else {
                    ordem = 'asc';
                }
            }
            return ordem;
        }
        
    };
    
    function atualizarPaginacao(qtde, qtdePorPag, pag, campo, order, string, paro, entidade){
        $('#paginacao').bootpag({
            total: qtde,
            page: pag,
            maxVisible:5
        }).on('page', function(event, pag){
            paro = true;
            return atualizarListagens(qtdePorPag, pag, campo, order, string, paro, entidade, 0);
        });
    }
    
    
    function deuErro() {
        toastr.error("Erro na listagem", "Erro");
    }
        
}]);





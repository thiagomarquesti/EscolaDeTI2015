module.service('ServicePaginacao', ['$http', function ($http) {
    
    var todosDados = { 
        itens: [],
        variaveis: []
    };
    
    return {
        atualizarListagens : function(qtdePorPag, pag, campo, order, string, paro, entidade, troca) {
            if(qtdePorPag == null || qtdePorPag == ""){ qtdePorPag = 10; }
            if(pag == null || pag == ""){ pag = 1; }
            if(!order){ order = "asc"; }
            if(string == null){ string = ""; }
            if(troca == 1){
                if(order == 'asc') { order = 'desc'; }
                else { order = 'asc'; }
            }
            console.log("/"+entidade+"/listar/"+qtdePorPag+"/"+pag+"/"+campo+"/"+order+"/"+string);
            todosDados.variaveis.campoAtual = campo;
            todosDados.variaveis.tipoOrdem = order;
            $http.get("/"+entidade+"/listar/"+qtdePorPag+"/"+pag+"/"+campo+"/"+order+"/"+string)
                .success(function (data) {
                    if (!paro) { atualizarPaginacao(data.quantidadeDePaginas, qtdePorPag, pag, campo, order, string, false, entidade); }
                    todosDados.itens = data;
                    
                })
                .error(deuErro);
            return todosDados;
        },
        
        registrosPadrao : function(numregistros) {
            if(numregistros == null || numregistros == "") { 
                numregistros = 10;
            }
            return numregistros;
        }
        
//        trocaOrdem : function(qtdePorPag, campo, string, entidade, ordemAtual){
//            if(ordemAtual === 'asc'){
//                this.ordem = "desc";
//            }
//            else {
//                this.ordem = "asc";
//            }
//            return this.atualizarListagens(qtdePorPag, "",campo, this.ordem ,string, true, entidade);
//        }       
        
        
    };
    
//    this.atualizarListagens = function (qtdePorPag, pag,campo,order,string, paro, entidade) {
//        if(qtdePorPag == null || qtdePorPag == ""){ qtdePorPag = 10; }
//        if(pag == null || pag == ""){ pag = 1; }
//        if(campo == null || campo == ""){ campo = "nome"; }
//        if(order != "asc" && order != "desc"){ order = "asc"; }
//        if(string == null){ string = ""; }
//        $http.get("/"+entidade+"/listar/"+qtdePorPag+"/"+pag+"/"+campo+"/"+order+"/"+string)
//            .success(function (data) {
//                console.log(data);
//                todosDados = data;
//                if (!paro) { atualizaPaginacao(data.quantidadeDePaginas, qtdePorPag, pag, campo, order, string, false, entidade); }
//            })
//            .error(deuErro);
//    };
    
    
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





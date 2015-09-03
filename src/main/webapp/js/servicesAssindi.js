module.service('ServicePaginacao', ['$http', function ($http) {
    
    var todosDados = { 
        itens: [],
        variaveis: []
    };
    
    var vm = this;
    
    return {
        
        atualizaPaginacao : function(qtde, qtdePorPag, pag, campo, order, string, paro, entidade){
            $('#paginacao').bootpag({
                total: qtde,
                page: pag,
                maxVisible:5
            }).on('page', function(event, pag){
                paro = true;
                return vm.atualizarListagens(qtdePorPag, pag, campo, order, string, paro, entidade);
            });
        },
        
        atualizarListagens : function(qtdePorPag, pag, campo, order, string, paro, entidade) {
            if(qtdePorPag == null || qtdePorPag == ""){ qtdePorPag = 10; }
            if(pag == null || pag == ""){ pag = 1; }
            if(order != "asc" && order != "desc"){ order = "asc"; }
            if(string == null){ string = ""; }
            $http.get("/"+entidade+"/listar/"+qtdePorPag+"/"+pag+"/"+campo+"/"+order+"/"+string)
                .success(function (data) {
                    todosDados.itens = data;
                    todosDados.variaveis.campoAtual = campo;
                    //todosDados.variaveis.tipoOrdem = order;
                    if (!paro) { vm.atualizaPaginacao(data.quantidadeDePaginas, qtdePorPag, pag, campo, order, string, false, entidade); }
                })
                .error(deuErro);
            return todosDados;
            
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
    
    
//    function atualizaPaginacao(qtde, qtdePorPag, pag, campo, order, string, paro, entidade){
//        $('#paginacao').bootpag({
//            total: qtde,
//            page: pag,
//            maxVisible:5
//        }).on('page', function(event, pag){
//            paro = true;
//            atualizarListagens(qtdePorPag, pag, campo, order, string, paro, entidade);
//        });
//    }
    
    function deuErro() {
        toastr.error("Erro na listagem", "Erro");
    }
        
}]);





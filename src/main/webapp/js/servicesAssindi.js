module.service('ServicePaginacao', ['$http', function ($http) {
    
    var todosDados = { 
        itens: [] 
    };
    
    return {
        atualizarListagens : function(qtdePorPag, pag,campo,order,string, paro, entidade) {
            if(qtdePorPag == null || qtdePorPag == ""){ qtdePorPag = 10; }
            if(pag == null || pag == ""){ pag = 1; }
            if(campo == null || campo == ""){ campo = "nome"; }
            if(order != "asc" && order != "desc"){ order = "asc"; }
            if(string == null){ string = ""; }
            $http.get("/"+entidade+"/listar/"+qtdePorPag+"/"+pag+"/"+campo+"/"+order+"/"+string)
                .success(function (data) {
                    //console.log(data);
                    todosDados.itens = data;
                    if (!paro) { atualizaPaginacao(data.quantidadeDePaginas, qtdePorPag, pag, campo, order, string, false, entidade); }
                })
                .error(deuErro);
            return todosDados;
        }
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
    
    this.trocaOrdem = function(qtdePorPag, campo, string, entidade){
        if(this.tipoOrdem === true){
            this.tipoOrdem = false;
            var ordem = "asc";
        }
        else {
            this.tipoOrdem = true;
            var ordem = "desc";
        }
        this.campoAtual = campo;
        this.atualizarListagens(qtdePorPag, "",campo, ordem ,string, true, entidade);
    };
    
    function atualizaPaginacao(qtde, qtdePorPag, pag, campo, order, string, paro, entidade){
        $('#paginacao').bootpag({
            total: qtde,
            page: pag,
            maxVisible:5
        }).on('page', function(event, pag){
            paro = true;
            this.atualizarListagens(qtdePorPag, pag, campo, order, string, paro, entidade);
        });
    }
    
    function deuErro() {
        toastr.error("Erro na listagem", "Erro");
    }
        
}]);





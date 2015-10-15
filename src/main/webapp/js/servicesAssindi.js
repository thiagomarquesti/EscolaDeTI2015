module.service('ServicePaginacao', ['$http','$rootScope', function ($http, $rootScope) {
    
    var todosDados = { 
        itens: []
    };
    
    return {
        atualizarListagens : function(qtdePorPag, pag, campo, string, entidade, troca, paro) {
            if(pag == null || pag == ""){ pag = 1; }
            if($rootScope.tipoOrdem == null || $rootScope.tipoOrdem == ""){ $rootScope.tipoOrdem = "asc"; }
            if(troca === 'ok') { $rootScope.tipoOrdem = this.trocaOrdem($rootScope.tipoOrdem, troca); }
            if(string == null){ string = ""; }
            $rootScope.numregistros = qtdePorPag;
            var busca = "/"+entidade+"/listar/"+qtdePorPag+"/"+pag+"/"+campo+"/"+$rootScope.tipoOrdem+"/"+string;
            //console.log(busca);
            $http.get(busca)
                .success(function (data) {
                    todosDados.itens = data;
                    $rootScope.pagina = data.paginaAtual;
                    $rootScope.campoAtual = campo;
                    if (!paro) { atualizarPaginacao(data.quantidadeDePaginas, $rootScope.pagina, $rootScope.string); }
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
        
        trocaOrdem : function(ordem) {
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
        
    function atualizarPaginacao(qtde, pag, string){
        $('#paginacao').remove();
        $('#blocoPaginacao').append("<div id='paginacao'></div>");
        $('#paginacao').bootpag({
            total: qtde,
            page: pag,
            maxVisible:5
        }).on('page', function(event, pag){
            $rootScope.atualizarListagens($rootScope.numregistros, pag, $rootScope.campoAtual, string, $rootScope.ent,0, true);
        });
    }
    
    function deuErro() {
        toastr.error("Erro na listagem", "Erro");
    }
        
}]);

module.service('ServiceFuncoes', [function(){
    return {

        dateToData : function(d) {
            var ano = d.substring(0, 4);
            var mes = d.substring(5, 7);
            var dia = d.substring(8, 10);
            var data = dia+"/"+mes+"/"+ano;
            return data;
        }

    };

}]);




    function consulta(reg,pag,campo,order,string, paro,entidade) {
        if(reg == null || reg == ""){ reg = 10; }
        if(pag == null || pag == ""){ pag = 1; }
        if(campo == null || campo == ""){ campo = "nome"; }
        if(order != "asc" && order != "desc"){ order = "asc"; }
        if(string == null){ string = ""; }
        return "/"+entidade+"/listar/"+reg+"/"+pag+"/"+campo+"/"+order+"/"+string;
//      if(order == "desc"){ $scope.tipoOrdem == true; } else { $scope.tipoOrdem == false; }
//        $http.get("/"+entidade+"/listar/"+reg+"/"+pag+"/"+campo+"/"+order+"/"+string)
//            .success(function (data) {
//                return data;
//            })
//            .error(deuErro);
    };
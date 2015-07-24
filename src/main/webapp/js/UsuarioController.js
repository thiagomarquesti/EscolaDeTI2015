module.controller("UsuarioController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function($scope, $http, $routeParams, $location, $timeout){
    function novoUsuario(){
        $scope.usuario = {
            nome : "",
            login : "",
            email : "",
            senha : "",
            status : "ATIVO"
        };
        $scope.isNovo = true;
    }
    
    $scope.novoAdmin = function(){
        novoUsuario();
    };
    
    $scope.verificaLogado = function(){
         $http.get("/login/usuariologado")
           .success(function(data){
               if(!data.idusuario){
                   window.location.href="/login.html";
               }
               else {
                   $scope.nomeUsuario = data.nome;
                   $scope.idUsuario = data.idusuario;
               }
           })
           .error(function(){
               window.location.href="/login.html";
           });
    };
    
    $scope.salvar = function(){
        if($scope.isNovo){
            $http.post("/usuario", $scope.usuario)
               .success(function(){
                   toastr.success("Usuário cadastrado com sucesso!");
                   if($location.path() === "/Usuario/novo"){ 
                       $location.path("/Usuario/listar"); 
                   }
                   else{
                       window.location.href="/login.html";
                   }
               })
               .error(deuErro);
        }
        else {
            $http.put("/usuario/", $scope.usuario)
               .success(function(){
                   toastr.success("Usuário atualizado com sucesso!");
                   $location.path("/Usuario/listar");
               })
               .error(deuErro);
        }

    };

    $scope.atualizar = function(){
       $http.get("/usuario")
           .success(function(data){
               $scope.usuarios = data;
           })
           .error(deuErro);
    };

    $scope.atrasa = function(tempo){
        $timeout(tempo);
    }

    $scope.editar = function(usuario) {
        $location.path("/Usuario/editar/" + usuario.idusuario);
    };
    
    $scope.alteraStatus = function(id) {
        $http.put("/usuario/" + id)
               .success(function(){
                   $scope.atualizar();
               })
               .error(deuErro);
    };
    
    $scope.statusArray =  {"0":"Acesso Liberado", "1":"Acesso Bloqueado", "":"Sem acesso"};
    $scope.corStatus =  {"0":"success", "1":"danger", "":"info"};
    
    $scope.carregar = function(){
        if($location.path() === "/Usuario/novo"){
            novoUsuario();
        }
        else {
            $http.get("/usuario/" + $routeParams.id)
                    .success(function(data){
                        $scope.usuario = data[0];
                        $scope.usuario.rsenha = $scope.usuario.senha;
                        //console.log(data[0]);
                        $scope.isNovo = false;
                    })
                    .error(deuErro);
        }
    };

    $scope.reset = function(form) {
        if(form) {
          form.$setPristine();
          form.$setUntouched();
        }
        novoUsuario();
    };
    
    
    $scope.logout = function(){
        $http.get("/login/usuariologado")
           .success(function(data){
               console.log(data.login);
               var dadosLogin = {"login": data.login, "senha" : data.senha };
               $http.post("/login/efetuarlogout", dadosLogin)
                .success(function() {
                    window.location.href="/login.html";
                }
                )
                .error(deuErro);
           })
           .error(deuErro);
    };
    

    $scope.carregaitensAcesso = function(){
       $http.get("/login/usuariologado/itensdeacesso")
            .success(function(data){
                $scope.itensAcesso = data;
                console.log(data);
                //alert("funcionou");
            })
            .error(erroListarItensAcessoDoMenu);
    };
//-----------------AKI-------------------------------
    function erroListarItensAcessoDoMenu(){
        alert("Atenção, erro ao subir os itens de acesso do usuário! Entre em contato com o Administrador!!");
    }

    
    function deuErro(){
        toastr.error("Algo deu errado. Tente novamente.");
    }
    
    $scope.carregaScript = function(nScript){
        $timeout(function(){
            var script = document.createElement('script');
            script.src = nScript+".js";
            document.getElementsByTagName('head')[0].appendChild(script);
        },10);
    };

}]);

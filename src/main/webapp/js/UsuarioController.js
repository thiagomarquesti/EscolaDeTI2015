module.controller("UsuarioController", ["$scope", "$http", "$routeParams", "$location", function($scope, $http, $routeParams, $location){

    $scope.itensAcesso = [
                            {
                                "id": 1,
                                "nome": "Menu",
                                "rota": "",
                                "items": [
                                        {"id": 2,
                                        "nome": "Cadastro de Usuario",
                                        "rota": "",
                                        "items":[
                                                {"id": 3,
                                                "nome": "Listar Usuário",
                                                "rota": "#/usuario/list",
                                                "items":[]
                                                },
                                                {"id": 4,
                                                "nome": "Novo Usuário",
                                                "rota": "#/usuario/novo",
                                                "items":[]
                                                }
                                                ]
                                        },
                                        {"id": 5,
                                        "nome": "Cadastro de Perfil",
                                        "rota": "",
                                        "items":[
                                                {"id": 6,
                                                "nome": "Listar Perfil",
                                                "rota": "#/perfil/list",
                                                "items":[]
                                                },
                                                {"id": 7,
                                                "nome": "Novo Perfil",
                                                "rota": "#/perfil/novo",
                                                "items":[]
                                                }
                                                ]
                                        }
                                        ]
                            }
                            ];

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
               if(!data.id){
                   window.location.href="/login.html";
               }
               else {
                   $scope.nomeUsuario = data.nome;
                   
               }
           })
           .error(function(){
               window.location.href="/login.html"
           });
    };
    
    $scope.salvar = function(){
        if($scope.isNovo){
            $http.post("/usuario", $scope.usuario)
               .success(function(){
                   alert("Usuário cadastrado com sucesso!");
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
                   alert("Usuário atualizado com sucesso!");
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

    $scope.editar = function(usuario) {
        $location.path("/Usuario/editar/" + usuario.id);
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
    
//    &scope.listaItensAcessoDoMenu = function(){
////        &http.get("/usuariologado/itensdeacesso")
////        .success(function(data){
//            alert("teste");
//            $scope.itensAcesso = [
//    {
//    "id": 1,
//    "title": "1. dragon-breath",
//    "items": []
//  },
//  {
//    "id": 2,
//    "title": "2. moiré-vision",
//    "items": [
//      {
//        "id": 21,
//        "title": "2.1. tofu-animation",
//        "items": [
//          {
//            "id": 211,
//            "title": "2.1.1. spooky-giraffe",
//            "items": []
//          },
//          {
//            "id": 212,
//            "title": "2.1.2. bubble-burst",
//            "items": []
//          }
//        ]
//      },
//      {
//        "id": 22,
//        "title": "2.2. barehand-atomsplitting",
//        "items": []
//      }
//    ]
//  },
//  {
//    "id": 3,
//    "title": "3. unicorn-zapper",
//    "items": []
//  },
//  {
//    "id": 4,
//    "title": "4. romantic-transclusion",
//    "items": []
//  }
//];
//        })
//        .error(erroListarItensAcessoDoMenu);
//}
    
    function erroListarItensAcessoDoMenu(){
        alert("Atenção, erro ao subir os itens de acesso do usuário!");
    }

    
    function deuErro(){
        alert("Algo deu errado. Tente novamente.");
    }
}]);
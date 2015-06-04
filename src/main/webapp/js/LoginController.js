module.controller("LoginController", ["$scope", "$http", function ($scope, $http) {

    function deuErro() {
        alert("Algo deu errado. Tente novamente.");
    }
    function erroLogin() {
        alert("Login ou senha incorretos. Tente novamente.");
    }
    
    function erroBloqueio() {
        alert("Esse usuário está com o acesso bloqueado Procure um administrador.");
    }

    $scope.verificaTelaLogin = function () {
        $http.get("/login/usuariologado")
                .success(function (data) {
                    if (data.idUsuario) {
                        console.log(data.idUsuario);
                        window.location.href = "/";
                    }
                })
                .error(deuErro);
    };

    $scope.verificaUsers = function () {
        $http.get("/usuario")
                .success(function (data) {
                    //console.log(data);
                    if (data[0]) {
                        $scope.yesUser = true;
                    }
                    else {
                        $scope.yesUser = false;
                    }
                })
                .error(deuErro);
    };
    
    $scope.logar = function () {
        $http.get("/login/statusLogin/" + $scope.login.login)
                .success(function (data) {
                    //console.log(data.length);
                    if (data.length > 0) {
                        var statusUsuario = data[0].status;
                        if (statusUsuario == 0) {
                            $http.post("/login/efetuarlogin", $scope.login)
                                    .success(function (data) {
                                        window.location.href = "/";
                                    }
                                    )
                                    .error(erroLogin);
                        }
                        else {
                            erroBloqueio();
                        }
                    }
                    else {
                        erroLogin();
                    }
                })
                .error(deuErro);
    };
}]);
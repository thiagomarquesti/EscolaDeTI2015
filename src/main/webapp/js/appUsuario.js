module = angular.module("usuarioApp", ['ngRoute', 'UserValidation']);

valida = angular.module('UserValidation', []);

valida.directive('passwordMatch', [function () {
        return {
            restrict: 'A',
            scope: true,
            require: 'ngModel',
            link: function (scope, elem, attrs, control) {
                var checker = function () {
                    //get the value of the first password
                    var e1 = scope.$eval(attrs.ngModel);
                    //get the value of the other password  
                    var e2 = scope.$eval(attrs.passwordMatch);
                    return e1 == e2;
                };
                scope.$watch(checker, function (n) {
                    //set the form control to valid if both 
                    //passwords are the same, else invalid
                    control.$setValidity("pwmatch", n);
                });
            }
        };
    }]);

valida.directive('loginUnique', ['$http', function ($http) {
        return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ctrl) {
                elem.on('blur', function () {
                    scope.$apply(function () {
                        if (scope.usuario.login) {
                            if (scope.usuario.id) {
                                var dados = scope.usuario.login + "/" + scope.usuario.id;
                            }
                            else {
                                var dados = scope.usuario.login;
                            }
                            $http.get('/usuario/verificarLogin/' + dados)
                                    .success(function (data) {
                                        ctrl.$setValidity('unlogin', data);
                                    });
                        }
                    });
                });
            }
        };
    }]);

valida.directive('emailUnique', ['$http', function ($http) {
        return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ctrl) {
                elem.on('blur', function () {
                    scope.$apply(function () {
                        if (scope.usuario.email) {
                            if (scope.usuario.id) {
                                var dados = scope.usuario.email + "/" + scope.usuario.id;
                            }
                            else {
                                var dados = scope.usuario.email;
                            }
                            $http.get('/usuario/verificarEmail/' + dados)
                                    .success(function (data) {
                                        ctrl.$setValidity('unemail', data);
                                    });
                        }
                    });
                });
            }
        };
    }]);

valida.directive('verifSenha', [function () {
    return {
        restrict: 'A',
        scope: true,
        require: 'ngModel',
        link: function (scope, elem, attrs, control) {
            var SENHA_REGEXP = RegExp("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%.])");
            var checker = function () {
                var char = scope.$eval(attrs.ngModel);
                return SENHA_REGEXP.test(char);
            };
            scope.$watch(checker, function(n) {
                control.$setValidity("chsenha", n);
            });
        }
    };
}]);

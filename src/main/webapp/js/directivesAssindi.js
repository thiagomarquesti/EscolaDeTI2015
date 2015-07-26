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
                            if (scope.usuario.idusuario) {
                                var dados = scope.usuario.login + "/" + scope.usuario.idusuario;
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
                            if (scope.usuario.idusuario) {
                                var dados = scope.usuario.email + "/" + scope.usuario.idusuario;
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
                if(!scope.isNovo){ SENHA_REGEXP = RegExp(""); }
                var checker = function () {
                    var char = scope.$eval(attrs.ngModel);
                    return SENHA_REGEXP.test(char);
                };
                scope.$watch(checker, function (n) {
                    control.$setValidity("chsenha", n);
                });
            }
        };
    }]);

camera = angular.module('cameraWeb', []);

camera.directive('ngCamera', ['$q','$timeout', function($q, $timeout){
       return {
            'restrict': 'E',
            'scope': {
                'actionMessage': '@',
                'captureMessage': '@',
                'countdown': '@',
                'flashFallbackUrl': '@',
                'overlayUrl': '@',
                'outputHeight': '@',
                'outputWidth': '@',
                'shutterUrl': '@',
                'viewerHeight': '@',
                'viewerWidth': '@',
                'imageFormat': '@',
                'jpegQuality': '@',
                'snapshot': '='
            },
            'templateUrl': '/views/cameraWeb.html',
            'link': link
        };

        function link(scope, element, attrs) {
            /**
             * Set default variables
             */
            scope.libraryLoaded = false;
            scope.cameraLive = false;
            scope.activeCountdown = false;

            /**
             * Set dimensions
             */
            if(scope.viewerHeight === undefined) {
                scope.viewerHeight = 'auto';
            }
            if(scope.viewerWidth === undefined) {
                scope.viewerWidth = 'auto';
            }
            if(scope.outputHeight === undefined) {
                scope.outputHeight = scope.viewerHeight;
            }
            if(scope.outputWidth === undefined) {
                scope.outputWidth = scope.viewerWidth;
            }

            /**
             * Set configuration parameters
             * @type {object}
             */
            Webcam.set({
                width: scope.viewerWidth,
                height: scope.viewerHeight,
                dest_width: scope.outputWidth,
                dest_height: scope.outputHeight,
                image_format: scope.imageFormat,
                jpeg_quality: scope.jpegQuality,
                force_flash: false
            });
            if(scope.flashFallbackUrl !== 'undefined') {
                Webcam.setSWFLocation(scope.flashFallbackUrl);
            }
            Webcam.attach('#ng-camera-feed');

            /**
             * Register WebcamJS events
             */
            Webcam.on('load', function() {
                console.info('library loaded');
                scope.$apply(function() {
                    scope.libraryLoaded = true;
                });
            });
            Webcam.on('live', function() {
                console.info('camera live');
                scope.$apply(function() {
                    scope.cameraLive = true;
                });
            });
            Webcam.on('error', function(error) {
                console.error('WebcameJS directive ERROR: ', error);
            });

            /**
             * Preload the shutter sound
             */
            if(scope.shutterUrl !== undefined) {
                scope.shutter = new Audio();
                scope.shutter.autoplay = false;
                if(navigator.userAgent.match(/Firefox/)) {
                    scope.shutter.src = scope.shutterUrl.split('.')[0] + '.ogg';
                } else {
                    scope.shutter.src = scope.shutterUrl;
                }
            }

            /**
             * Set countdown
             */
            if(scope.countdown !== undefined) {
                scope.countdownTime = parseInt(scope.countdown) * 1000;
                scope.countdownText = parseInt(scope.countdown);
            }
            scope.countdownStart = function() {
                scope.activeCountdown = true;
                scope.countdownPromise = $q.defer();
                scope.countdownTick = setInterval(function() {
                    return scope.$apply(function() {
                        var nextTick;
                        nextTick = parseInt(scope.countdownText) - 1;
                        if(nextTick === 0) {
                            scope.countdownText = scope.captureMessage != null ? scope.captureMessage : 'GO!';
                            clearInterval(scope.countdownTick);
                            scope.countdownPromise.resolve();
                        }else{
                            scope.countdownText = nextTick;
                        }
                    });
                }, 1000);
            };

            /**
             * Get snapshot
             */
            scope.getSnapshot = function() {
                if(scope.countdown !== undefined) {
                    scope.countdownStart();
                    scope.countdownPromise.promise.then(function() {
                        $timeout(function() {
                            scope.activeCountdown = false;
                            scope.countdownText = parseInt(scope.countdown);
                        }, 2000);

                        if(scope.shutterUrl !== undefined) {
                            scope.shutter.play();
                        }

                        Webcam.snap(function(data_uri) {
                            scope.snapshot = data_uri;
                        });
                    });
                } else {
                    if(scope.shutterUrl !== undefined) {
                        scope.shutter.play();
                    }

                    Webcam.snap(function(data_uri) {
                        scope.snapshot = data_uri;
                    });
                }
            };
        }
}]);



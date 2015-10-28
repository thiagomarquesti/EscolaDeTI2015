module.controller("HomeController", ["$scope", "$http", "$routeParams", "$location", "$timeout", function ($scope, $http) {

        var yy;
        var calendarArray = [];
        var monthOffset = [6, 7, 8, 9, 10, 11, 0, 1, 2, 3, 4, 5];
        var monthArray = [["JAN", "Janeiro"], ["FEB", "Fevereiro"], ["MAR", "Março"], ["APR", "Abril"], ["MAY", "Maio"], ["JUN", "Junho"], ["JUL", "Julho"], ["AUG", "Agosto"], ["SEP", "Setembro"], ["OCT", "Outubro"], ["NOV", "Novembro"], ["DEC", "Dezembro"]];
        var letrasArray = ["Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom"];
        var dayArray = ["7", "1", "2", "3", "4", "5", "6"];

        function novoEvento() {
            $scope.eventos = {
                descricao: "",
                datainicial: "",
                datafinal: "",
                visualizarnocalendario: ""
            };
        }

        function getEventosDoCalendario() {
            novoEvento();
            $http.get("/eventos").success(function (data) {
                $scope.eventos = data;
                console.log($scope.eventos);
            }).error(erroNoEvento);
        }

//        function cadastrarevento() {
//            var dia = new Date(parseInt($(this).attr('time')));
//            console.log("Cadastrar Evento do dia: " + dia.getDate());
//            
//        }

        $(document).ready(function () {
            getEventosDoCalendario();
            setTimeout(function () {
                $(document).on('click', '.calendar-day.have-events', activateDay);
                $(document).on('click', '.specific-day', activatecalendar);
                $(document).on('click', '.calendar-month-view-arrow', offsetcalendar);
//                $(document).on('click', '.calendar-day', cadastrarevento);
                $(window).resize(calendarScale);
                calendarSet();
                calendarScale();
            }, 100);
        });

        function calendarScale() {
            $(".calendar").each(function () {
                if ($(this).width() < 400 && !$(this).hasClass('small')) {
                    $(this).addClass('small');
                } else if ($(this).width() > 400 && $(this).hasClass('small')) {
                    $(this).removeClass('small');
                }
            })
        }

        function offsetcalendar() {
            var cm = parseInt($(".calendar").attr('offset'));
            if ($(this).data('dir') == "left") {
                calendarSetMonth(cm - 1);
            } else if ($(this).data('dir') == "right") {
                calendarSetMonth(cm + 1);
            }

        }

        function orderBy(deli, array) {
            var p = array.slice();
            var o = p.length;
            var y, t;
            var temparray = [];
            for (var u = 0; u < o; u++) {
                for (var uu = 0; uu < p.length; uu++) {
//                    if (uu == 0) {
                    t = uu;
                    y = p[uu];
//                    }
//                    else if (parseInt(p[uu][deli].replace('.', '')) < parseInt(y[deli].replace('.', ''))) {
//                        y = p[uu];
//                        t = uu;
//                    }
                }
                temparray.push(y);
                p.splice(t, 1);
            }
            return temparray;
        }

        function calendarSet() {
            $(".calendar").append('<div class="calendar-month-view"><div class="calendar-month-view-arrow" data-dir="left">&lsaquo;</div><p></p><div class="calendar-month-view-arrow" data-dir="right">&rsaquo;</div><div class="letrasDay"></div></div><div class="calendar-holder"><div class="calendar-grid"></div><div class="calendar-specific"><div class="specific-day"><div class="specific-day-info" i="day"></div><div class="specific-day-info" i="month"></div></div><div class="specific-day-scheme"></div></div></div>');

            if ($(this).data("color") == undefined) {
                $(this).data("color", "red");
            }

            var tempdayarray = [];
            for (var i = 0; i < $scope.eventos.length; i++) {
                if ($scope.eventos[i].visualizarnocalendario) {
                    var tempeventarray = [];
                    tempeventarray["datainicial"] = $scope.eventos[i].datainicial.replaceAllCaracteresEspecial('-', '');
                    tempeventarray["datafinal"] = $scope.eventos[i].datafinal.replaceAllCaracteresEspecial('-', '');
                    tempeventarray["descricao"] = $scope.eventos[i].descricao;
                    tempdayarray.push(tempeventarray);
                }
            }

            for (var i = 0; i < $scope.eventos.length; i++) {
                var tempdayevento = [];

                if ($scope.eventos[i].visualizarnocalendario) {
                    if (calendarArray[$scope.eventos[i].datainicial.replaceAllCaracteresEspecial('-', '')] === undefined) {
                        for (var j = 0; j < tempdayarray.length; j++) {
                            var tempevento = [];

                            if ($scope.eventos[i].datainicial.replaceAllCaracteresEspecial('-', '') === tempdayarray[j].datainicial) {
                                tempevento["name"] = tempdayarray[j].descricao;
//                                tempevento["start"] = tempdayarray[j].datainicial;
//                                tempevento["end"] = tempdayarray[j].datafinal;
                                tempdayevento.push(tempevento);
                            }
                        }
                        calendarArray[$scope.eventos[i].datainicial.replaceAllCaracteresEspecial('-', '')] = tempdayevento;
                    }
                }
            }

            $(".calendar [data-role=day]").remove();
            calendarSetMonth();
        }

        String.prototype.replaceAllCaracteresEspecial = function (de, para) {
            var str = this;
            var pos = str.indexOf(de);
            while (pos > -1) {
                str = str.replace(de, para);
                pos = str.indexOf(de);
            }
            return (str);
        }

        function activateDay() {
            $(this).parents('.calendar').addClass('spec-day');
            var di = new Date(parseInt($(this).attr('time')));
            var strtime = $(this).attr('strtime');
            var d = new Object();
            d.day = di.getDate();
            d.month = di.getMonth();
            d.events = calendarArray[strtime];
            d.tocalendar = tocalendar;
            d.tocalendar();
        }
        var tocalendar = function () {
            $(".specific-day-info[i=day]").html(this.day);
            $(".specific-day-info[i=month]").html(monthArray[this.month][0]);
            if (this.events !== undefined) {
                var ev = orderBy('start', this.events);
                for (var o = 0; o < ev.length; o++) {
                    $(".specific-day-scheme").append('<div class="specific-day-scheme-event"><p>' + ev[o]['name'] + '</p></div>'); //<p data-role="dur">' + ev[o]['start'] + ' - ' + ev[o]['end'] + '</p><p data-role="loc">' + ev[o]['location'] + '</p></div>'); // Monta os eventos do dia clicado.
                }
            }
        }
        function activatecalendar() {
            $(this).parents('.calendar').removeClass('spec-day');
            $(".specific-day-scheme").html('');
        }

        function calendarSetMonth(offset) {
            $(".calendar-grid").html('');
            $(".letrasDay").html('');
            var d = new Date();
            var c = new Date();
            var e = new Date();
            if (offset !== undefined) {
                d.setMonth(d.getMonth() + offset);
                e.setMonth(e.getMonth() + offset);
                $(".calendar").attr('offset', offset);
            } else {
                $(".calendar").attr('offset', 0);
            }
            $(".calendar .calendar-month-view p").text(monthArray[d.getMonth()][1] + ' ' + d.getFullYear());
            d.setDate(1);
            if (dayArray[d.getDay()] == 1) {
                d.setDate(d.getDate() - 7);
            } else {
                d.setDate(d.getDate() - dayArray[d.getDay()] + 1);
            }
            for (var i = 0; i < 7; i++) {
                var dias_Semana = $('<div>' + letrasArray[i] + '</div>');
                $(".letrasDay").append(dias_Semana);
            }

            for (var i = 0; i < 42; i++) {
                d.setDate(d.getDate() + i);
                var cal_day = $('<div class="calendar-day"><div class="date-holder">' + d.getDate() + '</div></div>');
                if (d.getMonth() !== e.getMonth()) {
                    cal_day.addClass('other-month');
                }
                if (d.getTime() == c.getTime()) {
                    cal_day.addClass('this-day');
                }

                var mes;
                if ((d.getMonth() + 1) < 10) {
                    mes = '0' + (d.getMonth() + 1);
                } else {
                    mes = (d.getMonth() + 1);
                }

                var dia;
                if (d.getDate() < 10) {
                    dia = '0' + d.getDate();
                } else {
                    dia = d.getDate();
                }

                var strtime = d.getFullYear() + '' + mes + '' + dia;
                if (calendarArray[strtime] !== undefined) {
                    cal_day.addClass('have-events');
                }
                var cal_day_eventholder = $('<div class="event-notif-holder"></div>');
                if (calendarArray[strtime] != undefined) {
                    for (var u = 0; u < 5 && u < calendarArray[strtime].length; u++) {
                        cal_day_eventholder.append('<div class="event-notif"></div>') // Mostra a quantidade de eventos no dia.
                    }
                }
                cal_day.attr('strtime', strtime);
                cal_day.attr('time', d.getTime());
                cal_day.prepend(cal_day_eventholder);

                $(".calendar-grid").append(cal_day);
                d.setDate(d.getDate() - i);
            }
        }
        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function erroNoEvento() {
            toastr.error("Erro ao carregar os eventos.");
        }


    }]);
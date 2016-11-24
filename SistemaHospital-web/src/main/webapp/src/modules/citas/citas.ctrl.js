/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function (ng) {
    var mod = ng.module("citasModule");

    mod.controller("citasCtrl", ['$scope', '$state', '$stateParams', '$http', 'citasContext', function ($scope, $state, $stateParams, $http, context) {
        load = function () {
            $http.get(context).then(function (response) {
                $scope.citas = response.data;
                console.log(response.data);
            }, responseError);
        }

        load();

        this.deleteRecord = function (cita) {
            return $http.delete(context + "/" + cita.id)
                .then(function () {
                    load();
                }, responseError)
        }

        if ($stateParams.citaID !== null && $stateParams.citaId !== undefined) {
            // toma el id del parámetro
            id = $stateParams.citaId;
            // obtiene el dato del recurso REST
            $http.get(context + "/" + id)
                .then(function (response) {
                    // $http.get es una promesa
                    // cuando llegue el dato, actualice currentRecord
                    console.log(response)
                    $scope.fechaEdit = new Date();
                    var currentRecord = response.data;
                    $scope.fechaEdit.setTime(currentRecord.hora)
                    $scope.id = currentRecord.id;
                    $scope.fecha = currentRecord.fecha;
                    $scope.hora = new Date(currentRecord.hora).getHours();
                    $scope.duracion = currentRecord.duracion;
                    $scope.medico = currentRecord.medico;
                    $scope.paciente = currentRecord.paciente;

                    $scope.habilitada = currentRecord.habilitada;

                    $scope.terminado = currentRecord.terminado;


                }, responseError);

            // el controlador no recibió un editorialId
        } else {
            // el registro actual debe estar vacio
            $scope.currentRecord = {
                id: undefined /*Tipo Long. El valor se asigna en el backend*/,
                name: '' /*Tipo String*/
            };

            $scope.alerts = [];
        }

            
            this.saveCita = function(){
                if(!$scope.id || !$scope.fecha || !$scope.hora || !$scope.duracion || !$scope.medico || !$scope.paciente) alert ("No puede dejar ningún campo vacio.");
                
                if (isNaN($scope.hora)) {alert("La Hora debe ser numérica.");}
                if (isNaN($scope.duracion)) {alert("La Duración debe ser numérica.");}

                else{
                    console.log($scope.medico + " : " + $scope.paciente)
                    console.log($scope.fecha.getTime() +"SE CREA UNA CITA CON ESTE TIEMPO")
                    var hora = $scope.fecha.getTime() + ($scope.hora*3600000);
                    console.log(hora);
                    var cita = {
                        "id" :  $scope.id,
                        "fecha" :hora,
                        "hora" : hora,
                        "duracion" : $scope.duracion,
                        "medico": $scope.medico,
                        "paciente": $scope.paciente,
                        "habilitada": $scope.habilitada

                    };
                    cita = JSON.stringify(cita);
                    console.log(cita);
                    return $http.post(context, cita.toString())
                            .then(function(){
                                $state.go('listaCitas');

                    }, responseError)
            }

        }
        
        
        
        
        
        
        
        

        this.turnMillisToDate = function (dateLong) {
            var d = new Date(dateLong);
            return d.getUTCDate() + "/" + (d.getUTCMonth() + 1) + "/" + d.getFullYear();
        }

        this.turnMillisToHour = function (dateLong) {
            var d = new Date(dateLong);
            return d.getHours() + ":" + d.getMinutes() + "0";
        }
        
        
        this.editCitaFinal = function () {
            if (!$scope.id || !$scope.hora || !$scope.duracion || !$scope.medico || !$scope.paciente) {
                alert("No puede dejar ningún campo vacio.");
                return;
            }

            if (isNaN($scope.hora)) {
                alert("La hora debe ser un numérica.");
                return;
            }
            if (isNaN($scope.duracion)) {
                alert("La duracion debe ser numérica.");
                return;
            }

            else {
                console.log("Se va a editar la cita" + $scope.id);
                $scope.fechaEdit.setHours(0);
                var cita =
                {
                    "id": $scope.id,
                    "fecha": $scope.fechaEdit.setHours(0) + ($scope.hora*3600000),
                    "hora": $scope.fechaEdit.setHours(0) + ($scope.hora*3600000),
                    "duracion": $scope.duracion,
                    "medico": $scope.medico,
                    "paciente": $scope.paciente,
                    "habilitada": $scope.habilitada
                };
//                cita = JSON.stringify(cita);
                console.log(cita);
                return $http.put(context + "/" + $stateParams.citaId, cita)
                    .then(function () {
                        $state.go('listaCitas');
                    }, responseError)
            }
        };
        
        this.terminarCita = function (cita) {
            console.log("Metodo terminar");
            $http.put(context + "/" + cita.id+"/terminar")
                .then(function () {
                    $state.reload('listaCitas');
                }, responseError)
        };


        this.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };

        function showMessage(msg, type) {
            var types = ["info", "danger", "warning", "success"];
            if (types.some(function (rc) {
                    return type === rc;
                })) {
                $scope.alerts.push({type: type, msg: msg});
            }
        }

        this.showError = function (msg) {
            showMessage(msg, "danger");
        };

        this.showSuccess = function (msg) {
            showMessage(msg, "success");
        };

        var self = this;

        function responseError(response) {

            self.showError(response.data);
        }
    }]);

})(window.angular);

(function (ng) {
    var mod = ng.module("patientModule");
    mod.controller("patientCtrl", ['$scope', '$state', '$stateParams', '$http', 'patientContext', function ($scope, $state, $stateParams, $http, context){

           loadDocs = function (){
                $http.get(context).then(function(response){
                    console.log(response.data);
                    $scope.patient = response.data;    
                    }, responseError);
            }
            loadDocsCitas = function(){
                $http.get(context+"/"+$stateParams.ptnID+"/citaspaciente"). then(function(response){
                    console.log(response.data);
                    $scope.citas = response.data;
                    }, responseError);
            }
            
            
            loadDocs();
            loadDocsCitas();
            
            this.turnMillisToHour = function (dateLong) {
            var d = new Date(dateLong);
            return d.getHours() + ":" + d.getMinutes();
        }

            this.deleteRecord = function (ptn){
                return $http.delete(context+"/"+ptn.id)
                        .then(function () {
                            loadDocs();
                        }, responseError)
            }
            
            if ($stateParams.ptnID !== null && $stateParams.ptnID !== undefined) {

            // toma el id del parámetro
            id = $stateParams.ptnID;
            // obtiene el dato del recurso REST
            $http.get(context + "/" + id)
                .then(function (response) {
                    // $http.get es una promesa
                    // cuando llegue el dato, actualice currentRecord
                    var currentRecord = response.data;
                    $scope.nombre = currentRecord.name;
                    $scope.cedula = currentRecord.id;
                    $scope.edad = currentRecord.edad;
                    $scope.tipoSangre = currentRecord.tipoSangre;
                    $scope.eps = currentRecord.eps;
                    $scope.sexo = currentRecord.sexo;
                    $scope.tipoDocumento = currentRecord.tipoDocumento;
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

            
            
            this.savePatient = function () {
                if (!$scope.nombre || !$scope.cedula || !$scope.edad ||  !$scope.eps || !$scope.tipoDocumento )
                {
                    alert("No puede dejar ningún campo vacio.");
                    return;
                }
                if (isNaN($scope.cedula)){
                    alert("La cédula debe ser numérica.");
                return;
                }
                if (isNaN($scope.edad)){
                    alert("La edad debe ser un numérica.");
                    return;
                }
                
                
                else{
                    var ptn = 
                    {
                        "name" :  $scope.nombre,    
                        "id" : $scope.cedula,      
                        "edad" : $scope.edad,   
                        "sexo" : $scope.sexo,
                        "tipoSangre" : $scope.tipoSangre,
                        "eps" : $scope.eps,
                        "tipoDocumento": $scope.tipoDocumento
                    };
                    ptn = JSON.stringify(ptn);
                    console.log(ptn);
                    return $http.post(context, ptn.toString())
                        .then(function () {
                            $state.go('patientListforpatient');
                        }, responseError)
                }
            }
            
            this.editFinalPatient = function () {
                if (!$scope.nombre || !$scope.cedula || !$scope.sexo || !$scope.edad|| !$scope.tipoSangre || !$scope.eps || !$scope.tipoDocumento){
                    alert("No puede dejar ningún campo vacio.");
                    return;
                }
                if (isNaN($scope.cedula)){
                    alert("La cédula debe ser numérica.");
                    return;
                }
                if (isNaN($scope.edad)){
                    alert("La edad debe ser un numérica.");
                    return;
                }
                
                else{
                    var ptn = 
                    {
                        "name" :  $scope.nombre,    
                        "id" : $scope.cedula,      
                        "edad" : $scope.edad,    
                        "sexo" : $scope.sexo,
                        "tipoSangre" : $scope.tipoSangre,
                        "eps" : $scope.eps,
                        "tipoDocumento": $scope.tipoDocumento
                    };
                    ptn = JSON.stringify(ptn);
                    console.log(ptn);
                    return $http.put(context+"/"+ $stateParams.ptnID, ptn.toString())
                        .then(function () {
                            $state.go('patientList');
                        }, responseError)
                }
            }
            
//            this.savePatientForPatient = function () {
//                if (!$scope.nombre || !$scope.cedula || !$scope.sexo || !$scope.edad || !$scope.tipoSangre || !$scope.eps )
//                {
//                    alert("No puede dejar ningún campo vacio.");
//                    return;
//                }
//                if (isNaN($scope.cedula)){
//                    alert("La cédula debe ser numérica.");
//                return;
//                }
//                if (isNaN($scope.edad)){
//                    alert("La edad debe ser un numérica.");
//                    return;
//                }
//                
//                
//                else{
//                    var ptn = 
//                    {
//                        "name" :  $scope.nombre,    
//                        "id" : $scope.cedula,      
//                        "edad" : $scope.edad,   
//                        "sexo" : $scope.sexo,
//                        "tipoSangre" : $scope.tipoSangre,
//                        "eps" : $scope.eps
//                    };
//                    ptn = JSON.stringify(ptn);
//                    console.log(ptn);
//                    return $http.post(context, ptn.toString())
//                        .then(function () {
//                            $state.go('patientListforpatient');
//                        }, responseError)
//                }
//            }
            
            this.editFinalPatientForPatient = function () {
                if (!$scope.nombre || !$scope.cedula || !$scope.sexo || !$scope.edad|| !$scope.tipoSangre || !$scope.eps || !$scope.tipoDocumento){
                    alert("No puede dejar ningún campo vacio.");
                    return;
                }
                if (isNaN($scope.cedula)){
                    alert("La cédula debe ser numérica.");
                    return;
                }
                if (isNaN($scope.edad)){
                    alert("La edad debe ser un numérica.");
                    return;
                }
                
                else{
                    var ptn = 
                    {
                        "name" :  $scope.nombre,    
                        "id" : $scope.cedula,      
                        "edad" : $scope.edad,    
                        "sexo" : $scope.sexo,
                        "tipoSangre" : $scope.tipoSangre,
                        "eps" : $scope.eps,
                        "tipoDocumento": $scope.tipoDocumento
                    };
                    ptn = JSON.stringify(ptn);
                    console.log(ptn);
                    return $http.put(context+"/"+ $stateParams.ptnID, ptn.toString())
                        .then(function () {
                            $state.go('patientListforpatient');
                        }, responseError)
                }
            }

             this.getCitasPaciente = function (x){
//                 if(!$scope.cedula)
//                 {
//                    alert("La Cedula no puede ser vacia.");
//                    return;
//                 }
//                 else{
                     return $http.get(context+"/"+"citaspaciente"+ "/"+x.paciente)
                     
                        .then(function () {
                            $state.go('patientListforpatient');
                        }, responseError)
//                 }
             }   
           
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
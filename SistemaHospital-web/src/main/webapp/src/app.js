(function (ng) {

    var app = ng.module("mainApp", [
        "ui.router",
        "doctorModule",
        "ngMessages",
        "patientModule",
        "especialidadModule",
        "consultorioModule", 
        "consultaHistoricaModule", 
        "citasModule"
    ]);
    
    app.controller("appCtrl", function ($scope, $resource, $window, $cookies) {
        $window.cookies = $cookies;
        $scope.$on("$stateChangeSuccess", function (event, newState) {
            $scope.currentState = newState.name;
        });
        console.log("HERE");
    });

    app.config(['$logProvider', function ($logProvider) {
            $logProvider.debugEnabled(true);
        }]);

    app.config(['$urlRouterProvider', function ($urlRouterProvider) {
            $urlRouterProvider.otherwise('/');
        }]);

  
})(window.angular);

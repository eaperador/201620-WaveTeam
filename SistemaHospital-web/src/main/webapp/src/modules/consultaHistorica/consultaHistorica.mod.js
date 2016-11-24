/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function (ng) {
     
var mod = ng.module("consultaHistoricaModule", ["ngMessages"]);
mod.constant("consultaHistoricaContext", "api/consultasHistoricas");

mod.config(['$stateProvider', '$urlRouterProvider', function($stateProvider,$urlRouterProvider){
    var basePath = 'src/modules/consultaHistorica/';
    $urlRouterProvider.otherwise("/consultaHistoricaList");
    $stateProvider.state('consultaHistoricaList', {
                url: '/consultaHistorica',
                views: {
                    'mainView': {
                        controller: 'consultaHistoricaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'consultaHistorica.list.html'
                    }
                }
            }).state('consultaHistoricaCreate', {
                url: '/consultaHistorica/create',
                views: {
                    'mainView': {
                        controller: 'consultaHistoricaCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'consultaHistorica.create.html'
                    }
                }
            });
}]);

})(window.angular);

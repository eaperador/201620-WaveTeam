/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 (function (ng) {
     
var mod = ng.module("especialidadModule", ["ngMessages"]);
mod.constant("especialidadContext", "api/especialidades");

mod.config(['$stateProvider', '$urlRouterProvider', function($stateProvider,$urlRouterProvider){
    var basePath = 'src/modules/especialidad/';
    $urlRouterProvider.otherwise("/especialidadList");
    $stateProvider.state('especialidadList', {
                url: '/especialidad',
                views: {
                    'mainView': {
                        controller: 'especialidadCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'especialidad.list.html'
                    }
                }
            }).state('especialidadCreate', {
                url: '/especialidad/create',
                views: {
                    'mainView': {
                        controller: 'especialidadCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'especialidad.create.html'
                    }
                }

            }).state('especialidadSearch', {
                url: '/especialidad/:especialidadId',
                param:{
                        especialidadId: null
                },
                views: {
                    'mainView': {
                        controller: 'especialidadCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'especialidad.search.html'
                    }
                }

            }).state('especialidadEdit', {
                url: '/especialidad/:especialidadId',
                param: {
                    especialidadId: null
                },
                views: {
                    'mainView': {
                        controller: 'especialidadCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'especialidad.create.html'
                    }
                }
            });
}]);

})(window.angular);
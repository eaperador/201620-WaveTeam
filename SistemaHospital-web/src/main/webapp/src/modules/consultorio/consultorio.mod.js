/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
(function(ng){
    var mod = ng.module("consultorioModule", ['ngMessages']);
    mod.constant("consultorioContext", "api/consultorios");
    mod.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider){
        var basePath = 'src/modules/consultorio/';
        $urlRouterProvider.otherwise("/");
        $stateProvider.state('getConsultorios',{
            url:"/consultorios",
            views: {
                'mainView':{
                    controller:'consultoriosCtrl',
                    controllerAs:'ctrl',
                    templateUrl:basePath+'consultorios.get.html'
                }
            }

        }).state('infoConsultorios',{
            url:"/consultorios/servicios",
            views: {
                'mainView':{
                    controller:'consultoriosCtrl',
                    controllerAs:'ctrl',
                    templateUrl:basePath+'consultorio.info.html'
                }
            }
        }).state('crearConsultorio',{
            url:"/consultorios/crear",
            views: {
                'mainView':{
                    controller:'consultoriosCtrl',
                    controllerAs:'ctrl',
                    templateUrl:basePath+'consultorios.create.html'
                }
            }
        }).state('actualizarConsultorio',{
            url:"/consultorios/:consultorioId/actualizar",
            param: {
                consultorioId: null
            },
            views: {
                'mainView':{
                    controller:'consultoriosCtrl',
                    controllerAs:'ctrl',
                    templateUrl:basePath+'consultorio.actualizar.html'
                }
            }
        }).state('buscarConsultorio',{
            url:"/consultorios/:consultorioId",
            param:{
                consultorioId: null
            },
            views:{
                'mainView':{
                    controller:'consultoriosCtrl',
                    controllerAs:'ctrl',
                    templateUrl:basePath+'consultorio.buscar.html'
                }                
            }
            
        }
                
                    );
    }]);

})(window.angular);
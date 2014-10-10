'use strict';

var terminplanerApp = angular.module('terminplaner', ['ngRoute', 'ngResource', 'ui.bootstrap', 'terminplaner.services']);

terminplanerApp.serviceUrl = function (relativePath) {
    return window.terminplanerServiceUrl + relativePath;
};

terminplanerApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/index', {templateUrl: 'partials/index.html'}).
        when('/termin/neu', {templateUrl: 'partials/termin_neu.html', controller: 'TerminNeuController'}).
/*
        when('/rampe', {templateUrl: 'partials/rampe.xhtml', controller: 'RampeController'}).
        when('/logistik', {templateUrl: 'partials/logistik.xhtml', controller: 'LogistikController'}).
        when('/produkte', {templateUrl: 'partials/produkte.xhtml', controller: 'ProductManagementController'}).
*/
        otherwise({redirectTo: '/index'});
}]);

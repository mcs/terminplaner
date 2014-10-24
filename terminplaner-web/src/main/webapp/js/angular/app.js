'use strict';

var terminplanerApp = angular.module('terminplaner', ['ngRoute', 'ngResource', 'ui.bootstrap', 'terminplaner.services']);

terminplanerApp.serviceUrl = function (relativePath) {
    return window.terminplanerServiceUrl + relativePath;
};

terminplanerApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/index', {templateUrl: 'partials/index.html'}).
        when('/user/login', {templateUrl: 'partials/user_login.html', controller: 'UserLoginController'}).
        when('/termin/neu', {templateUrl: 'partials/termin_neu.html', controller: 'TerminNeuController'}).
        when('/termine', {templateUrl: 'partials/termine.html', controller: 'TermineController'}).
        otherwise({redirectTo: '/index'});
}]);

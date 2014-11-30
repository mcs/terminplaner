'use strict';

var terminplanerApp = angular.module('terminplaner', ['ngRoute', 'ngResource', 'hateoas', 'ngCookies', 'ui.bootstrap', 'terminplaner.services']);

terminplanerApp.serviceUrl = function (relativePath) {
    return window.terminplanerServiceUrl + relativePath;
};

terminplanerApp.config(['$routeProvider', 'HateoasInterceptorProvider', 'HateoasInterfaceProvider', function ($routeProvider, HateoasInterceptorProvider, HateoasInterfaceProvider) {
    $routeProvider.
        when('/index', {templateUrl: 'partials/index.html'}).
        when('/user/login', {templateUrl: 'partials/user_login.html', controller: 'UserLoginController'}).
        when('/termine/neu', {templateUrl: 'partials/termin_neu.html', controller: 'TerminNeuController'}).
        when('/termine', {templateUrl: 'partials/termine.html', controller: 'TermineController'}).
        otherwise({redirectTo: '/index'});

    HateoasInterfaceProvider.setLinksKey("_links");
    HateoasInterceptorProvider.transformAllResponses();
}]);

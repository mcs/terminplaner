'use strict';

var terminfinderApp = angular.module('terminfinder', ['ngRoute']);

terminfinderApp.serviceUrl = function (relativePath) {
    return window.terminfinderServiceUrl + relativePath;
};

terminfinderApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/index', {templateUrl: 'partials/index.html'}).
/*
        when('/bar', {templateUrl: 'partials/bar.xhtml', controller: 'CocktailbarController'}).
        when('/rampe', {templateUrl: 'partials/rampe.xhtml', controller: 'RampeController'}).
        when('/logistik', {templateUrl: 'partials/logistik.xhtml', controller: 'LogistikController'}).
        when('/produkte', {templateUrl: 'partials/produkte.xhtml', controller: 'ProductManagementController'}).
*/
        otherwise({redirectTo: '/index'});
}]);

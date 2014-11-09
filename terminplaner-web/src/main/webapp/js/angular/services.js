'use strict';

angular.module('terminplaner.services', ['ngResource'])
    .factory('Links', function () {
        return {
            links: []
        };
    })
    .factory('Termine', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/termine/:id"), {}, {
            update: {
                method: 'PUT'
            }
        });
    })
    .factory('Invitations', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/invitations/:id"), {}, {
            update: {
                method: 'PUT'
            }
        });
    })
    .factory('User', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/user"), {}, {
            update: {
                method: 'PUT'
            }
        });
    })
    .factory('Login', function ($resource, $http) {
        return {
            send: function (username, password) {
                var result = $http.post(terminplanerApp.serviceUrl("/login"), {username: username, password: password}, null);
                console.log("Login result = %o", result);
                return result;
            },
            auth: function (authCode) {
                var result = $http.post(terminplanerApp.serviceUrl("/login"), {auth: authCode}, null);
                console.log("Login result = %o", result);
                return result;
            },
            logout: function (authCode) {
                var result = $http.post(terminplanerApp.serviceUrl("/login/logout"), {auth: authCode}, null);
                console.log("Logout result = %o", result);
                return result;
            }
        };
    })
    .factory('Logout', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/logout"), {}, {
            update: {
                method: 'PUT'
            }
        });
    })
;

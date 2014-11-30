'use strict';

angular.module('terminplaner.services', ['ngResource', 'hateoas'])
    .factory('Root', ["$resource", function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/"), {}, {
            update: {
                method: 'PUT'
            }
        });
    }])
    .factory('Login', ["$resource", "$http", function ($resource, $http) {
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
            }
        };
    }])
    .factory('Termine', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/termin/termine/:id"), {}, {
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
    .factory('Logout', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/logout"), {}, {
            update: {
                method: 'PUT'
            }
        });
    })
;

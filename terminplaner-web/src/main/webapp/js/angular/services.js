'use strict';

angular.module('terminplaner.services', ['ngResource'])
    .factory('Termine', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/termine/:uuid"), {}, {
            update: {
                method: 'PUT'
            }
        });
    })
    .factory('Termin', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/termine/:target"), {}, {
            update: {
                method: 'PUT'
            }
        });
    });

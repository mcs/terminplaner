'use strict';

angular.module('terminfinderServices', ['ngResource'])
    .factory('Terminplan', function ($resource) {
        return $resource(terminfinderApp.serviceUrl("/products/:productId"), {}, {
        });
    })
    .factory('Order', function ($resource) {
        var order = $resource(terminfinderApp.serviceUrl("/orders/:target"), {}, {
            update: {
                method: 'PUT'
            }
        });
        //noinspection JSUnusedGlobalSymbols
        order.prototype.displayState = function () {
            return bundle[this.state];
        };
        return order
    });

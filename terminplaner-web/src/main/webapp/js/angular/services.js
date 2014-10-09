'use strict';

angular.module('terminplanerServices', ['ngResource'])
    .factory('Terminplan', function ($resource) {
        return $resource(terminplanerApp.serviceUrl("/products/:productId"), {}, {
        });
    })
    .factory('Order', function ($resource) {
        var order = $resource(terminplanerApp.serviceUrl("/orders/:target"), {}, {
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

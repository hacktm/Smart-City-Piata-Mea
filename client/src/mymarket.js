'use strict';

(function() {

    var app = angular.module("mymarket", ["ngRoute"]);

    app.config(function($routeProvider) {
        $routeProvider
            .when("/", {
                templateUrl: "views/main.html",
                controller: "MainCtrl"
            })
            .when("/market/:idMarket/product/:idProduct", {
                templateUrl: "views/product.html",
                controller: "ProductCtrl"
            })
    });

} ());
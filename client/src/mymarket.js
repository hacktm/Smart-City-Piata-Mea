'use strict';

(function() {

    var app = angular.module("mymarket", ["ngRoute"]);

    app.config(function($routeProvider) {
        $routeProvider
            .when("/", {
                templateUrl: "views/main.html",
                controller: "MainCtrl"
            })
            .when("/market/:idMarket", {
                templateUrl: "views/markets.html",
                controller: "MarketCtrl"
            })
            .when("/market/:idMarket/product/:idProduct", {
                templateUrl: "views/product.html",
                controller: "ProductCtrl"
            })
            .when("/user/profile/:idUser", {
                templateUrl: "views/profile.html",
                controller: "ProfileCtrl"
            })
            .when("/market/:idMarket/productEdit/:idProduct", {
                templateUrl: "views/product_edit.html",
                controller: "ProductEditCtrl"
            })
            .otherwise({redirectTo: "/"})
    });

} ());
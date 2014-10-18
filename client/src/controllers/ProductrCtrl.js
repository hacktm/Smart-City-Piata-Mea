'use strict';

(function(app) {

    var ProductCtrl = function($routeParams, $scope) {
        var idMarket = $routeParams.idMarket;
        var idProduct = $routeParams.idProduct;
        console.log("products:", idMarket, idProduct);

        $scope.test = function() {
            console.log("Clicked me!");
        };

        $scope.arr = [1,2,3,4,5];
    };

    app.controller("ProductCtrl", ProductCtrl);

} (angular.module("mymarket")));
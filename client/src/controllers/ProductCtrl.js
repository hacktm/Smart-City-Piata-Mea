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

        $('.tipped').tooltip();
        $('.popovered').popover();

        var userName = "Andrei Avram";
        $scope.prices = [{
            name: userName,
            value: 3.4
        }];
        $scope.addPrice = function() {
            $scope.prices.push({name: userName, value: $scope.newPrice});
        }
    };

    app.controller("ProductCtrl", ProductCtrl);

} (angular.module("mymarket")));
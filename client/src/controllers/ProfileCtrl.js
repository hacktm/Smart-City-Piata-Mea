/**
 * Created by andrei on 18.10.2014.
 */
'use strict';

(function(app) {

    var ProfileCtrl = function($routeParams, $scope) {
        var idMarket = $routeParams.idMarket;
        var idProduct = $routeParams.idProduct;
        console.log("products:", idMarket, idProduct);

        $scope.test = function() {
            console.log("Clicked me!");
        };

        $scope.arr = [1,2,3,4,5];

        $('.tipped').tooltip();
    };

    app.controller("ProfileCtrl", ProfileCtrl);

} (angular.module("mymarket")));
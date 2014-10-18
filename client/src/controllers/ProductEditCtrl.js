/**
 * Created by andrei on 18.10.2014.
 */
'use strict';

(function(app) {

    var ProductEditCtrl = function($routeParams, $scope) {
        var idMarket = $routeParams.idMarket;
        var idProduct = $routeParams.idProduct;
        console.log("products:", idMarket, idProduct);

    };

    app.controller("ProductEditCtrl", ProductEditCtrl);

} (angular.module("mymarket")));
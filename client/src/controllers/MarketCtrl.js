'use strict';

(function(app) {

    var MarketCtrl = function($scope, $http, $routeParams) {
        $scope.idMarket = $routeParams.idMarket;
        $scope.query = "";

        $http.get("/api/market/1/products.json")
            .success(function(data) {
                $scope.products = data;
            })
            .error(function() {
                toastr.notify("Produsele ")
            })
    };
    app.controller("MarketCtrl", MarketCtrl)
} (angular.module("mymarket")));
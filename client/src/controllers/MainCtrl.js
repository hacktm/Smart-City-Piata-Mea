'use strict';

(function(app) {

    var MainCtrl = function($scope, $http) {
        $http.get("/api/market/1/products.json")
            .success(function(data) {
                $scope.products = data;
            })
            .error(function() {
                toastr.notify("Produsele ")
            })
    };

    app.controller("MainCtrl", MainCtrl);
} (angular.module("mymarket")));
/**
 * Created by andrei on 18.10.2014.
 */
'use strict';

(function(app) {

    var ProductEditCtrl = function($routeParams, $scope, $http, $location) {
        var idMarket = $routeParams.idMarket;
        var idProduct = $routeParams.idProduct;

        $http.get("/api/product/" + idProduct)
            .success(function(data) {
                $scope.product = data;
            })
            .error(function() {
                toastr.error("Produsul nu a putut fi citit de pe server");
            });

        $scope.updateProduct = function() {
            toastr.success("Produsul a fost editat cu success");
            $location.path("/market/" + idMarket);
        };
    };

    app.controller("ProductEditCtrl", ProductEditCtrl);

} (angular.module("mymarket")));
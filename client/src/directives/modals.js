/**
 * Created by andrei on 19.10.2014.
 */
'use strict';

(function(app) {

    var modals = function($http) {
        return {
            restrict: "AE",
            templateUrl: "views/directives/modals.html",
            link: function(scope) {
                scope.newProd = {
                    marketId: scope.idMarket
                };

                scope.productAdd = function() {
                    $http.put("/api/product", scope.newProd)
                        .success(function() {
//                            console.log(JSON.stringify(scope.newProd));
                            toastr.success("Produsul a fost salvat");
                            $("#addproduct_modal").modal("hide");
                            scope.products.push(scope.newProd);
                        })
                        .error(function() {
                            toastr.error("Produsul nu poate fi salvat.");
                        })
                };

                scope.userAdd = function() {
                    $http.put("/api/user", scope.user)
                        .success(function() {
                            toastr.success("Te-ai inregistrat cu success!");
                            $("#signup_modal").modal("close");
                        })
                        .error(function() {
                            toastr.error("A aparut o problema la inregistrare.");
                        })
                };

                scope.login = function() {
                    $http.post("/api/", scope.login)
                        .success(function() {
                           // toastr.success("Ai");
                            $("#signin_modal").modal("close");
                        })
                        .error(function() {
                            toastr.error("A aparut o problema, incearca mai tarziu.");
                        })
                };
            }
        }
    };

    app.directive("modals", modals)
} (angular.module("mymarket")));
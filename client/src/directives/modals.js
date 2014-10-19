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
                scope.productAdd = function() {
                    $http.post("/api/market" + scope.idMarket + "/product", scope.newProd)
                        .success(function() {
                            toastr.success("Produsul a fost salvat");
                            $("#addproduct_modal").modal("close");
                        })
                        .error(function() {
                            toastr.error("Produsul nu poate fi salvat.");
                        })
                }
            }
        }
    };

    app.directive("modals", modals)
} (angular.module("mymarket")));
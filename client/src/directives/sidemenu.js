'use strict';

(function(app) {

    var sidemenu = function($http) {
        return {
            restrict:"AE",
            templateUrl: "views/directives/sidemenu.html",
            link: function(scope) {
                $http.get("/api/market.json")
                    .success(function(data) {
                        scope.markets = data;
                    })
                    .error(function() {
                        toastr.error("Pietele nu au putut fi citite de pe server");
                    });

                scope.isActive = function(id) {
                    return scope.idMarket == id;
                }
            }
        }
    };

    app.directive("sidemenu", sidemenu)
} (angular.module("mymarket")));
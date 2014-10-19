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
                        $.each(data, function(i,el){
                            if(el.id == scope.idMarket)
                                scope.activeMarket = el.location;
                        });

                        console.log("market",scope.activeMarket);
                        $("#static_map").attr("src","https://maps.googleapis.com/maps/api/staticmap?center="+scope.activeMarket+"&zoom=16&size=250x180&maptype=roadmap&markers=size:big%7Ccolor:red%7C"+scope.activeMarket);
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
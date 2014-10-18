'use strict';

(function(app) {

    var sidemenu = function() {
        return {
            restrict:"AE",
            templateUrl: "views/directives/sidemenu.html",
            link: function(scope) {
                scope.markets = [
                    {name: "700",description: "Piata comunala 700", location: "Strada Paris, numarul 1, camera 2"},
                    {name: "Iosefin",description: "Piata Iosefin", location: "Strada Paris, numarul 2, camera 2"},
                    {name: "Sinaia",description: "Piata Sinaia", location: "Strada Paris, numarul 3, camera 2"},
                    {name: "Badea Cartan",description: "Piata Badea Cartan", location: "Strada Paris, numarul 4, camera 2"}
                ]
            }
        }
    };

    app.directive("sidemenu", sidemenu)
} (angular.module("mymarket")));
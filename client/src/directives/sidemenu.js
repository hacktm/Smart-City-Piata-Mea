'use strict';

(function(app) {

    var sidemenu = function() {
        return {
            restrict:"AE",
            templateUrl: "views/directives/sidemenu.html"
        }
    };

    app.directive("sidemenu", sidemenu)
} (angular.module("mymarket")));
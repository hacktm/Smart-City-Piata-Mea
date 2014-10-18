'use strict';

(function(app) {

    var navbar = function() {
        return {
            restrict: "AE",
            templateUrl: "views/directives/navbar.html"
        }
    };

    app.directive("navbar", navbar)
} (angular.module("mymarket")));
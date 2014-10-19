/**
 * Created by andrei on 19.10.2014.
 */
'use strict';

(function(app) {

    var modals = function() {
        return {
            restrict: "AE",
            templateUrl: "views/directives/modals.html"
        }
    };

    app.directive("modals", modals)
} (angular.module("mymarket")));
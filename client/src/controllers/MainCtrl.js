'use strict';

(function(app) {

    var MainCtrl = function() {
        console.log("Main");
    };

    app.controller("MainCtrl", MainCtrl);
} (angular.module("mymarket")));
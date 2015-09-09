'use strict';

/**
 * @ngdoc overview
 * @name webApp
 * @description
 * # webApp
 *
 * Main module of the application.
 */
var app = angular
  .module('webApp', [
    //'ngAnimate',
    //'ngCookies',
    //'ngResource',
    'ngRoute',
    'routeStyles',
    //'ngSanitize',
    //'ngTouch',
    'blockUI',
    'ui.bootstrap'
  ])
  .config(['$routeProvider', function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/board/main.html',
        controller: 'DashboardCtrl',
        controllerAs: 'Dashboard',
        css: 'styles/blog.css'
      })
      .when('/login', {
        templateUrl: 'views/login/main.html',
        controller: 'LoginCtrl',
        controllerAs: 'Login',
        css: 'styles/login.css'
      })
      .otherwise({
        redirectTo: '/login'
      });
  }]);

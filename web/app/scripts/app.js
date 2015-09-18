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
    'ngRoute',
    'routeStyles',
    'blockUI',
    'ui.bootstrap'
  ])
  .config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/board/main.html',
        controller: 'DashboardCtrl'
      })
      .when('/view/:commentId', {
        templateUrl: 'views/board/detail.html',
        controller: 'CommentDetailCtrl'
      })
      .when('/login', {
        templateUrl: 'views/login/main.html',
        controller: 'LoginCtrl',
        css: 'styles/login.css'
      })
      .otherwise({
        redirectTo: '/404.html'
      });

    $locationProvider.html5Mode(true);
    $locationProvider.hashPrefix('!');
  }]);

'use strict';

/**
 * @ngdoc overview
 * @name activityApp
 * @description
 * # activityApp
 *
 * Main module of the application.
 */
angular
  .module('activityApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap'
  ])
  .constant("baseUrl", "http://localhost:8080/products/")
  .config(function($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/user/records', {
        templateUrl: 'views/activity-records.html',
        controller: 'ActivityRecordsCtrl',
        controllerAs: 'activityRecords'
      })
      .when('/test', {
        templateUrl: 'views/test.html',
        controller: 'TestCtrl',
        controllerAs: 'test'
      })
      .otherwise({
        redirectTo: '/'
      });
  });

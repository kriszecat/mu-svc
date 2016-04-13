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
    'hljs',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'spring-data-rest',
    'ui.bootstrap',
  ])
  .constant("baseUrl", "http://activity-service:8080/")
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
      .when('/employee/records', {
        templateUrl: 'views/activity-records.html',
        controller: 'ActivityRecordsCtrl',
        controllerAs: 'activityRecords'
      })
      .when('/employee/props', {
        templateUrl: 'views/employee.html',
        controller: 'EmployeeCtrl',
        controllerAs: 'employee'
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

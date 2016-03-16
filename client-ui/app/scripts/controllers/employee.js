'use strict';

/**
 * @ngdoc function
 * @name activityApp.controller:EmployeeCtrl
 * @description
 * # EmployeeCtrl
 * Controller of the activityApp
 */
angular.module('activityApp')
  .controller('EmployeeCtrl', function($scope, $http,
    baseUrl, SpringDataRestAdapter) {

    $scope.baseUrl = baseUrl;
    $scope.props = {
      isCollapsed: false
    };


    // employee
    var httpPromise = $http.get(baseUrl + 'employees');
    SpringDataRestAdapter.process(httpPromise)
      .then(function(processedResponse) {
        $scope.processedResponse = angular.toJson(processedResponse, true);
        $scope.employees = processedResponse._embeddedItems;
      });

    $scope.showEmployee = function() {

    };
    // end-employee

  });

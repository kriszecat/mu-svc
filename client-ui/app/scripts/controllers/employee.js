'use strict';

/**
 * @ngdoc function
 * @name activityApp.controller:EmployeeCtrl
 * @description
 * # EmployeeCtrl
 * Controller of the activityApp
 */
angular.module('activityApp')
  .controller('EmployeeCtrl', function(
    $scope, $rootScope, $http, baseUrl, SpringDataRestAdapter) {

    $scope.apiUrl = baseUrl + 'hr/user/employee/';
    $scope.employee = {
      uri: '',
      selected: {}
    };
    $scope.props = {
      isCollapsed: false
    };

    $scope.selectEmployee = function(nomPre) {
      $scope.employee.uri = $scope.apiUrl + 'alias/000002';
      $scope.showEmployee();
    }

    // Get employee details from its alias
    $scope.showEmployee = function() {
      SpringDataRestAdapter.process($http.get($scope.employee.uri))
      .then(function(response) {
        $scope.employee.selected = response.content;
        $rootScope.employeeAlias = $scope.employee.selected.alias;
      });
    }

    // Get all employees
    SpringDataRestAdapter.process($http.get($scope.apiUrl))
      .then(function(response) {
        $scope.employees = response.content;
      });

  });

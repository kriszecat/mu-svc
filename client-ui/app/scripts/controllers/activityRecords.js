'use strict';

/**
 * @ngdoc function
 * @name activityApp.controller:ActivityrecordsCtrl
 * @description
 * # ActivityrecordsCtrl
 * Controller of the activityApp
 */
angular.module('activityApp')
  .controller('ActivityRecordsCtrl', function(
    $scope, $rootScope, $http, SpringDataRestAdapter) {

    $scope.displayMode = "view";

    // calendar
    $scope.calendar = {
      date: null,
      opened: false
    };

    // Disable weekend selection
    function disabled(data) {
      var date = data.date,
        mode = data.mode;
      return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }

    $scope.open = function() {
      $scope.calendar.opened = true;
    };
    // end-calendar

    // activityRecords
    //http: //localhost:8080/employees/:id/activityRecords?projection=inlineActivity
    $scope.listActivityRecords = function() {
      var httpPromise = $http.get($rootScope.employeeUri);
      SpringDataRestAdapter.process(httpPromise)
        .then(function(processedResponse) {
          // employee
          $scope.employee = processedResponse;
          $scope.ejson = angular.toJson($scope.employee, true);
          // end-employee

          var activityRecordsResource = {
            "name": "activityRecords",
            "parameters": {
              "projection": "inlineActivity"
            }
          };
          processedResponse._resources(activityRecordsResource).get(
            function(response) {
              SpringDataRestAdapter.process(response)
                .then(function(processedResponse) {
                  $scope.activityRecords = processedResponse._embeddedItems;
                })
            });
        });
    }

    $scope.deleteActivityRecord = function(product) {}

    $scope.editOrCreateActivityRecord = function(product) {}
      // end-activityRecords

    $scope.listActivityRecords();
  });

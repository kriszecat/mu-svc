'use strict';

/**
 * @ngdoc function
 * @name activityApp.controller:ActivityrecordsCtrl
 * @description
 * # ActivityrecordsCtrl
 * Controller of the activityApp
 */
angular.module('activityApp')
  .controller('ActivityRecordsCtrl', function($scope) {

    $scope.status = {
      isOpen: false
    };

    $scope.user = {
      current: null,
      list: [{
        'id': 1,
        'eid': '130001'
      }, {
        'id': 2,
        'eid': '130002'
      }, {
        'id': 3,
        'eid': '130003'
      }]
    };

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

  });

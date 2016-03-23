'use strict';

describe('Controller: ActivityrecordsCtrl', function () {

  // load the controller's module
  beforeEach(module('activityApp'));

  var ActivityrecordsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ActivityrecordsCtrl = $controller('ActivityrecordsCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ActivityrecordsCtrl.awesomeThings.length).toBe(3);
  });
});

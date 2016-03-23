'use strict';

describe('Controller: EmployeeCtrl', function () {

  // load the controller's module
  beforeEach(module('activityApp'));

  var EmployeeCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    EmployeeCtrl = $controller('EmployeeCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(EmployeeCtrl.awesomeThings.length).toBe(3);
  });
});

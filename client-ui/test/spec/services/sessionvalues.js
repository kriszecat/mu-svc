'use strict';

describe('Service: sessionValues', function () {

  // load the service's module
  beforeEach(module('activityApp'));

  // instantiate service
  var sessionValues;
  beforeEach(inject(function (_sessionValues_) {
    sessionValues = _sessionValues_;
  }));

  it('should do something', function () {
    expect(!!sessionValues).toBe(true);
  });

});

'use strict';

/**
 * @ngdoc function
 * @name activityApp.controller:TestCtrl
 * @description
 * # TestCtrl
 * Controller of the activityApp
 */
angular.module('activityApp')
  .controller('TestCtrl', function($scope, $resource, baseUrl) {

    $scope.displayMode = "list";
    $scope.currentProduct = null;
    $scope.productsResource = $resource(baseUrl + "products/:id", {
      id: "@id"
    });

    $scope.listProducts = function() {
      /*$scope.products = [{
        id: 0,
        name: "Dummy1",
        category: "Test",
        price: 1.25
      }, {
        id: 1,
        name: "Dummy2",
        category: "Test",
        price: 2.45
      }, {
        id: 2,
        name: "Dummy3",
        category: "Test",
        price: 4.25
      }];*/
      $scope.products = $scope.productsResource.query();
    }

    $scope.deleteProduct = function(product) {
      product.$delete().then(function() {
        $scope.products.splice($scope.products.indexOf(product), 1);
      });
      $scope.displayMode = "list";
    }

    $scope.createProduct = function(product) {
      new $scope.productsResource(product).$save().then(function(newProduct) {
        $scope.products.push(newProduct);
        $scope.displayMode = "list";
      });
    }

    $scope.updateProduct = function(product) {
      product.$save();
      $scope.displayMode = "list";
    }

    $scope.editOrCreateProduct = function(product) {
      $scope.currentProduct = product ? product : {};
      $scope.displayMode = "edit";
    }

    $scope.saveEdit = function(product) {
      if (angular.isDefined(product.id)) {
        $scope.updateProduct(product);
      } else {
        $scope.createProduct(product);
      }
    }

    $scope.cancelEdit = function() {
      if ($scope.currentProduct && $scope.currentProduct.$get) {
        $scope.currentProduct.$get();
      }
      $scope.currentProduct = {};
      $scope.displayMode = "list";
    }

    $scope.listProducts();

  });

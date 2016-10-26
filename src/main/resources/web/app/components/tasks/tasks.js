'use strict';

angular.module('app.tasks', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/errors', {
    templateUrl: 'components/tasks/tasks.html',
    controller: 'ErrorListCtrl'
  });
}])

.controller('ErrorListCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {

  $scope.setTitleAndUrl('Список задач', '#/errors');

  $scope.loadErrors = function() {
    $http.get('api/task/all').
      success(function(data) {
        $scope.tasks = data;
      }).
      error(function(error) {
        console.log(error);
      });
  }

  $scope.canRun = function(status) {
    if (status == "NEW") {
      return true;
    } else {
      return false;
    }
  }

  $scope.canCancel = function(status) {
    if (status == "WAITING" || status == "PROCESSING") {
      return true;
    } else {
      return false;
    }
  }

  $scope.canDelete = function(status) {
    if (status == "SUCCESS" || status == "FAILURE") {
      return true;
    } else {
      return false;
    }
  }

  $scope.run = function(id) {
    console.log("run task " + id);
  }

  $scope.cancel = function(id) {
    console.log("cancel task " + id);
  }

  $scope.delete = function(id) {
    console.log("delete task " + id);
  }

  $scope.loadErrors();

}]);

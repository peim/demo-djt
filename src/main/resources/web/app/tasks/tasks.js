'use strict';

angular.module('app.tasks', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/tasks', {
    templateUrl: 'tasks/tasks.html',
    controller: 'TasksCtrl'
  });
}])

.controller('TasksCtrl', ['$scope', '$http', '$location', function($scope, $http, $location) {

  $scope.setTitleAndUrl('Список задач', '#/tasks');

  $scope.loadErrors = function() {
    $http.get('api/task/all')
      .success(function(tasks) {
        $scope.tasks = tasks;
      })
      .error(function(error) {
        console.log(error);
      });
  }

  $scope.run = function(id) {
    $http.get('api/task/execute/' + id)
      .success(function(task) {
        $scope.updateTasks(task);
      })
      .error(function(error) {
        console.log(error);
      });
  }

  $scope.cancel = function(id) {
    console.log("cancel task " + id);

    $http.put('api/task/cancel/' + id)
      .success(function(task) {
        $scope.updateTasks(task);
      })
      .error(function(error) {
        console.log(error);
      });
  }

  $scope.delete = function(id) {
    $http.delete('api/task/delete/' + id)
      .success(function() {
        $scope.removeTask(id);
      })
      .error(function(error) {
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

  $scope.updateTasks = function(task) {
    for (var i in $scope.tasks) {
      if ($scope.tasks[i].id == task.id) {
        $scope.tasks[i].status = task.status;
        $scope.tasks[i].description = task.description;
        break;
      }
    }
  }

  $scope.removeTask = function(id) {
    for (var i in $scope.tasks) {
      if ($scope.tasks[i]['id'] == id) {
        $scope.tasks.splice(i, 1);
      }
    }
  }

  $scope.loadErrors();

}]);

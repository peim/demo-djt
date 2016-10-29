'use strict';

angular.module('app.tasks', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/tasks', {
    templateUrl: 'tasks/tasks.html',
    controller: 'TasksCtrl'
  });
}])

.controller('TasksCtrl', ['$scope', '$http', function($scope, $http) {

  $scope.setTitleAndUrl('Список задач', '#/tasks');

  $scope.run = function(task) {
    $http.put('api/task/execute', task)
      .success(function(data) {
        updateTasks(data);
      })
      .error(function(error) {
        console.log(error);
      });
  }

  $scope.cancel = function(task) {
    $http.put('api/task/cancel', task)
      .success(function(data) {
        updateTasks(data);
      })
      .error(function(error) {
        console.log(error);
      });
  }

  $scope.delete = function(id) {
    $http.delete('api/task/delete/' + id)
      .success(function() {
        removeTask(id);
      })
      .error(function(error) {
        console.log(error);
      });
  }

  $scope.canRun = function(status) {
    if (status == "NEW" || status == "CANCELED") {
      return true;
    } else {
      return false;
    }
  }

  $scope.canCancel = function(status) {
    if (status == "PROCESSING") {
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

  var loadErrors = function() {
    $http.get('api/task/all')
      .success(function(tasks) {
        $scope.tasks = tasks;
      })
      .error(function(error) {
        console.log(error);
      });
    }

  var updateTasks = function(task) {
    for (var i in $scope.tasks) {
      if ($scope.tasks[i].id == task.id) {
        $scope.tasks[i].status = task.status;
        $scope.tasks[i].description = task.description;
        break;
      }
    }
  }

  var removeTask = function(id) {
    for (var i in $scope.tasks) {
      if ($scope.tasks[i]['id'] == id) {
        $scope.tasks.splice(i, 1);
      }
    }
  }

  var initSockets = function() {
    var endpoint = new SockJS('/socket');
    $scope.socket = Stomp.over(endpoint);
    $scope.socket.connect({}, startListener);
  }

  var startListener = function() {
    $scope.socket.subscribe('/queue/task', function(data) {
      var task = JSON.parse(data.body);
      updateTasks(task);
      $scope.$evalAsync();
    });
  }

  loadErrors();
  initSockets();

}]);

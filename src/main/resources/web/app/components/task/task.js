'use strict';

angular.module('app.task', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/error/:errorId', {
    templateUrl: 'components/task/task.html',
    controller: 'RepErrorCtrl'
  });
}])

.controller('RepErrorCtrl', ['$scope', '$http', '$location', '$routeParams', '$timeout', '$window', function($scope, $http, $location, $routeParams, $timeout, $window) {

  $scope.setTitleAndUrl('Список ошибок', '#/errors');
  $scope.errorId = $routeParams.errorId;

  $scope.$on('errorListUpdated', function(e, data) {
    var id_runner = $scope.repError.id_runner;
    var id_table = $scope.repError.id_table;
    var id_foreign = $scope.repError.id_foreign;

    var parts = data.body.replace(/"/g, '').split(':');
    if (id_runner == parts[1] && id_table == parts[2] && id_foreign == parts[3]) {
      $scope.btnDisabled = true;
      $scope.$apply();
    }
  });

  $scope.loadError = function() {
    $scope.$emit('loading');

    $http.get('api/task/id/' + $scope.errorId).
      success(function(data) {
        console.log(data);
        $scope.task = data;
        $scope.repErrorType = data.errorType;
        $scope.repError = data.errorLog;

        if ($scope.repErrorType == 'DELEDIT' || $scope.repErrorType == 'DELETE') {
          $scope.confirmMessage = 'Вы действительно хотите удалить запись?';
        } else if ($scope.repErrorType == 'RECOVER') {
          $scope.confirmMessage = 'Вы действительно хотите восстановить запись?';
        }
        $scope.$emit('notLoading');
      }).
      error(function(data, status) {
        $scope.isServerError = true;
        $scope.status = 'ERROR ' + status;
        $scope.detail = data;
        $('.modal-md-result').modal('show');
        $scope.$emit('notLoading');
      });
  }

  $scope.edit = function() {
    $location.path('error/edit/' + $scope.errorId);
  }

  $scope.recover = function() {
    $scope.$emit('loading');
    $scope.btnDisabled = true;

    $http.get('api/error/recover/' + $scope.errorId).
      success(function(data) {
        $scope.status = data.status;
        $scope.detail = data.detail;
        $('.modal-md-result').modal('show');
        $scope.$emit('notLoading');
      }).
      error(function(data, status) {
        $scope.isServerError = true;
        $scope.status = 'ERROR ' + status;
        $scope.detail = data;
        $('.modal-md-result').modal('show');
        $scope.$emit('notLoading');
      });
  }

  $scope.delete = function() {
    $scope.$emit('loading');
    $scope.btnDisabled = true;

    $http.get('api/error/delete/' + $scope.errorId).
      success(function(data) {
        $scope.status = data.status;
        $scope.detail = data.detail;
        $('.modal-md-result').modal('show');
        $scope.$emit('notLoading');
      }).
      error(function(data, status) {
        $scope.isServerError = true;
        $scope.status = 'ERROR ' + status;
        $scope.detail = data;
        $('.modal-md-result').modal('show');
        $scope.$emit('notLoading');
      });
  }

  $scope.performAction = function() {
    if ($scope.repErrorType == 'DELEDIT' || $scope.repErrorType == 'DELETE') {
      $scope.delete();
    } else if ($scope.repErrorType == 'RECOVER') {
      $scope.recover();
    }
  }

  $scope.goToErrorList = function() {
    $location.path('/errors');
  }

  $scope.goToErrorListWithDelay = function() {
    $timeout(function() {
      if ($scope.isServerError) {
        $window.location.href = '#/errors';
        $window.location.reload();
      } else {
        $location.path('/errors');
      }
    }, 200);
  }

  $scope.goToMainPageWithDelay = function() {
    $timeout(function() {
      $window.open('//dbrep-tools.chelny.techn.ru', '_self');
    }, 200);
  }

  $scope.loadError();

  /* Draggable modals */
  $('.modal').draggable({
    handle: '.modal-header',
    revert: true
  });
  /* */

}]);

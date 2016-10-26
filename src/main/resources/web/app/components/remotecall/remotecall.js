'use strict';

angular.module('dbrepApp.remotecall', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/remotecall/:errorId', {
    templateUrl: 'components/remotecall/remotecall.html',
    controller: 'RemotecallCtrl'
  });
}])

.controller('RemotecallCtrl', ['$scope', '$http', '$location', '$routeParams', '$timeout', '$window', function($scope, $http, $location, $routeParams, $timeout, $window) {

  $scope.setTitleAndUrl('Список ошибок', '#/errors');
  $scope.errorId = $routeParams.errorId;

  $scope.$on('errorListUpdated', function(e, data) {
    var id_runner = $scope.remotecall.id_runner;
    var id_table = $scope.remotecall.id_table;
    var id_foreign = $scope.remotecall.id_foreign;

    var parts = data.body.replace(/"/g, '').split(':');
    if (id_runner == parts[1] && id_table == parts[2] && id_foreign == parts[3]) {
      $scope.btnDisabled = true;
      $scope.$apply();
    }
  });

  $scope.loadRemotecall = function() {
    $scope.$emit('loading');

    $http.get('api/remotecall/' + $scope.errorId).
      success(function(data) {
        $scope.remotecall = data;
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
    $location.path('remotecall/edit/' + $scope.errorId);
  }

  $scope.restart = function() {
    $scope.$emit('loading');
    $scope.btnDisabled = true;

  	$http.get('api/remotecall/restart/' + $scope.errorId).
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

  $scope.ignore = function() {
    $scope.$emit('loading');
    $scope.btnDisabled = true;

  	$http.get('api/remotecall/ignore/' + $scope.errorId).
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

  $scope.callModal = function(actionType) {
    if (actionType == 'restart') {
      $scope.isIgnore = false;
      $scope.isRestart = true;
      $scope.confirmMessage = 'Вы действительно хотите перезапустить remotecall?';
    } else if (actionType == 'ignore') {
      $scope.isRestart = false;
      $scope.isIgnore = true;
      $scope.confirmMessage = 'Вы действительно хотите проигнорировать remotecall?';
    }
  }

  $scope.performAction = function() {
    if ($scope.isRestart) {
      $scope.restart();
    } else if ($scope.isIgnore) {
      $scope.ignore();
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

  $scope.loadRemotecall();

  /* Draggable modals */
  $('.modal').draggable({
    handle: '.modal-header',
    revert: true
  });
  /* */

}]);

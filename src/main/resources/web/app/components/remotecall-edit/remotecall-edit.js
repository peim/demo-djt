'use strict';

angular.module('dbrepApp.remotecallEdit', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/remotecall/edit/:errorId', {
    templateUrl: 'components/remotecall-edit/remotecall-edit.html',
    controller: 'RemoteCallEditCtrl'
  });
}])

.controller('RemoteCallEditCtrl', ['$scope', '$http', '$location', '$routeParams', '$timeout', '$window', function($scope, $http, $location, $routeParams, $timeout, $window) {

  $scope.setTitleAndUrl('Список ошибок', '#/errors');
  $scope.errorId = $routeParams.errorId;

  $scope.$on('errorListUpdated', function(e, data) {
    var id_runner = $scope.remotecall.id_runner;
    var id_table = $scope.remotecall.id_table;
    var id_foreign = $scope.remotecall.id_foreign;

    var parts = data.body.replace(/"/g, '').split(':');
    if (id_runner == parts[1] && id_table == parts[2] && id_foreign == parts[3]) {
      $scope.btnDisabled = true;
      $scope.inputDisabled = true;
      $scope.$apply();
    }
  });

  $scope.loadRemotecallDetail = function() {
    $scope.$emit('loading');

    $http.get('api/remotecall/get/' + $scope.errorId).
      success(function(data) {
        $scope.remotecall = data;
        $scope.confirmMessage = 'Вы действительно хотите отредактировать запись?';
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
    $scope.$emit('loading');
    $scope.btnDisabled = true;
    $scope.inputDisabled = true;

    $http.put('api/remotecall/edit/' + $scope.errorId, $scope.remotecall).
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

  $scope.goToRemotecall = function() {
    $location.path('remotecall/' + $scope.errorId);
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

  $scope.loadRemotecallDetail();

  /* Draggable modals */
  $('.modal').draggable({
    handle: '.modal-header',
    revert: true
  });
  /* */

}]);

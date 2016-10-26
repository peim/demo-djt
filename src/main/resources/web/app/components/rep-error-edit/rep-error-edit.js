'use strict';

angular.module('dbrepApp.repErrorEdit', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/error/edit/:errorId', {
    templateUrl: 'components/rep-error-edit/rep-error-edit.html',
    controller: 'RepErrorEditCtrl'
  });
}])

.controller('RepErrorEditCtrl', ['$scope', '$http', '$location', '$routeParams', '$timeout', '$window', function($scope, $http, $location, $routeParams, $timeout, $window) {

  $scope.setTitleAndUrl('Список ошибок', '#/errors');
  $scope.errorId = $routeParams.errorId;

  $scope.$on('errorListUpdated', function(e, data) {
    var id_runner = $scope.tableRecord.id_runner;
    var id_table = $scope.tableRecord.id_table;
    var id_foreign = $scope.tableRecord.id_foreign;

    var parts = data.body.replace(/"/g, '').split(':');
    if (id_runner == parts[1] && id_table == parts[2] && id_foreign == parts[3]) {
      $scope.btnDisabled = true;
      $scope.inputDisabled = true;
      $scope.$apply();
    }
  });

  $scope.loadErrorDetail = function() {
    $scope.$emit('loading');

    $http.get('api/error/get/' + $scope.errorId).
      success(function(data) {
        $scope.tableRecord = data;
        $scope.columns = data.columns;
        $scope.isSourceEmpty = data.sourceEmpty;
        $scope.isDestEmpty = data.destEmpty;
        $scope.confirmMessage = 'Вы действительно хотите отредактировать запись?';
        $scope.$emit('notLoading');
        $scope.enableTextareaAutosize();
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

    $http.put('api/error/edit/' + $scope.errorId, $scope.tableRecord).
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

  $scope.goToError = function() {
    $location.path('error/' + $scope.errorId);
  }

  $scope.isNullValue = function(value) {
    return value === null;
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

  $scope.loadErrorDetail();

  $scope.enableTextareaAutosize = function() {
    $timeout(function() {
      autosize($('textarea'));
    }, 400);
  }

  $scope.textareaAutosizeUpdate = function() {
    $timeout(function() {
      autosize.update($('textarea'));
    }, 400);
  }

  /* Draggable modals */
  $('.modal').draggable({
    handle: '.modal-header',
    revert: true
  });
  /* */

}]);

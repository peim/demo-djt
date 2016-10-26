'use strict';

angular.module('dbrepApp.errorList', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/errors', {
    templateUrl: 'components/error-list/error-list.html',
    controller: 'ErrorListCtrl'
  });
}])

.controller('ErrorListCtrl', ['$scope', '$http', '$location', '$timeout', '$window', 'actualPagesStorage', function($scope, $http, $location, $timeout, $window, actualPagesStorage) {

  $scope.setTitleAndUrl('Список задач', '#/errors');

//  $scope.$on('errorListUpdated', function(e, data) {
//    $scope.loadActualErrors();
//  });

//  $scope.delays = [
//    {name: '0 second', value: 1},
//    {name: '5 second', value: 5},
//    {name: '1 minute', value: 60},
//    {name: '5 minute', value: 300}
//  ];

//  $scope.updateErrorList = function() {
//    $scope.loadActualErrors();
//  }

//  $scope.errorDelay = {name: '0 second', value: 0};

  $scope.loadErrors = function() {
    $scope.$emit('loading');
    
    $http.get('api/task/all').
      success(function(data) {
        console.log(data);
        $scope.tasks = data;
//        $scope.errorList = data.error_logs;
//        $scope.errorCount = data.error_count;
//        $scope.errorDelay = $scope.delays[data.delay.id];
//        $scope.totalItems = $scope.errorCount;
//        $scope.$emit('notLoading');
      }).
      error(function(data, status) {
        $scope.status = 'ERROR ' + status;
        $scope.detail = data;
        $('.modal-md-result').modal('show');
        $scope.$emit('notLoading');
      });
  }

  $scope.currentTask = function(id) {
    console.log(id);
    $location.path('error/' + id);
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

//  $scope.goToError = function(error) {
//    actualPagesStorage.setErrorsPage($scope.currentPage);
//    if (error.is_remote_call) {
//      $location.path('remotecall/' + error.id_error);
//    } else {
//      $location.path('error/' + error.id_error);
//    }
//  }

//  $scope.loadActualErrors = function() {
//    $scope.loadErrors($scope.errorsLimit, ($scope.currentPage - 1) * $scope.errorsLimit);
//    actualPagesStorage.setErrorsPage(1);
//  }

//  $scope.reloadPage = function() {
//    $window.location.reload();
//  }
//
//  $scope.goToMainPageWithDelay = function() {
//    $timeout(function() {
//      $window.open('//dbrep-tools.chelny.techn.ru', '_self');
//    }, 200);
//  }

//  $scope.paginationMaxSize = 2;
//  $scope.errorsLimit = 10;
//  $scope.currentPage = actualPagesStorage.getErrorsPage();
//
//  $scope.loadActualErrors();

  $scope.loadErrors();

  /* Draggable modals */
//  $('.modal').draggable({
//    handle: '.modal-header',
//    revert: true
//  });
  /* */

}]);

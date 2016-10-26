'use strict';

angular.module('dbrepApp.errorLogs', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/logs', {
    templateUrl: 'components/error-logs/error-logs.html',
    controller: 'ErrorLogsCtrl'
  });
}])

.controller('ErrorLogsCtrl', ['$scope', '$http', '$location', '$timeout', '$window', 'actualPagesStorage', function($scope, $http, $location, $timeout, $window, actualPagesStorage) {    

  $scope.setTitleAndUrl('Журнал', '#/logs');

  $scope.loadErrorLogs = function(logCount, offset) {
    $scope.$emit('loading');

    $http.get('api/log?count=' + logCount + '&offset=' + offset).
      success(function(data) {
        $scope.errorLogs = data.logRecords;
        $scope.logCount = data.logCount;
        $scope.totalItems = $scope.logCount;
        $scope.$emit('notLoading');
      }).
      error(function(data, status) {
        $scope.status = 'ERROR ' + status;
        $scope.detail = data;
        $('.modal-md-result').modal('show');
        $scope.$emit('notLoading');
      });
  }

  $scope.goToLog = function(log) {
    actualPagesStorage.setLogsPage($scope.currentPage);
    $location.path('log/' + log.id);
  }

  $scope.loadActualLogs = function() {
    $scope.loadErrorLogs($scope.logsLimit, ($scope.currentPage - 1) * $scope.logsLimit);
    actualPagesStorage.setLogsPage(1);
  }

  $scope.reloadPage = function() {
    $window.location.reload();
  }

  $scope.goToMainPageWithDelay = function() {
    $timeout(function() {
      $window.open('//dbrep-tools.chelny.techn.ru', '_self');
    }, 200);
  }

  $scope.paginationMaxSize = 2;
  $scope.logsLimit = 10;
  $scope.currentPage = actualPagesStorage.getLogsPage();

  $scope.loadActualLogs();
  
  /* Draggable modals */
  $('.modal').draggable({
    handle: '.modal-header',
    revert: true
  });
  /* */

}]);

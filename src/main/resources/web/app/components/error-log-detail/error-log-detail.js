'use strict';

angular.module('dbrepApp.errorLogDetail', ['ngRoute', 'ui.bootstrap'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/log/:logId', {
    templateUrl: 'components/error-log-detail/error-log-detail.html',
    controller: 'ErrorLogDetailCtrl'
  });
}])

.controller('ErrorLogDetailCtrl', ['$scope', '$http', '$location', '$routeParams', '$timeout', '$window', function($scope, $http, $location, $routeParams, $timeout, $window) {    

  $scope.setTitleAndUrl('Журнал', '#/logs');
  $scope.logId = $routeParams.logId;

  $scope.loadLog = function() {
    $scope.$emit('loading');

    $http.get('api/log/' + $scope.logId).
      success(function(data) {
        $scope.log = data;
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

  $scope.goToErrorLogs = function() {
    $location.path('/logs');
  }

  $scope.goToErrorLogsWithDelay = function() {
    $timeout(function() {
      if ($scope.isServerError) {
        $window.location.href = '#/logs';
        $window.location.reload();
      } else {
        $location.path('/logs');
      }
    }, 200);
  }

  $scope.goToMainPageWithDelay = function() {
    $timeout(function() {
      $window.open('//dbrep-tools.chelny.techn.ru', '_self');
    }, 200);
  }

  $scope.loadLog();

  /* Draggable modals */
  $('.modal').draggable({
    handle: '.modal-header',
    revert: true
  });
  /* */

}]);

'use strict';

// Declare app level module which depends on views, and components
angular.module('dbrepApp', [
  'ngRoute',
  'ui.bootstrap',
  'dbrepApp.services',
  'dbrepApp.errorList',
  'dbrepApp.repError',
  'dbrepApp.remotecall',
  'dbrepApp.repErrorEdit',
  'dbrepApp.remotecallEdit',
  'dbrepApp.errorLogs',
  'dbrepApp.errorLogDetail'
])

.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
  $routeProvider.otherwise({redirectTo: '/errors'});
  $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
  $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
}])

.controller('MainCtrl', ['$scope', '$http', '$location', '$timeout', function($scope, $http, $location, $timeout) {
  $scope.setTitleAndUrl = function(title, url) {
    $scope.title = title;
    $scope.url = url;
  }

  /* Loading events */
//  $scope.$on('loading', function() {
//    $scope.isLoading = true;
//  });

//  $scope.$on('notLoading', function() {
//    $scope.isLoading = false;
//  });
  /* */

  /* Websockets */
//  var startListener = function() {
//    $scope.socket.stomp.subscribe('/topic/errors', function(data) {
//      $scope.$broadcast('errorListUpdated', data);
//      $scope.showNotification();
//    });
//  }

//  $scope.initSockets = function() {
//    $http.get('api/config').success(function(data) {
//      $scope.serverUrl = data.serverUrl;
//      $scope.serverType = data.serverType;
//      $scope.serverTypeTitle = $scope.serverType.toUpperCase() + ': ';
//    });
//
//    $timeout(function() {
//      $scope.socket = new SockJS($scope.serverUrl + 'stomp');
//      $scope.socket.stomp = Stomp.over($scope.socket);
//      $scope.socket.stomp.connect({}, startListener);
//      $scope.socket.stomp.onclose = $scope.reconnect;
//    }, 1000);
//  }

//  $scope.reconnect = function() {
//    $timeout($scope.initSockets, 10000);
//  }

//  $scope.showNotification = function() {
//    if ($('.notifyjs-hidable')[0]) {
//      $scope.modifiedErrorCount++;
//      $('.notifyjs-bootstrap-base > span').text('Список ошибок обновлен: ' + $scope.modifiedErrorCount);
//    } else {
//      $scope.modifiedErrorCount = 1;
//      $.notify('Список ошибок обновлен: ' + $scope.modifiedErrorCount, 'success');
//    }
//  }

//  $scope.initSockets();
  /* */
}]);

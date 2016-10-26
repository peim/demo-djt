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

}]);

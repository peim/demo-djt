'use strict';

angular.module('app', [
  'ngRoute',
  'app.tasks',
  'app.task'
])

.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
  $routeProvider.otherwise({redirectTo: '/errors'});
  $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
  $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
}])

.controller('MainCtrl', ['$scope', function($scope) {
  $scope.setTitleAndUrl = function(title, url) {
    $scope.title = title;
    $scope.url = url;
  }

}]);

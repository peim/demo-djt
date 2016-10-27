'use strict';

angular.module('app', [
  'ngRoute',
  'app.tasks',
  'app.websocket'
])

.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
  $routeProvider.otherwise({redirectTo: '/tasks'});
}])

.controller('MainCtrl', ['$scope', function($scope) {
  $scope.setTitleAndUrl = function(title, url) {
    $scope.title = title;
    $scope.url = url;
  }

}]);

'use strict';

angular.module('app.websocket', [])

  .factory('websocket', ['$q', '$timeout', function($q, $timeout) {

    function WebSocket() {
      var socket;
      var reconnectTimeout = 30000;
      var deferred = $q.defer();

      this.get = function() {
        return socket;
      };

      this.init = function() {
        socket = Stomp.over(new SockJS('/socket'));
        socket.connect({}, startListener, reconnect);
        return deferred.promise;
      };

      var startListener = function() {
        deferred.resolve(JSON.parse(true));
      };

      var reconnect = function() {
        $timeout(function() {
          console.log('Websocket connection reconnect!');
          init();
        }, reconnectTimeout);
      };
    }

    return {
      getInstance: function() {
        return new WebSocket();
      }
    };

  }]);
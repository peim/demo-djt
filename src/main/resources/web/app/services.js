'use strict';

/* Services */

angular.module('dbrepApp.services', ['ngRoute'])

.service('actualPagesStorage', function() {
  var _errorsPage = 1;
  var _logsPage = 1;
  
  return {
    getErrorsPage: function() {
      return _errorsPage;
    },
    getLogsPage: function() {
      return _logsPage;
    },
    setErrorsPage: function(page) {
      _errorsPage = page;
    },
    setLogsPage: function(page) {
      _logsPage = page;
    }
  }
});
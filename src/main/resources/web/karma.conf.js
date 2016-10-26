module.exports = function(config) {
  config.set({

    basePath: './',

    files: [
      'app/bower_components/angular/angular.js',
      'app/bower_components/angular-route/angular-route.js',
      'app/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
      'app/bower_components/angular-mocks/angular-mocks.js',
      'app/bower_components/sockjs/sockjs.min.js',
      'app/bower_components/stomp-websocket/lib/stomp.min.js',
      'https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js',
      'app/bower_components/jquery-ui/jquery-ui.min.js',
      'app/components/**/*.js',
      'app/services.js'
    ],

    autoWatch: true,

    frameworks: ['jasmine'],

    browsers: ['PhantomJS'],

    plugins: [
      'karma-phantomjs-launcher',
      'karma-jasmine',
      'karma-junit-reporter'
    ],

    junitReporter: {
      outputDFir: '',
      outputFile: undefined,
      suite: '',
      useBrowserName: true
    }

  });
};

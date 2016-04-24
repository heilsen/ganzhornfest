// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('ngGanzhornfest', ['ionic', 'ngGanzhornfest.controllers', 'ngGanzhornfest.services', 'leaflet-directive', 'ngTouch', 'ngCordova'])

.value('Application', {
  version: "0.1.4",
  name: "Ganzhornfest",
})

.run(function($ionicPlatform, Application, $templateCache, $http, $cordovaSQLite) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.getAppVersion.getVersionNumber(function(version) {
        Application.version = version;
      });

			var pois = [];
			document.addEventListener("deviceready", function () {
 				var db = window.sqlitePlugin.openDatabase( {name: 'db.backup', location: 'default', createFromLocation: 1} );
        var query = "SELECT * from poi";
				$cordovaSQLite.execute(db, query, []).
					then(
						function(res) {
							for(var i = 0; i < res.rows.length; i++) {
								pois.push(res.rows.item(i));
							}
							console.log("pois: " + JSON.stringify(pois));
						}, function(err) {
							console.error(JSON.stringify(err)); 
						}
					);
				}, false);
    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleLightContent();
    }

    document.addEventListener("deviceready", function () {
      var db = window.sqlitePlugin.openDatabase( {name: 'db.backup', location: 'default', createFromLocation: 1} );
      var query = "SELECT * from poi";
      $cordovaSQLite.execute(db, query, []).
        then(
          function(res) {
            var pois = [];
            for(var i = 0; i < res.rows.length; i++) {
              pois.push(res.rows.item(i));
            }
            console.log("pois: " + JSON.stringify(pois));
          }, function(err) {
            console.error(JSON.stringify(err)); 
          }
        );
      }, false);

  });

  $http.get("templates/tabs.html", { cache: $templateCache });
  $http.get("templates/tab-list.html", { cache: $templateCache });
  $http.get("templates/tab-detail.html", { cache: $templateCache });
  $http.get("templates/tab-detail-stand.html", { cache: $templateCache });
  $http.get("templates/tab-detail-poi.html", { cache: $templateCache });
  $http.get("templates/tab-program.html", { cache: $templateCache });
  $http.get("templates/tab-bus.html", { cache: $templateCache });
  $http.get("templates/tab-info.html", { cache: $templateCache });
  $http.get("templates/tab-map.html", { cache: $templateCache });
})

.config(function($stateProvider, $urlRouterProvider, $ionicConfigProvider) {

  // Place tabs on top for every OS (otherwise Android defaults to top)
  // see: http://forum.ionicframework.com/t/ion-tabs-on-top-of-the-screen-in-android/15732/8
  // and: http://ionicframework.com/docs/api/provider/%24ionicConfigProvider/
  $ionicConfigProvider.tabs.position('top'); 

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

  // setup an abstract state for the tabs directive
  .state('tab', {
    url: "/tab",
    abstract: true,
    templateUrl: "templates/tabs.html"
  })

  // Each tab has its own nav history stack:
  .state('tab.list', {
    url: '/list',
    views: {
      'tab-list': {
        templateUrl: 'templates/tab-list.html',
        controller: 'ListCtrl'
      }
    }
  })

  .state('tab.detail', {
    url: '/detail?id?type',
    views: {
      'tab-list': {
        templateUrl: 'templates/tab-detail.html',
        controller: 'DetailCtrl'
      }
    }
  })

  .state('tab.stand', {
    url: '/stand?id',
    views: {
      'tab-list': {
        templateUrl: 'templates/tab-detail-stand.html',
        controller: 'DetailStandCtrl'
      }
    }
  })

  .state('tab.program', {
    url: '/program?day?stage',
    params: {
      day: function() {
        var possibleDays = [6,0,1];
        var date = new Date();
        var day = date.getDay();
        if ( possibleDays.indexOf(day) !== -1 ) {
          return day;
        } else {
          return 6;
        }
      },
      stage: 59,
    },
    views: {
      'tab-program': {
        templateUrl: 'templates/tab-program.html',
        controller: 'ProgramCtrl'
      }
    }
  })

  .state('tab.stage', {
    url: '/stage?id',
    views: {
      'tab-program': {
        templateUrl: 'templates/tab-detail-poi.html',
        controller: 'DetailStageCtrl'
      }
    }
  })


  .state('tab.bus', {
    url: '/bus?day',
    params: {
      day: function() {
        var possibleDays = [6,0,1];
        var date = new Date();
        var day = date.getDay();
        if ( possibleDays.indexOf(day) !== -1 ) {
          return day;
        } else {
          return 6;
        }
      },
    },
    views: {
      'tab-bus': {
        templateUrl: 'templates/tab-bus.html',
        controller: 'BusCtrl'
      }
    }
  })

  .state('tab.busstop', {
    url: '/busstop',
    views: {
      'tab-bus': {
        templateUrl: 'templates/tab-detail-poi.html',
        controller: 'DetailBusstopCtrl'
      }
    }
  })

  .state('tab.map', {
    url: '/map',
    views: {
      'tab-map': {
        templateUrl: 'templates/tab-map.html',
        controller: 'MapCtrl'
      }
    }
  })

  .state('tab.info', {
    url: '/info',
    views: {
      'tab-info': {
        templateUrl: 'templates/tab-info.html',
        controller: 'InfoCtrl'
      }
    }
  });


  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/tab/info');

});

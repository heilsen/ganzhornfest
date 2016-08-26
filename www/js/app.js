// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('ngGanzhornfest',
    ['ionic', 'ngGanzhornfest.controllers', 'ngGanzhornfest.services', 'leaflet-directive', 'ngTouch', 'ngCordova'])

    .run(function ($ionicPlatform) {
        $ionicPlatform.ready(function () {
            // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
            // for form inputs)
            if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
                cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
            }

            // TODO: find out if StatusBar is really needed in this project
            if (window.StatusBar) {
                // org.apache.cordova.statusbar required
                StatusBar.styleLightContent();
            }
        });
    })

    .config(function ($stateProvider, $urlRouterProvider, $ionicConfigProvider) {

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

            .state('tab.club', {
                url: '/club?id?type',
                params: {
                    type: 'club'
                },
                views: {
                    'tab-list': {
                        templateUrl: 'templates/tab-detail-poi.html',
                        controller: 'PoiDetailController'
                    }
                }
            })

            .state('tab.program', {
                url: '/program?day?stage',
                params: {
                    day: function () {
                        var possibleDays = [6, 0, 1];
                        var date = new Date();
                        var day = date.getDay();
                        if (possibleDays.indexOf(day) !== -1) {
                            return day;
                        } else {
                            return 6;
                        }
                    },
                    stage: 59
                },
                views: {
                    'tab-program': {
                        templateUrl: 'templates/tab-program.html',
                        controller: 'ProgramCtrl'
                    }
                }
            })

            .state('tab.stage', {
                url: '/stage?id?type',
                params: {
                    type: "stage"
                },
                views: {
                    'tab-program': {
                        templateUrl: 'templates/tab-detail-poi.html',
                        controller: 'PoiDetailController'
                    }
                }
            })


            .state('tab.bus', {
                url: '/bus?day',
                params: {
                    day: function () {
                        var possibleDays = [6, 0, 1];
                        var date = new Date();
                        var day = date.getDay();
                        if (possibleDays.indexOf(day) !== -1) {
                            return day;
                        } else {
                            return 6;
                        }
                    }
                },
                views: {
                    'tab-bus': {
                        templateUrl: 'templates/tab-bus.html',
                        controller: 'BusCtrl'
                    }
                }
            })

            .state('tab.busstop', {
                url: '/busstop?id?type',
                params: {
                    id: 64,
                    type: 'busstop'
                },
                views: {
                    'tab-bus': {
                        templateUrl: 'templates/tab-detail-poi.html',
                        controller: 'PoiDetailController'
                    }
                }
            })

            .state('tab.map', {
                url: '/map',
                views: {
                    'tab-map': {
                        templateUrl: 'templates/tab-map.html',
                        controller: 'MapController'
                    }
                }
            })

            .state('tab.detail', {
                url: '/detail?id',
                views: {
                    'tab-list': {
                        templateUrl: 'templates/tab-detail-item.html',
                        controller: 'ItemDetailController'
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
            })

        // if none of the above states are matched, use this as the fallback
        $urlRouterProvider.otherwise('/tab/info');

    });

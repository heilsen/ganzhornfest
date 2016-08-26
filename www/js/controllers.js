angular.module('ngGanzhornfest.controllers', [])
    .controller('PopoverCtrl', ['$scope', '$ionicPopover', '$ionicPopup', '$ionicPlatform', '$cordovaSocialSharing',
        function ($scope, $ionicPopover, $ionicPopup, $ionicPlatform, $cordovaSocialSharing) {
            $ionicPopover.fromTemplateUrl('templates/popover-menu.html', {
                scope: $scope
            }).then(function (popover) {
                $scope.popover = popover;
            });
            $scope.openPopover = function ($event) {
                $scope.popover.show($event);
            };
            //Cleanup the popover when we're done with it!
            $scope.$on('$destroy', function () {
                $scope.popover.remove();
            });

            $scope.shareAnywhere = function () {
                $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", null, null,
                    "https://play.google.com/store/apps/DataServices?id=de.heilsen.ganzhornfest")
                    .then(function (result) {
                    }, function () {
                        $scope.showAlert("Die App konnte mit dem Ziel nicht geteilt werden.");
                    });
            };
            $scope.showAlert = function (message) {
                $ionicPopup.alert({
                    title: "Fehler",
                    template: message
                });
            };
            $scope.showAboutPopup = function () {
                $ionicPopup.alert({
                    scope: $scope,
                    title: "Über die App",
                    cssClass: "about-popup",
                    templateUrl: 'templates/popup-about.html'
                });
            };

            $ionicPlatform.ready(function () {
                cordova.getAppVersion.getVersionNumber().then(function (version) {
                    $scope.version = version;
                });
            });
        }])

    .controller('InfoCtrl', ['$scope', 'Swipe', 'DataService',
        function ($scope, Swipe, DataService) {
            $scope.swipeRight = Swipe.swipeRight;
            $scope.swipeLeft = Swipe.swipeLeft;

            DataService.getClubs.then(function (result) {
                $scope.clubsCounter = result.length;
            });
            DataService.getFoods.then(function (result) {
                $scope.roundedFoodsCounter = Math.floor(result.length / 10) * 10;
            });
            DataService.getDrinks.then(function (result) {
                $scope.roundedDrinksCounter = Math.floor(result.length / 10) * 10;
            });

        }])

    .controller('ListCtrl', ['$scope', 'DataService', 'Swipe', '$ionicScrollDelegate',
        function ($scope, DataService, Swipe, $ionicScrollDelegate) {
            $scope.swipeRight = Swipe.swipeRight;
            $scope.swipeLeft = Swipe.swipeLeft;

            // search form
            $scope.searchTerm = "";
            $scope.clearSearch = function () {
                $scope.searchTerm = "";
            };
            $scope.category = 'club';

            $scope.scrollTop = function() {
                $ionicScrollDelegate.scrollTop();
                $scope.searchTerm = "";
            };

            DataService.getClubs.then(function (result) {
                $scope.clubs = result;
            });
            DataService.getFoods.then(function (result) {
                $scope.foods = result;
            });
            DataService.getDrinks.then(function (result) {
                $scope.drinks = result;
            });
            DataService.getOther.then(function (result) {
                $scope.other = result;
            });
        }])

    .controller('MapController', ['$scope', '$stateParams', 'DataService', 'Swipe',
        function ($scope, $stateParams, DataService, Swipe) {
            $scope.swipeRight = Swipe.swipeRight;
            $scope.swipeLeft = Swipe.swipeLeft;

            // TODO: parseArray and use ids as array
            var ids = parseInt($stateParams.ids);
            var type = $stateParams.type;

            $scope.defaults = DataService.mapDefaults;
            $scope.center = DataService.mapCenter;


            DataService.getAllMarkers().then(function (markers) {
                $scope.markers = markers;
            });

        }])


    .controller('ProgramCtrl', ['$scope', 'DataService', 'Swipe', '$ionicScrollDelegate', '$stateParams', '$filter',
        function ($scope, DataService, Swipe, $ionicScrollDelegate, $stateParams, $filter) {
            $scope.swipeRight = Swipe.swipeRight;
            $scope.swipeLeft = Swipe.swipeLeft;

            DataService.getEvents.then(function (result) {
                result.map(function (item) {
                    item.eventtime = $filter('date')(item.startdate, "HH:mm");
                    //string means data/date, object means no data/date
                    if (typeof item.enddate === "string") {
                        item.eventtime += " - " + $filter('date')(item.enddate, "HH:mm");
                    }
                });
                $scope.events = result;
            });
            DataService.getStages.then(function (result) {
                $scope.stages = result;
                $scope.selectedStage = $stateParams.stage;
            });

            $scope.scrollTop = function() {
                $ionicScrollDelegate.scrollTop();
            };
            $scope.stageFilter = function (poi) {
                return parseInt($scope.selectedStage) === poi.id;
            };
            $scope.eventFilter = function (event) {
                return (new Date(event.startdate)).getDay() === $scope.selectedDay &&  event.poiid === parseInt($scope.selectedStage);
            };
            $scope.days = [
                {id: 6, name: 'Samstag'},
                {id: 0, name: 'Sonntag'},
                {id: 1, name: 'Montag'}
            ];
            switch ((new Date()).getDay()) {
                case 0: $scope.selectedDay = 0; break;
                case 1: $scope.selectedDay = 1; break;
                case 6:
                default: $scope.selectedDay = 6; break;
            }

        }])

    .controller('BusCtrl', ['$scope', 'DataService', 'Swipe', '$ionicScrollDelegate', '$stateParams', '$filter',
        function ($scope, DataService, Swipe, $ionicScrollDelegate, $stateParams, $filter) {
            $scope.swipeRight = Swipe.swipeRight;
            $scope.swipeLeft = Swipe.swipeLeft;

            DataService.getBusdepartures.then(function(result) {
                result.map(function (item) {
                    item.departuretime = $filter('date')(item.date, "HH:mm");
                });
                $scope.busdepartures = result;
            });
            DataService.getBuslines.then(function(result) {
                $scope.buslines = result;
                $scope.selectedDestination = 1;
            });

            $scope.buslineFilter = function (item) {
                return item.id === $scope.selectedDestination;
            };
            $scope.depatureFilter = function (item) {
                // little hack to get all departures until 1 hour (3 -"+0200" for german MESZ = 1) after midnight
                var date = new Date(item.date);
                date.setHours(date.getHours() - 3);
                return $scope.selectedDay === date.getDay() && $scope.selectedDestination === item.busline;
            };
            $scope.scrollTop = function() {
                $ionicScrollDelegate.scrollTop();
            };

            $scope.days = [
                {id: 6, name: 'Samstag'},
                {id: 0, name: 'Sonntag'},
                {id: 1, name: 'Montag'}
            ];
            switch ((new Date()).getDay()) {
                case 0: $scope.selectedDay = 0; break;
                case 1: $scope.selectedDay = 1; break;
                case 6:
                default: $scope.selectedDay = 6; break;
            }
        }])

    .controller('PoiDetailController', ['$scope','DataService','Swipe','$stateParams',
        function($scope, DataService, Swipe, $stateParams) {
            $scope.swipeRight = Swipe.swipeRight;
            $scope.swipeLeft = Swipe.swipeLeft;

            $scope.type = $stateParams.type;

            $scope.defaults = DataService.mapDefaults;
            $scope.center = DataService.mapCenter;
            if ($stateParams.type === "busstop") {
                $scope.center = DataService.mapCenterBusstop;
            }
            if ($stateParams.type === "stage") {
                $scope.center = DataService.mapCenter;
            }
            DataService.getPoi(parseInt($stateParams.id)).then(function(result) {
                $scope.poi = result.details;
                $scope.items = result.items;
            });
            DataService.getMarkersByPois(parseInt($stateParams.id)).then(function (markers) {
                $scope.markers = markers;
            });
        }])

    .controller('ItemDetailController', ['$scope','DataService','Swipe','$stateParams',
        function($scope, DataService, Swipe, $stateParams) {
            $scope.swipeRight = Swipe.swipeRight;
            $scope.swipeLeft = Swipe.swipeLeft;

            $scope.defaults = DataService.mapDefaults;
            $scope.center = DataService.mapCenter;

            DataService.getItem(parseInt($stateParams.id)).then(function(result) {
                $scope.item = result;
            });
            DataService.getClubsByItemId(parseInt($stateParams.id)).then(function(result) {
                $scope.clubs = result;
            });
            DataService.getMarkersByItemId(parseInt($stateParams.id)).then(function (markers) {
                $scope.markers = markers;
            });
        }])

;
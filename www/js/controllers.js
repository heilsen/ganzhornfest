angular.module('ngGanzhornfest.controllers', [])
.controller('PopoverCtrl', function($scope, $ionicPopover, $ionicPopup) {
  $ionicPopover.fromTemplateUrl('templates/popover-menu.html', {
    scope: $scope
  }).then(function(popover){
    $scope.popover = popover;
  });
  $scope.openPopover = function($event) {
    $scope.popover.show($event);
  };
  //Cleanup the popover when we're done with it!
  $scope.$on('$destroy', function() {
    $scope.popover.remove();
  });

  $scope.shareAnywhere = function() {
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", null, null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
    .then(function(result) {
    }, function(err) {
      $scope.showAlert("Die App konnte mit dem Ziel nicht geteilt werden.");
    });
  };
  $scope.showAlert = function(message) {
   var alertPopup = $ionicPopup.alert({
     title: "Fehler",
     template: message
   });
  };
  $scope.showAboutPopup = function(message) {
   var alertPopup = $ionicPopup.alert({
     title: "Über die App",
     cssClass: "about-popup",
     templateUrl: 'templates/popup-about.html'
   });
  };

})

.controller('AboutCtrl', function($scope, Application) {
  $scope.version = Application.version;
})

.controller('InfoCtrl', function($scope, $state, $cordovaSocialSharing) {
  $scope.swipeLeft = function() {
    $state.go('tab.list');
  };

})

.controller('ListCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $cordovaSocialSharing) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };

  // search form
  $scope.searchTerm = "";
  $scope.clearSearch = function() {
    $scope.searchTerm = "";
  };

  // category filter
  $scope.category = "food";
  $scope.list = Detail.foods;

  $scope.updateCategoryFilter = function() {
    $ionicScrollDelegate.scrollTop();
    switch($scope.category) {
      case 'club':
        $scope.list = Detail.clubs;
        break;
      case 'food':
        $scope.list = Detail.foods;
        break;
      case 'drink':
        $scope.list = Detail.drinks;
        break;
      case 'other':
        $scope.list = Detail.otherItems;
        break;
    };
  };

})

.controller('DetailCtrl', function($scope, $stateParams, Detail, $state, $cordovaSocialSharing) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };

  itemId = parseInt($stateParams.id);
  // TODO: better use a item <-> poi mapping since this is probably is faster
  clubs = Detail.clubs;
  var getClubsByItemId = function(itemId) {
    var filteredClubs = clubs.filter(function(club) {
      return club.items.indexOf(itemId) !== -1;
    })
    return filteredClubs;
  };
  var getClubIdsByItemId = function(filteredStands) {
    var filteredClubIds = filteredStands.map(function(club){
      return parseInt(club.id);
    }); 
    return filteredClubIds;
  };
  $scope.filteredStands = getClubsByItemId(itemId);
  $scope.filteredStandsIds = getClubIdsByItemId($scope.filteredStands);

  $scope.item = Detail.item[itemId];

  $scope.defaults = angular.copy(Detail.mapDefaults);
  $scope.center = angular.copy(Detail.mapCenter);

  $scope.markersHash = angular.copy(Detail.getMarkerByPoiIds($scope.filteredStandsIds));
  $scope.markersHash = Detail.focusMarker($scope.markersHash);

})

.controller('DetailStandCtrl', function($scope, $stateParams, Detail, $state, $cordovaSocialSharing) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };

  standId = parseInt($stateParams.id);
  $scope.stand = Detail.poi[standId];

  $scope.itemArray = Detail.clubItemMap[$scope.stand.id];

  $scope.defaults = angular.copy(Detail.mapDefaults);
  $scope.center = angular.copy(Detail.mapCenter);

  $scope.markersHash = angular.copy(Detail.getMarkerByPoiIds($scope.stand.id));
  $scope.markersHash = Detail.focusMarker($scope.markersHash);

})

.controller('MapCtrl', function($scope, Detail, $state, $cordovaSocialSharing) {
  $scope.swipeRight = function() {
    $state.go('tab.list');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.program');
  };

  $scope.defaults = angular.copy(Detail.mapDefaults);
  $scope.center = angular.copy(Detail.mapCenter);
  $scope.markers = angular.copy(Detail.marker);
  $scope.tiles = {
    url: "/appdata/map-tiles/{z}/{x}/{y}.png",
    options: {
      attribution: 'Map data &copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }
	};

})

.controller('ProgramCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams, $cordovaSocialSharing) {

  $scope.swipeRight = function() {
    $state.go('tab.map');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.bus');
  };
  $scope.events = Detail.events;
  $scope.stages = Detail.stages;

  $scope.requestedDay = $stateParams.day;
  $scope.requestedStage = $stateParams.stage;

  // TODO: include scrollTop
  // $ionicScrollDelegate.scrollTop();
  $scope.stageFilter = function(item,index) {
    return $scope.requestedStage === item.id;
  };
  $scope.dayFilter = function(item,index) {
    $ionicScrollDelegate.scrollTop();
    var date = new Date(item.start);
    return parseInt($scope.requestedDay) === date.getDay();
  };

})

.controller('DetailStageCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams, $cordovaSocialSharing) {

  $scope.swipeRight = function() {
    $state.go('tab.map');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.bus');
  };

  stageId = parseInt($stateParams.id);
  $scope.poi = Detail.poi[stageId];

  $scope.defaults = angular.copy(Detail.mapDefaults);
  $scope.center = angular.copy(Detail.mapCenter);

  $scope.markersHash = angular.copy(Detail.getMarkerByPoiIds($scope.poi.id));
  $scope.markersHash = Detail.focusMarker($scope.markersHash);

})

.controller('BusCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams, $cordovaSocialSharing) {

  $scope.swipeRight = function() {
    $state.go('tab.program');
  };
  $scope.bustimes = Detail.bustimes;
  $scope.directions = Detail.directions;
  $scope.requestedDay = $stateParams.day;
  $scope.requestedDestination = "1";
  $scope.directionFilter = function(item,index) {
    $ionicScrollDelegate.scrollTop();
    return item.id === parseInt($scope.requestedDestination);
  };
  $scope.dayFilter = function(item,index) {
    $ionicScrollDelegate.scrollTop();
    var date = new Date(item.time);
    // let's also account for the two hour after midnight in this filter
    date.setHours(date.getHours()-2);
    return parseInt($scope.requestedDay) === date.getDay();
  };

})

.controller('DetailBusstopCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams, $cordovaSocialSharing) {

  $scope.swipeRight = function() {
    $state.go('tab.program');
  };

  $scope.poi = angular.copy(Detail.busstops).shift();

  $scope.markersHash = angular.copy(Detail.getMarkerByPoiIds($scope.poi.id));
  $scope.markersHash = Detail.focusMarker($scope.markersHash);
  $scope.defaults = angular.copy(Detail.mapDefaults);
  $scope.center = angular.copy(Detail.mapCenter);
  $scope.marker = angular.copy(Detail.marker);
  $scope.center.lat = $scope.marker[64].lat;
  $scope.center.lng = $scope.marker[64].lng;

})
;

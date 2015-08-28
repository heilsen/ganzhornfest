angular.module('starter.controllers', [])

.controller('InfoCtrl', function($scope, $state) {
  $scope.swipeLeft = function() {
    $state.go('tab.list');
  };

})

.controller('ListCtrl', function($scope, Detail, $state, $ionicScrollDelegate) {

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

.controller('DetailCtrl', function($scope, $stateParams, Detail, $state) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };

  $scope.filteredStands = Detail.filterClubsByItem(parseInt($stateParams.id));
  // only looking for one item
  $scope.item = Detail.getItemsByIds($stateParams.id).shift();

  $scope.center = Detail.center;
  $scope.defaults = Detail.mapDefaults;

  $scope.markersHash = Detail.getMarkers($scope.filteredStands);

})

.controller('DetailStandCtrl', function($scope, $stateParams, Detail, $state) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };

  $scope.stand = Detail.getPoi(parseInt($stateParams.id),'club');
  $scope.standName = $scope.stand.name;
  $scope.standURL = $scope.stand.url;
  $scope.standDescription = $scope.stand.description;
  $scope.standItemIds = $scope.stand.items;
  $scope.itemArray = Detail.getItemsByIds($scope.standItemIds);

  $scope.center = Detail.center;
  $scope.defaults = Detail.mapDefaults;

  $scope.markersHash = Detail.getMarker($scope.stand);

})

.controller('MapCtrl', function($scope, Detail, $state) {
  $scope.swipeRight = function() {
    $state.go('tab.list');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.program');
  };

  $scope.center = Detail.center;
  $scope.defaults = Detail.mapDefaults;

  $scope.markers =  Detail.markers;

})

.controller('ProgramCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams) {

  $scope.swipeRight = function() {
    $state.go('tab.map');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.bus');
  };
  $scope.events = Detail.getEvents();
  $scope.stages = Detail.getStages();

  $scope.requestedDay = $stateParams.day;
  $scope.requestedStage = $stateParams.stage;

  $scope.stageFilter = function(item,index) {
    $ionicScrollDelegate.scrollTop();
    return parseInt($scope.requestedStage) === item.id;
  };
  $scope.dayFilter = function(item,index) {
    $ionicScrollDelegate.scrollTop();
    var date = new Date(item.start);
    return parseInt($scope.requestedDay) === date.getDay();
  };

})

.controller('DetailStageCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams) {

  $scope.swipeRight = function() {
    $state.go('tab.map');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.bus');
  };

  $scope.poi = Detail.getPoi(parseInt($stateParams.id),'stage');

  $scope.center = Detail.center;
  $scope.defaults = Detail.mapDefaults;

  $scope.markersHash = Detail.getMarker($scope.poi);


})

.controller('BusCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams) {

  $scope.swipeRight = function() {
    $state.go('tab.program');
  };
  $scope.busstops   = Detail.getBusstops();
  $scope.directions = Detail.getDirections();
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

.controller('DetailBusstopCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams) {

  $scope.swipeRight = function() {
    $state.go('tab.program');
  };

  $scope.poi = Detail.getPoi(64,'busstop');

  $scope.center = Detail.center;
  $scope.defaults = Detail.mapDefaults;

  $scope.markersHash = Detail.generateMarkers([$scope.poi]);
  $scope.center.lat = $scope.markersHash[64].lat;
  $scope.center.lng = $scope.markersHash[64].lng;
});

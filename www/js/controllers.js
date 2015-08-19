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
  $scope.category = "food";
  $scope.list = Detail.getList($scope.category);
  $scope.sref = "detail";

  // TODO: rename other to program

  $scope.searchTerm = "";
  $scope.clearSearch = function() {
    $scope.searchTerm = "";
  };

  $scope.updateCategoryFilter = function() {
    $ionicScrollDelegate.scrollTop();
    if($scope.category == 'stands') {
      $scope.sref = 'stand';
    } else {
      $scope.sref = 'detail';
    }
    $scope.list = Detail.getList($scope.category);
  };


})

.controller('DetailStandCtrl', function($scope, $stateParams, Detail, $state) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };
  $scope.stand = Detail.getFilteredStands('stands',$stateParams.id);
  $scope.standName = $scope.stand[0].name;
  $scope.standURL = $scope.stand[0].url;
  $scope.standDescription = $scope.stand[0].description;
  $scope.standItemIDs = $scope.stand[0].items;
  $scope.itemArray = Detail.getList().filter(function(item) { return $scope.standItemIDs.indexOf(item.id) > -1; });

  $scope.mapDefaults = {
//    scrollWheelZoom: false,
//    doubleClickZoom: false,
    tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
    minZoom: 15,
  };

  $scope.neckarsulm = {
    lat: 49.1920114764648,
    lng: 9.22389686107636,
    zoom: 17,
    zoomControl: true,
  };

  $scope.markersHash = Detail.getMarker($scope.stand);

})

.controller('DetailCtrl', function($scope, $stateParams, Detail, $state) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };
  $scope.filteredStands = Detail.getFilteredStands($stateParams.type,$stateParams.id);
  $scope.itemArray = Detail.getList($stateParams.type).filter(function(item) { return item.id == $stateParams.id; });
  $scope.itemName = $scope.itemArray[0].name;

  $scope.mapDefaults = {
//    scrollWheelZoom: false,
//    doubleClickZoom: false,
    tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
    minZoom: 15,
  };

  $scope.neckarsulm = {
    lat: 49.1920114764648,
    lng: 9.22389686107636,
    zoom: 17,
    zoomControl: true,
  };

  $scope.markersHash = Detail.getMarker($scope.filteredStands);

})

.controller('MapCtrl', function($scope, Detail, $state) {

  $scope.swipeRight = function() {
    $state.go('tab.list');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.program');
  };
  $scope.stands = Detail.getList('stands'); 
  $scope.neckarsulm = {
    lat: 49.1920114764648,
    lng: 9.22389686107636,
    zoom: 17,
    zoomControl: true,
  };
  $scope.defaults = {
//    scrollWheelZoom: false,
//    doubleClickZoom: false,
    tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
    minZoom: 15,
  };
  $scope.markers = Detail.getMarker();

})

.controller('ProgramCtrl', function($scope, Detail, $state, $ionicScrollDelegate) {

  $scope.swipeRight = function() {
    $state.go('tab.map');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.bus');
  };
  $scope.events = Detail.getEvents();
  $scope.stages = Detail.getStages();
  $scope.requestedDay = "6";
  $scope.requestedStage = "0";

  $scope.stageFilter = function(item,index) {
    $ionicScrollDelegate.scrollTop();
    if(parseInt($scope.requestedStage) === 0) {
      return true;
    };
    return parseInt($scope.requestedStage) === item.id;
  };
  $scope.dayFilter = function(item,index) {
    $ionicScrollDelegate.scrollTop();
    var date = new Date(item.start);
    return parseInt($scope.requestedDay) === date.getDay();
  };

})

.controller('BusCtrl', function($scope, Detail, $state, $ionicScrollDelegate) {

  $scope.swipeRight = function() {
    $state.go('tab.program');
  };
  $scope.busstops   = Detail.getBusstops();
  $scope.directions = Detail.getDirections();
  $scope.requestedDay = "6";
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



});

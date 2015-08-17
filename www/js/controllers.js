angular.module('starter.controllers', [])

.controller('ListCtrl', function($scope, Detail) {

  $scope.category = "food";
  $scope.list = Detail.getList($scope.category);
  $scope.sref = "detail";

  // TODO: rename other to program

  $scope.searchTerm = "";
  $scope.clearSearch = function() {
    $scope.searchTerm = "";
  };

  $scope.updateCategoryFilter = function() {
    if($scope.category == 'stands') {
      $scope.sref = 'stand';
    } else {
      $scope.sref = 'detail';
    }
    $scope.list = Detail.getList($scope.category);
  };


})

.controller('ProgramCtrl', function($scope, Detail) {

  $scope.events = Detail.getEvents();
  $scope.stages = Detail.getStages();
  $scope.requestedDay = "6";
  $scope.requestedStage = "0";

  $scope.stageFilter = function(item,index) {
    if(parseInt($scope.requestedStage) === 0) {
      return true;
    };
    return parseInt($scope.requestedStage) === item.id;
  };
  $scope.dayFilter = function(item,index) {
    var date = new Date(item.start);
    return parseInt($scope.requestedDay) === date.getDay();
  };

})

.controller('BusCtrl', function($scope, Detail) {

  $scope.busstops   = Detail.getBusstops();
  $scope.directions = Detail.getDirections();
  $scope.requestedDay = "6";
  $scope.requestedDestination = "1";

  $scope.dayFilter = function(item,index) {
    var date = new Date(item.time);
    // let's also account for the two hour after midnight in this filter
    date.setHours(date.getHours()-2);
    return parseInt($scope.requestedDay) === date.getDay();
  };


})

.controller('DetailStandCtrl', ['$scope', '$stateParams', 'Detail', function($scope, $stateParams, Detail) {

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
  };

  $scope.neckarsulm = {
    lat: 49.1920114764648,
    lng: 9.22389686107636,
    zoom: 17,
  };

  $scope.markersHash = Detail.getMarker($scope.stand);

}])

.controller('DetailCtrl', ['$scope', '$stateParams', 'Detail', function($scope, $stateParams, Detail) {

  $scope.filteredStands = Detail.getFilteredStands($stateParams.type,$stateParams.id);
  $scope.itemArray = Detail.getList($stateParams.type).filter(function(item) { return item.id == $stateParams.id; });
  $scope.itemName = $scope.itemArray[0].name;

  $scope.mapDefaults = {
//    scrollWheelZoom: false,
//    doubleClickZoom: false,
    tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
  };

  $scope.neckarsulm = {
    lat: 49.1920114764648,
    lng: 9.22389686107636,
    zoom: 17,
  };

  $scope.markersHash = Detail.getMarker($scope.filteredStands);

}])

.controller('MapCtrl', function($scope, Detail) {

  $scope.stands = Detail.getList('stands'); 
  $scope.neckarsulm = {
    lat: 49.1920114764648,
    lng: 9.22389686107636,
    zoom: 17,
  };
  $scope.defaults = {
//    scrollWheelZoom: false,
//    doubleClickZoom: false,
    tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
  };
  $scope.markers = Detail.getMarker();

});

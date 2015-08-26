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
  $scope.list = Detail.getItems("food");
  $scope.updateCategoryFilter = function() {
    $ionicScrollDelegate.scrollTop();
    if($scope.category === 'stands') {
      $scope.list = Detail.getClubs();
    } else {
      $scope.list = Detail.getItems($scope.category);
    }
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

  $scope.center = Detail.getCenter();
  $scope.mapDefaults = {
    tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
    minZoom: 17,
    tileLayerOptions: {
      attribution:  "...",
      maxZoom: 21,
      maxNativeZoom: 19,
    },
  };

  var markerIconMap = {
    'club'       : 'record',
    'first_aid'  : 'ios-medkit',
    'stage'      : 'ios-musical-notes',
    'kiosk'      : 'ios-pint',
    'playground' : 'ios-football',
    'toilets'    : 'waterdrop',
  };
  var markerColorMap = {
// posible colors: 'red', 'darkred', 'orange', 'green', 'darkgreen', 'blue', 'purple', 'darkpuple', 'cadetblue'
    'club'       : 'cadetblue',
    'stage'      : 'darkpurple',
    'first_aid'  : 'red',
    'kiosk'      : 'purple',
    'playground' : 'green',
    'toilets'    : 'blue',
  };

  generateMarkers = function(clubs) {
    var markersHash = {};
    var geodata = Detail.getGeodata();

    for(var i=0; i<clubs.length; i++) {
      var club = clubs[i];
      if(typeof club.geoid === 'number') {
        markersHash[club.id] = {
          lat: geodata[club.geoid].lat,
          lng: geodata[club.geoid].lng,
          name: club.name,
          title: club.name,
          alt: club.name,
          icon: {
            type: 'awesomeMarker',
            prefix: 'ion',
            icon: markerIconMap[club.type],
            markerColor: markerColorMap[club.type],
          },
        };
      // club.geoid contains multiple ids
      } else {
        for(var j=0; j<club.geoid.length; j++) {
          markersHash[club.id + "_" + j] = {
            lat: geodata[club.geoid[j]].lat,
            lng: geodata[club.geoid[j]].lng,
            name: club.name,
            title: club.name,
            alt: club.name,
            icon: {
              type: 'awesomeMarker',
              prefix: 'ion',
              icon: markerIconMap[club.type],
              markerColor: markerColorMap[club.type],
            },
          };
        };
      };
    };
    return markersHash;
  };
 
  $scope.markersHash = generateMarkers($scope.filteredStands);

})

.controller('DetailStandCtrl', function($scope, $stateParams, Detail, $state) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };

  $scope.stand = Detail.getClub(parseInt($stateParams.id));
  $scope.standName = $scope.stand.name;
  $scope.standURL = $scope.stand.url;
  $scope.standDescription = $scope.stand.description;
  $scope.standItemIds = $scope.stand.items;
  $scope.itemArray = Detail.getItemsByIds($scope.standItemIds);

  $scope.center = Detail.getCenter();
  $scope.mapDefaults = {
    tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
    minZoom: 17,
    tileLayerOptions: {
      attribution:  "...",
      maxZoom: 21,
      maxNativeZoom: 19,
    },
  };


  var markerIconMap = {
    'club'       : 'record',
    'first_aid'  : 'ios-medkit',
    'stage'      : 'ios-musical-notes',
    'kiosk'      : 'ios-pint',
    'playground' : 'ios-football',
    'toilets'    : 'waterdrop',
  };
  var markerColorMap = {
// posible colors: 'red', 'darkred', 'orange', 'green', 'darkgreen', 'blue', 'purple', 'darkpuple', 'cadetblue'
    'club'       : 'cadetblue',
    'stage'      : 'darkpurple',
    'first_aid'  : 'red',
    'kiosk'      : 'purple',
    'playground' : 'green',
    'toilets'    : 'blue',
  };

  generateMarkers = function(club) {
    var markersHash = {};
    var geodata = Detail.getGeodata();

    if(typeof club.geoid === 'number') {
      markersHash[club.id] = {
        lat: geodata[club.geoid].lat,
        lng: geodata[club.geoid].lng,
        name: club.name,
        title: club.name,
        alt: club.name,
        icon: {
          type: 'awesomeMarker',
          prefix: 'ion',
          icon: markerIconMap[club.type],
          markerColor: markerColorMap[club.type],
        },
      };
    // club.geoid contains multiple ids
    } else {
      for(var j=0; j<club.geoid.length; j++) {
        markersHash[club.id + "_" + j] = {
          lat: geodata[club.geoid[j]].lat,
          lng: geodata[club.geoid[j]].lng,
          name: club.name,
          title: club.name,
          alt: club.name,
          icon: {
            type: 'awesomeMarker',
            prefix: 'ion',
            icon: markerIconMap[club.type],
            markerColor: markerColorMap[club.type],
          },
        };
      };
    };
    return markersHash;
  };
 
  $scope.markersHash = generateMarkers($scope.stand);

})

.controller('MapCtrl', function($scope, Detail, $state) {

  $scope.swipeRight = function() {
    $state.go('tab.list');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.program');
  };

  $scope.stands = Detail.getClubs(); 

  $scope.center = Detail.getCenter();
  $scope.defaults = {
    tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
    minZoom: 17,
    tileLayerOptions: {
      attribution:  "...",
      maxZoom: 21,
      maxNativeZoom: 19,
    },
  };

  var markerIconMap = {
    'club'       : 'record',
    'first_aid'  : 'ios-medkit',
    'stage'      : 'ios-musical-notes',
    'kiosk'      : 'ios-pint',
    'playground' : 'ios-football',
    'toilets'    : 'waterdrop',
  };
  var markerColorMap = {
// posible colors: 'red', 'darkred', 'orange', 'green', 'darkgreen', 'blue', 'purple', 'darkpuple', 'cadetblue'
    'club'       : 'cadetblue',
    'stage'      : 'darkpurple',
    'first_aid'  : 'red',
    'kiosk'      : 'purple',
    'playground' : 'green',
    'toilets'    : 'blue',
  };
  generateMarkers = function() {
    var pois = Detail.getPois();
    var markersHash = {};
    var geodata = Detail.getGeodata();

    for(var i=0; i<pois.length; i++) {
      var poi = pois[i];
      if(typeof poi.geoid === 'number') {
        markersHash[poi.id] = {
          lat: geodata[poi.geoid].lat,
          lng: geodata[poi.geoid].lng,
          name: poi.name,
          title: poi.name,
          alt: poi.name,
          icon: {
            type: 'awesomeMarker',
            prefix: 'ion',
            icon: markerIconMap[poi.type],
            markerColor: markerColorMap[poi.type],
          },
        };
      // poi.geoid contains multiple ids
      } else {
        for(var j=0; j<poi.geoid.length; j++) {
          markersHash[poi.id + "_" + j] = {
            lat: geodata[poi.geoid[j]].lat,
            lng: geodata[poi.geoid[j]].lng,
            name: poi.name,
            title: poi.name,
            alt: poi.name,
            icon: {
              type: 'awesomeMarker',
              prefix: 'ion',
              icon: markerIconMap[poi.type],
              markerColor: markerColorMap[poi.type],
            },
          };
        };
      };
    };
    return markersHash;
  };
  $scope.markers = generateMarkers();

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
    var stageMapping = {
      1: 58,
      2: 59,
      3: 60,
      4: 61,
      5: 62
    };
    $ionicScrollDelegate.scrollTop();
    if(parseInt($scope.requestedStage) === 0) {
      return true;
    };
    return parseInt(stageMapping[$scope.requestedStage]) === item.id;
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

angular.module('starter.controllers', [])

.controller('InfoCtrl', function($scope, $state, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {
  $scope.swipeLeft = function() {
    $state.go('tab.list');
  };

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

.controller('ListCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {

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
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", "Ganzhornfest App", null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
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

.controller('DetailCtrl', function($scope, $stateParams, Detail, $state, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {

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
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", "Ganzhornfest App", null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
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

.controller('DetailStandCtrl', function($scope, $stateParams, Detail, $state, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {

  $scope.swipeRight = function() {
    $state.go('tab.info');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.map');
  };

  $scope.stand = Detail.getPoi(parseInt($stateParams.id),'club');
  $scope.itemArray = Detail.getItemsByIds($scope.stand.items);

  $scope.center = Detail.center;
  $scope.defaults = Detail.mapDefaults;

  $scope.markersHash = Detail.getMarker($scope.stand);

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
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", "Ganzhornfest App", null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
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

.controller('MapCtrl', function($scope, Detail, $state, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {
  $scope.swipeRight = function() {
    $state.go('tab.list');
  };
  $scope.swipeLeft = function() {
    $state.go('tab.program');
  };

  $scope.center = Detail.center;
  $scope.defaults = Detail.mapDefaults;

  $scope.markers =  Detail.markers;

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
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", "Ganzhornfest App", null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
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

.controller('ProgramCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {

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
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", "Ganzhornfest App", null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
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

.controller('DetailStageCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {

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
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", "Ganzhornfest App", null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
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

.controller('BusCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {

  $scope.swipeRight = function() {
    $state.go('tab.program');
  };
  $scope.busstops   = Detail.busstops;
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
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", "Ganzhornfest App", null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
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

.controller('DetailBusstopCtrl', function($scope, Detail, $state, $ionicScrollDelegate, $stateParams, $cordovaSocialSharing, $ionicPopover, $ionicPopup) {

  $scope.swipeRight = function() {
    $state.go('tab.program');
  };

  $scope.poi = Detail.busstop;

  $scope.center = Detail.center;
  $scope.defaults = Detail.mapDefaults;

  $scope.markersHash = Detail.getMarker($scope.poi);
  $scope.center.lat = $scope.markersHash[64].lat;
  $scope.center.lng = $scope.markersHash[64].lng;

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
    $cordovaSocialSharing.share("Hol dir die Ganzhornfest App!", "Ganzhornfest App", null, "https://play.google.com/store/apps/details?id=de.heilsen.ganzhornfest")
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
;

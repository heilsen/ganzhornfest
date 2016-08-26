- ensure open database on deviceready
- lock screen during data loading
        // $ionicLoading.show({template: "Lade Daten aus der Datenbank..."});
        // openDatabasePromise('db.sqlite')
        //     .then(function(db) {
        //         var query = "SELECT * FROM poi order by name;";
        //         $cordovaSQLite.execute(db, query, []).then(function(queryResult) {
        //             $scope.pois = extractResult(queryResult);
        //             // $scope.list = $scope.pois;
        //             // $scope.category = "club";
        //             $ionicLoading.hide();
        //         })
        //     });

angular.module('ngGanzhornfest.services', [])
    .factory('DataService', ['$q', '$ionicPlatform', '$cordovaSQLite', function ($q, $ionicPlatform, $cordovaSQLite) {
        var db;
        var data = {};
        var poi = {};
        var geodata = [];

        function openDb() {
            return $q(function (resolve, reject) {
                $ionicPlatform.ready().then(
                    function () {
                        if (db === null || typeof db === "undefined") {
                            if (window.sqlitePlugin) {
                                db = window.sqlitePlugin.openDatabase({
                                    name: 'db.sqlite',
                                    location: 'default',
                                    createFromLocation: 1
                                });
                            }
                        }
                        if (db !== null) {
                            resolve(db)
                        } else {
                            reject("Could not open Database")
                        }
                    }
                )
            })
        }

        function queryDb(query) {
            return openDb().then(function (db) {
                return $q(function (resolve) {
                    $cordovaSQLite.execute(db, query, [])
                        .then(function (result) {
                            var rows = [];
                            for (var row = 0; row < result.rows.length; row++) {
                                rows.push(result.rows.item(row));
                            }
                            resolve(rows);
                        })
                })
            })
        }

        function getAll() {
            return $q(function (resolve) {
                if (typeof data === "undefined" || data.length !== 9) {
                    $q.all([
                        queryDb("SELECT * FROM poi WHERE type = 'club' ORDER BY name;"),
                        queryDb("SELECT * FROM items WHERE type = 'food' ORDER BY name;"),
                        queryDb("SELECT * FROM items WHERE type = 'drink' ORDER BY name;"),
                        queryDb("SELECT * FROM items WHERE type = 'other' ORDER BY name;"),
                        queryDb("SELECT id, poiid, strftime('%Y-%m-%dT%H:%M:%S',startdate) as startdate, type, " +
                            "strftime('%Y-%m-%dT%H:%M:%S',enddate) as enddate, remark, name FROM event_dates ORDER BY startdate;"),
                        queryDb("SELECT * FROM poi WHERE type = 'stage' ORDER BY id;"),
                        queryDb("SELECT id, busline, strftime('%Y-%m-%dT%H:%M:%S',date) as date FROM busdepartures ORDER BY date;"),
                        queryDb("SELECT * FROM buslines ORDER BY id;"),
                        queryDb("SELECT * FROM poi WHERE type = 'busstop' ORDER BY name;")
                    ]).then(function (result) {
                        data["clubs"] = result[0];
                        data["foods"] = result[1];
                        data["drinks"] = result[2];
                        data["other"] = result[3];
                        data["events"] = result[4];
                        data["stages"] = result[5];
                        data["busdepartures"] = result[6];
                        data["buslines"] = result[7];
                        data["busstops"] = result[8];
                        resolve(data);
                    });
                } else {
                    resolve(data);
                }
            })
        }

        function getData(key) {
            return $q(function (resolve, reject) {
                if (data[key] === null || typeof data[key] === "undefined" || data[key].length === 0) {
                    getAll()
                        .then(function (data) {
                            if (data[key] !== null && typeof data[key] !== "undefined") {
                                resolve(data[key]);
                            } else {
                                reject("Could not get data")
                            }
                        });
                }
            })
        }

        function getPoi(poiId) {
            return $q(function (resolve) {
                if (typeof poi === "undefined" || poi.length !== 2) {
                    $q.all([
                        queryDb("SELECT i.id, i.name FROM poi as p, poiid_itemid as pi, items as i where p.id=pi.poiid and pi.itemid=i.id and p.id=" + poiId + ";"),
                        queryDb("SELECT name, description, url FROM poi as p where p.id=" + poiId + ";")
                    ]).then(function (result) {
                        poi["items"] = result[0];
                        poi["details"] = result[1][0];
                        resolve(poi);
                    })
                } else {
                    resolve(poi)
                }
            })
        }

        var markerIconMap = {
            'club': 'record',
            'first_aid': 'ios-medkit',
            'busstop': 'android-bus',
            'stage': 'ios-musical-notes',
            'playground': 'ios-football',
            'toilets': 'waterdrop',
        };

        // posible colors: 'red', 'darkred', 'orange', 'green', 'darkgreen', 'blue', 'purple', 'darkpuple', 'cadetblue'
        var markerColorMap = {
            'club': 'cadetblue',
            'first_aid': 'red',
            'busstop': 'orange',
            'stage': 'darkpurple',
            'playground': 'green',
            'toilets': 'blue',
        };

        function getAllMarkers() {
            return $q(function (resolve) {
                queryDb("SELECT g.id as id,p.name,p.type,g.lat,g.lng FROM geodata as g, poiid_geoid as pg, poi as p where pg.poiid=p.id and pg.geoid=g.id")
                    .then(function (result) {
                        var marker = {};
                        var item;
                        result.forEach(function(item) {
                            marker[item.id] = {
                                lat: item.lat,
                                lng: item.lng,
                                message: item.name,
                                title: item.name,
                                alt: item.name,
                                icon: {
                                    type: 'awesomeMarker',
                                    prefix: 'ion',
                                    icon: markerIconMap[item.type],
                                    markerColor: markerColorMap[item.type]
                                }
                            };
                        });
                        resolve(marker);
                    })
            })
        }

        function getMarkersByItemId(itemId) {
            // TODO: make focusPid configureable
            var focusPid = 64;
            return $q(function(resolve) {
                queryDb("SELECT g.id as gid, i.id as iid, i.name, i.type, g.lat,g.lng, p.name as poiName FROM geodata as g, poiid_geoid as pg, poi as p, items as i, poiid_itemid as pi WHERE pg.geoid = g.id and pg.poiid = pi.poiid and pg.poiid = p.id and pi.itemid = i.id and i.id = " + itemId + ";")
                    .then(function(result) {
                        var marker = {};
                        result.forEach(function(item) {
                            marker[item.gid] = {
                                lat: item.lat,
                                lng: item.lng,
                                message: item.poiName,
                                title: item.poiName,
                                alt: item.poiName,
                                icon: {
                                    type: 'awesomeMarker',
                                    prefix: 'ion',
                                    icon: markerIconMap["club"],
                                    markerColor: markerColorMap["club"]
                                }
                            };
                            if (item.pid === focusPid) {
                                marker[item.gid].focus = true;
                            }
                        });
                        resolve(marker);
                    })
            })
        }

        function getMarkersByPois(poiIds) {
            // TODO: make focusPid configureable
            var focusPid = 64;
            return $q(function (resolve) {
                if ( typeof poiIds !== "number") {
                    poiIds = poiIds.join(',');
                }
                poiIdsList = "(" + poiIds + ")";
                queryDb("SELECT g.id as gid, p.id as pid, p.name,p.type,g.lat,g.lng FROM geodata as g, poiid_geoid as pg, poi as p WHERE pg.poiid=p.id and pg.geoid=g.id and p.id in " + poiIdsList + ";")
                    .then(function (result) {
                        var marker = {};
                        result.forEach(function(item) {
                            marker[item.gid] = {
                                lat: item.lat,
                                lng: item.lng,
                                message: item.name,
                                title: item.name,
                                alt: item.name,
                                icon: {
                                    type: 'awesomeMarker',
                                    prefix: 'ion',
                                    icon: markerIconMap[item.type],
                                    markerColor: markerColorMap[item.type]
                                }
                            };
                            if (item.pid === focusPid) {
                                marker[item.gid].focus = true;
                            }
                        });
                        resolve(marker);
                    })
            })
        }

        function getItem(itemId) {
            return $q(function(resolve) {
                queryDb("SELECT * from items WHERE id=" + itemId)
                    .then(function(result) {
                        resolve(result[0]);
                    })
            })
        }

        function getClubsByItemId(itemId) {
            return $q(function(resolve) {
                queryDb("SELECT p.id as id, p.name as name FROM items as i, poi as p, poiid_itemid as pi WHERE i.id=" + itemId + " AND i.id=pi.itemid AND pi.poiid=p.id;")
                    .then(function(result) {
                        resolve(result);
                    })
            })
        }

        var mapDefaults = {
            // tileLayer: "/appdata/map-tiles/{z}/{x}/{y}.png",
            tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
            minZoom: 17,
            tileLayerOptions: {
                maxZoom: 21,
                maxNativeZoom: 19,
                attribution: '<a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
            },
        };

        var mapCenter = {
            // TODO: get geodata by name (paulus club)
            lat: 49.191992,
            lng: 9.223657,
            zoom: 17,
        };
        var mapCenterBusstop = {
            // TODO: get geodata by name (ZOB Ballei)
            lat: 49.193846,
            lng: 9.227222,
            zoom: 17,
        };

        return {
            getClubs: getData("clubs"),
            getFoods: getData("foods"),
            getDrinks: getData("drinks"),
            getOther: getData("other"),
            getEvents: getData("events"),
            getStages: getData("stages"),
            getBusdepartures: getData("busdepartures"),
            getBuslines: getData("buslines"),
            getBusstops: getData("busstops"),
            getPoi: getPoi,
            mapDefaults: mapDefaults,
            mapCenter: mapCenter,
            mapCenterBusstop: mapCenterBusstop,
            getMarkersByPois: getMarkersByPois,
            //TODO: implement getMarkersByItems
            getMarkersByItemId: getMarkersByItemId,
            getAllMarkers: getAllMarkers,
            getItem: getItem,
            getClubsByItemId: getClubsByItemId,
        }
    }])
    .factory('Swipe', ['$ionicTabsDelegate', function ($ionicTabsDelegate) {
        return {
            swipeRight: function () {
                var selected = $ionicTabsDelegate.selectedIndex();
                if (selected != -1 && selected != 0) {
                    $ionicTabsDelegate.select(selected - 1);
                }
            },
            swipeLeft: function () {
                var selected = $ionicTabsDelegate.selectedIndex();
                if (selected != -1) {
                    $ionicTabsDelegate.select(selected + 1);
                }
            }
        }
    }])
    .run(function (DataService) {
    })
;

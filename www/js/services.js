angular.module('starter.services', [])

.factory('Detail', function() {
  var geodata = {
    1  : {lat: 49.192290,   lng: 9.223065},
    2  : {lat: 49.191768,   lng: 9.224544},
    3  : {lat: 49.191893,   lng: 9.224606},
    4  : {lat: 49.191959,   lng: 9.222619},
    5  : {lat: 49.191662,   lng: 9.224517},
    6  : {lat: 49.191618,   lng: 9.225298},
    7  : {lat: 49.191957,   lng: 9.222933},
    8  : {lat: 49.192041,   lng: 9.222668},
    9  : {lat: 49.191110,   lng: 9.222178},
    10 : {lat: 49.191835,   lng: 9.224575},
    11 : {lat: 49.192071,   lng: 9.223429},
    12 : {lat: 49.192345,   lng: 9.222834},
    13 : {lat: 49.191917,   lng: 9.222370},
    14 : {lat: 49.1914692,  lng: 9.2233070},
    15 : {lat: 49.191196,   lng: 9.222500},
    16 : {lat: 49.191778,   lng: 9.222837},
    17 : {lat: 49.191543,   lng: 9.225009},
    18 : {lat: 49.191687,   lng: 9.222300},
    19 : {lat: 49.191517,   lng: 9.222594},
    20 : {lat: 49.191805,   lng: 9.224282},
    21 : {lat: 49.191779,   lng: 9.224976},
    22 : {lat: 49.1914438,  lng: 9.2226660},
    23 : {lat: 49.192164,   lng: 9.223170},
    24 : {lat: 49.1920520,  lng: 9.2229932},
    25 : {lat: 49.191367,   lng: 9.222389},
    26 : {lat: 49.191443,   lng: 9.224773},
    27 : {lat: 49.191527,   lng: 9.225092},
    28 : {lat: 49.1915472,  lng: 9.2233634},
    29 : {lat: 49.191535,   lng: 9.223440},
    30 : {lat: 49.1917453,  lng: 9.2253106},
    31 : {lat: 49.1917584,  lng: 9.2252637},
    32 : {lat: 49.1917856,  lng: 9.2251376},
    33 : {lat: 49.191452,   lng: 9.222574},
    34 : {lat: 49.191638,   lng: 9.222142},
    35 : {lat: 49.191584,   lng: 9.225269},
    36 : {lat: 49.191691,   lng: 9.222882},
    37 : {lat: 49.191682,   lng: 9.224902},
    38 : {lat: 49.191566,   lng: 9.222624},
    39 : {lat: 49.191835,   lng: 9.224575},
    40 : {lat: 49.192232,   lng: 9.222689},
    41 : {lat: 49.192161,   lng: 9.222780},
    42 : {lat: 49.191691,   lng: 9.222882},
    43 : {lat: 49.1915875,  lng: 9.2227491},
    44 : {lat: 49.192001,   lng: 9.222960},
    45 : {lat: 49.191693,   lng: 9.222072},
    46 : {lat: 49.191992,   lng: 9.223657},
    47 : {lat: 49.191405,   lng: 9.223370},
    48 : {lat: 49.191856,   lng: 9.222512},
    49 : {lat: 49.191625,   lng: 9.224728},
    50 : {lat: 49.191428,   lng: 9.223834},
    51 : {lat: 49.1913447,  lng: 9.2237053},
    52 : {lat: 49.191808,   lng: 9.222764},
    53 : {lat: 49.191850,   lng: 9.222794},
    54 : {lat: 49.191143,   lng: 9.222473},
    55 : {lat: 49.191459,   lng: 9.223019},
    56 : {lat: 49.191715,   lng: 9.224514},
    57 : {lat: 49.191996,   lng: 9.222762},
    58 : {lat: 49.191853,   lng: 9.224752},
    59 : {lat: 49.192436,   lng: 9.223148},
    60 : {lat: 49.191764,   lng: 9.223357},
    61 : {lat: 49.191197,   lng: 9.222202},
    62 : {lat: 49.191756,   lng: 9.225421},
    63 : {lat: 49.192685,   lng: 9.222914},
    64 : {lat: 49.192106,   lng: 9.222767},
    65 : {lat: 49.191460,   lng: 9.221921},
    66 : {lat: 49.191238,   lng: 9.222961},
    67 : {lat: 49.191036,   lng: 9.222433},
    68 : {lat: 49.191665,   lng: 9.225433},
    69 : {lat: 49.192083,   lng: 9.222574},
    70 : {lat: 49.191405,   lng: 9.222139},
    71 : {lat: 49.191460,   lng: 9.224800},
    72 : {lat: 49.192608,   lng: 9.223059},
    73 : {lat: 49.193846,   lng: 9.227222},
    74 : {lat: 49.1916077,  lng: 9.2226579},
    75 : {lat: 49.191110,   lng: 9.222048},
  };
  var pois = [
    {id: 1,  name: "Arbeiter-Samariter-Bund (ASB)",               geoid: 1,  items:   [1,2,101,102,103,104], url: "", description: "", type: "club"},
    {id: 2,  name: "Bowlingsportverein (BSV)",                    geoid: 2,  items:   [3,4,5,6,110], url: "", description: "", type: "club"},
    {id: 3,  name: "Budakesser Gemeinschaft",                     geoid: 3,  items:   [7,8,107,128,201,202], url: "", description: "", type: "club"},
    {id: 4,  name: "Circolo Italiano",                            geoid: 4,  items:   [9,10,11,12,13,14,109,110,111,112,113], url: "", description: "", type: "club"},
    {id: 5,  name: "DLRG",                                        geoid: [5,74],  items:   [114,115,116,203], url: "", description: "", type: "club"},
    {id: 6,  name: "Downtownboys",                                geoid: 6,  items:   [5,117,101,102,115,106,15,104], url: "", description: "", type: "club"},
    {id: 7,  name: "DRK Ortsverein",                              geoid: 7,  items:   [16], url: "", description: "", type: "club"},
    {id: 8,  name: "Fischerei- und Sportangler-Verein (FSV)",     geoid: 8,  items:   [17,18,19,20,114,109,121,103,122], url: "", description: "", type: "club"},
    {id: 9,  name: "Freier Kindergarten Neckarsulm",              geoid: 9,  items:   [16,104,103,123,124,204,205], url: "", description: "", type: "club"},
    {id: 10, name: "Freizeitbad AQUAtoll",                        geoid: 10, items:   [206], url: "", description: "", type: "club"},
    {id: 11, name: "Georgspfadfinder",                            geoid: 11, items:   [9,21,123,117,110,104], url: "", description: "", type: "club"},
    {id: 12, name: "Gesangsverein Concordia",                     geoid: 12, items:   [22,23,24,25,117,106,109,114,103,101,102,124,125,26,27], url: "", description: "", type: "club"},
    {id: 13, name: "Gesangsverein Lassallia",                     geoid: 13, items:   [3,28,29,30,126,109,117,110], url: "", description: "", type: "club"},
    {id: 14, name: "Griechischer Fußballverein Odysseas",         geoid: 14, items:   [31,127,113,128,117,129,115,110], url: "", description: "", type: "club"},
    {id: 15, name: "Griechische Gemeinde",                        geoid: 15, items:   [32,127,128,128,110], url: "", description: "", type: "club"},
    {id: 16, name: "Harmonika-Club",                              geoid: 16, items:   [33,34,35,36,29,117,133,109,128,124,110], url: "", description: "", type: "club"},
    {id: 17, name: "Jugendfarm",                                  geoid: [17,75], items:   [37,128,207], url: "", description: "", type: "club"},
    {id: 18, name: "Junge Union Stadtverband",                    geoid: 18, items:   [116,128,134,115,110], url: "", description: "", type: "club"},
    {id: 19, name: "Jusos",                                       geoid: 19, items:   [106,128,110], url: "", description: "", type: "club"},
    {id: 20, name: "Katholischer Kirchenchor St. Dionysius",      geoid: 20, items:   [16], url: "", description: "", type: "club"},
    {id: 21, name: "KIWANIS",                                     geoid: 21, items:   [38,135,109,114,103], url: "", description: "", type: "club"},
    {id: 22, name: "Kolping Blasorchester",                       geoid: 22, items:   [39,109,114,117,110,208], url: "", description: "", type: "club"},
    {id: 23, name: "Kolpingsfamilie",                             geoid: 23, items:   [40,41,42,43,44,45,46,47,15,50,109,114,125,110], url: "", description: "", type: "club"},
    {id: 24, name: "Kolping Jugend",                              geoid: 24, items:   [48,49], url: "", description: "", type: "club"},
    {id: 25, name: "Kreatief - Kultur im Unterland",              geoid: 25, items:   [110,113,136,117], url: "", description: "", type: "club"},
    {id: 26, name: "Metropolitan Jazz Community",                 geoid: 26, items:   [51,52,25,53,47,54,55,109,117,110,128], url: "", description: "", type: "club"},
    {id: 27, name: "Neckar Valley Dancer",                        geoid: 27, items:   [56,57,48,104,101,103,124], url: "", description: "", type: "club"},
    {id: 28, name: "NSU - Fußball Aktive",                        geoid: 28, items:   [106,117,101], url: "", description: "", type: "club"},
    {id: 29, name: "NSU - Jugend-Fußball",                        geoid: 29, items:   [58,59,104,209], url: "", description: "", type: "club"},
    {id: 30, name: "NSU - Handball",                              geoid: [30,31,32], items:   [4,5,60,6,110,117,115], url: "", description: "", type: "club"},
    {id: 31, name: "NSU - Kanu",                                  geoid: 33, items:   [61,109,110], url: "", description: "", type: "club"},
    {id: 32, name: "NSU - Karate",                                geoid: 34, items:   [115,116], url: "", description: "", type: "club"},
    {id: 33, name: "NSU - Leichtathletik",                        geoid: 35, items:   [117,115], url: "", description: "", type: "club"},
    {id: 34, name: "NSU - Rugby",                                 geoid: 36, items:   [137,110], url: "", description: "", type: "club"},
    {id: 35, name: "NSU - Tischtennis 1",                         geoid: 37, items:   [117,106,103,101,102,124], url: "", description: "", type: "club"},
    {id: 36, name: "NSU - Tischtennis 2",                         geoid: 38, items:   [62,63,64,65,66,210], url: "", description: "", type: "club"},
    {id: 37, name: "Tierschutzverein Pfötchenhilfe Neckarsulm",   geoid: 39, items:   [67,68,69,117,114,109,128,110], url: "", description: "", type: "club"},
    {id: 38, name: "Sängerbund",                                  geoid: 40, items:   [5,70,109,117,104,15], url: "", description: "", type: "club"},
    {id: 39, name: "SC Amorbach",                                 geoid: 41, items:   [70,5,6,101,102,103,106,117,128], url: "", description: "", type: "club"},
    {id: 40, name: "Schützengilde",                               geoid: [42,43], items:   [71,72,4,5,3,6,139,106,133,140,110,211], url: "", description: "", type: "club"},
    {id: 41, name: "SPD Ortsverein",                              geoid: 44, items:   [73,109,114,128], url: "", description: "", type: "club"},
    {id: 42, name: "Stone Heads",                                 geoid: 45, items:   [106,109,128,114,134,124,103], url: "", description: "", type: "club"},
    {id: 43, name: "St. Paulus Club",                             geoid: 46, items:   [74,75,76,77,141,109,110,104,15], url: "www.st-paulus-club.de", description: "Der Sankt Paulus Club verfolgt ausschließlich und unmittelbar gemeinnützige, mildtätige und kirchliche Zwecke. Als Ergänzung zur organisierten Jugend- und Gemeindearbeit bietet er auch ein Treffpunkt für aufgeschlossene Personen, die ihre Freizeit gemeinsam gestalten wollen.", type: "club"},
    {id: 44, name: "Sulmanafetza",                                geoid: 47, items:   [51,117,135,106,110], url: "", description: "", type: "club"},
    {id: 45, name: "Tauchclub Walhai",                            geoid: 48, items:   [78,106,117,115,110], url: "", description: "", type: "club"},
    {id: 46, name: "TC Neckarsulm",                               geoid: 49, items:   [47,109,117,114,110], url: "", description: "", type: "club"},
    {id: 47, name: "TC Sulmtal",                                  geoid: [50,51], items:   [5,4,115,117,109,114,110], url: "", description: "", type: "club"},
    {id: 48, name: "Türkspor Neckarsulm",                         geoid: 52, items:   [79,6,80,15,81,82,83,84,85,86,101,102,142,143,144,128,103,106,117,104,145], url: "", description: "", type: "club"},
    {id: 49, name: "Türkspor Neckarsulm - Jugendabteilung",       geoid: 53, items:   [83,86,87,88,89,15,90,91,48,143,146], url: "", description: "", type: "club"},
    {id: 50, name: "Türkiyemspor Obereisesheim",                  geoid: 54, items:   [92,93,94,95,96,97,88,6,110,143], url: "", description: "", type: "club"},
    {id: 51, name: "UFC",                                         geoid: 55, items:   [115,117,116,101,103], url: "", description: "", type: "club"},
    {id: 52, name: 'Waldkindergarten "Waldzauber"',               geoid: [56,61], items:   [208], url: "", description: "", type: "club"},
    {id: 53, name: "Weinbauverein",                               geoid: 57, items:   [109,114,125,103], url: "", description: "", type: "club"},
    {id: 54, name: "Karussell",                                   geoid: 58, type: "playground"},
    {id: 55, name: "Eisenbähnle",                                 geoid: 59, type: "playground"},
    {id: 56, name: "Kinder-Flohmarkt",                            geoid: 60, type: "playground"},
    {id: 57, name: "Waldkindergarten",                            geoid: 61, type: "playground"},
    {id: 58, name: "WC",                                          geoid: [62,63,64,65,66,67], type: "toilets"},
    {id: 59, name: "Hauptbühne Museumsplatz",                     geoid: 69, type: "stage"},
    {id: 60, name: "Bühne Marktplatz",                            geoid: 68, type: "stage"},
    {id: 61, name: "Bühne Karlsplatz",                            geoid: 70, type: "stage"},
    {id: 62, name: "Stand Metropolitan Jazz Community",           geoid: 71, type: "stage"},
    {id: 63, name: "Erste-Hilfe (ASB)",                           geoid: 72, type: "first_aid"},
    {id: 64, name: "ZOB (Ballei)",                                geoid: 73, type: "busstop"},
  ];
  var items = [
    { id: 1  , type: "food",     name: "Linsen & Spätzle", remark: "Mit Saitenwürstle"   },
    { id: 2  , type: "food",     name: "Apfelküchle"     , remark: "Mit Zimt/Zucker und Vanillesoße"  },
    { id: 3  , type: "food",     name: "Steak"     , remark: "Mit Zwiebel"  },
    { id: 4  , type: "food",     name: "Currywurst"},
    { id: 5  , type: "food",     name: "Bratwurst"}, // auch Grillwurst
    { id: 6  , type: "food",     name: "Pommes frites"},
    { id: 7  , type: "food",     name: "Kesselgulasch"},
    { id: 8  , type: "food",     name: "Ungarische Paprikawürste"},
    { id: 9  , type: "food",     name: "Pizza"},
    { id: 10 , type: "food",     name: "Salciccia"},
    { id: 11 , type: "food",     name: "Arancina"},
    { id: 12 , type: "food",     name: "Lasagne al forno"},
    { id: 13 , type: "food",     name: "Spaghetti aglio olio e Peperoncino", remark:"Nur mit Vorbestellung"},
    { id: 14 , type: "food",     name: "Antipasto all'italiana"},
    { id: 15 , type: "food",     name: "Kuchen" ,remark: "Nur Sonntag"},
    { id: 16 , type: "food",     name: "Crêpes" ,remark: "Süß und pikant"},
    { id: 17 , type: "food",     name: "Geräucherte Forellen" ,remark: "Mit Kartoffelsalat"},
    { id: 18 , type: "food",     name: "Lachsbröchten"},
    { id: 19 , type: "food",     name: "Aalbrötchen"},
    { id: 20 , type: "food",     name: "Shrimps", remark: "Mit Soße"},
    { id: 21 , type: "food",     name: "Neckarsulmer Rädle"},
    { id: 22 , type: "food",     name: "Schnitzel", remark: "Mit Kartoffelsalat oder Brot"},
    { id: 23 , type: "food",     name: "Wurst vom Sud"},
    { id: 24 , type: "food",     name: "Rauchfleisch-Kräuterkäsbrot"},
    { id: 25 , type: "food",     name: "Wurstsalat"},
    { id: 26 , type: "food",     name: "Gefüllter Hals mit Spätzle + Kartoffelsalat"},
    { id: 27 , type: "food",     name: "Schälrippchen", remark:"Mit Brot"},
    { id: 28 , type: "food",     name: "Hacksteak"},
    { id: 29 , type: "food",     name: "Kartoffelsalat"},
    { id: 30 , type: "food",     name: "Zwiebelkuchen"},
    { id: 31 , type: "food",     name: "Saitenwurst", remark:"Mit Brötchen"},
    { id: 32 , type: "food",     name: "Souvlaki", remark:"Spieß mit Brötchen"},
    { id: 33 , type: "food",     name: "Maultaschen"},
    { id: 34 , type: "food",     name: "Bratwurstschnecken"},
    { id: 35 , type: "food",     name: "Fleischkäse"},
    { id: 36 , type: "food",     name: "Ganzhornteller"},
    { id: 37 , type: "food",     name: "Langos"},
    { id: 38 , type: "food",     name: "Flammkuchen"},
    { id: 39 , type: "food",     name: "Baguettes"},
    { id: 40 , type: "food",     name: "Schupfnudeln"},
    { id: 41 , type: "food",     name: "Sauerkraut"},
    { id: 42 , type: "food",     name: "Blutwurst"},
    { id: 43 , type: "food",     name: "Leberwurst"},
    { id: 44 , type: "food",     name: "Besentoast"},
    { id: 45 , type: "food",     name: "Käsebrot"},
    { id: 46 , type: "food",     name: "Rauchfleischbrot"},
    { id: 47 , type: "food",     name: "Käsewürfel"},
    { id: 48 , type: "food",     name: "Waffeln"},
    { id: 49 , type: "food",     name: "Zuckerwatte"},
    { id: 50 , type: "food",     name: "Bratwürste mit Kartoffelsalat", remark:"Nur Montag"},
    { id: 51 , type: "food",     name: "Burger"},
    { id: 52 , type: "food",     name: "Baguette mit Lachs"},
    { id: 53 , type: "food",     name: "Tapas-Teller"},
    { id: 54 , type: "food",     name: "Oliven"},
    { id: 55 , type: "food",     name: "Weißwürste", remark:"Nur Sonntag"},
    { id: 56 , type: "food",     name: "Kartoffelpuffer"},
    { id: 57 , type: "food",     name: "Maiskolben"},
    { id: 58 , type: "food",     name: "Überbackene Seele"},
    { id: 59 , type: "food",     name: "Überbackene Hörner"},
    { id: 60 , type: "food",     name: "Country-Potatoes (Kartoffelspalten)", remark:"mit Dip"},
    { id: 61 , type: "food",     name: "Raclette"},
    { id: 62 , type: "food",     name: "Gebrannte Mandeln"},
    { id: 63 , type: "food",     name: "Gebrannte Erdnüsse"},
    { id: 64 , type: "food",     name: "Magenbrot"},
    { id: 65 , type: "food",     name: "Popcorn"},
    { id: 66 , type: "food",     name: "sonstige Süßigkeiten"},
    { id: 67 , type: "food",     name: "Thai Suppe"},
    { id: 68 , type: "food",     name: "Bigos", remark: "Polnischer Eintopf"},
    { id: 69 , type: "food",     name: "Piroschki", remark: "Russische gefüllte Teigtaschen"},
    { id: 70 , type: "food",     name: "Hähnchen"},
    { id: 71 , type: "food",     name: "Bogenwurst"},
    { id: 72 , type: "food",     name: "Calamaris"},
    { id: 73 , type: "food",     name: "Snacks"},
    { id: 74 , type: "food",     name: "Seelen mit Schinken und Käse überbacken (Spitzdappen)"},
    { id: 75 , type: "food",     name: "Germknödel"},
    { id: 76 , type: "food",     name: "Kässpätzle"},
    { id: 77 , type: "food",     name: "Gulaschsuppe"},
    { id: 78 , type: "food",     name: "Weißwürste mit frischen Brezel", remark:"Sonntag, Montag"},
    { id: 79 , type: "food",     name: "Döner Kebab"},
    { id: 80 , type: "food",     name: "Pfannkuchen"},
    { id: 81 , type: "food",     name: "Köfte", remark:"verschiedene Sorten, deutsche Frikadelle"},
    { id: 82 , type: "food",     name: "türkische Fleisch-Grillspezialitäten"},
    { id: 83 , type: "food",     name: "Börek"},
    { id: 84 , type: "food",     name: "Türkische Süßspeisen aus Blätterteig"},
    { id: 85 , type: "food",     name: "Eis"},
    { id: 86 , type: "food",     name: "Gefüllter Blätterteig"},
    { id: 87 , type: "food",     name: "Su-Börek"},
    { id: 88 , type: "food",     name: "Sarma", remark:"Gefüllte Weinblätter"},
    { id: 89 , type: "food",     name: "Salate"},
    { id: 90 , type: "food",     name: "Muffins"},
    { id: 91 , type: "food",     name: "Putenwiener"},
    { id: 92 , type: "food",     name: "Soguk Mezze", remark:"Mediterrane-Platte"},
    { id: 93 , type: "food",     name: "Sac Kavurma", remark:"Hirtenpfanne"},
    { id: 94 , type: "food",     name: "Tantuni", remark:"Teigrolle mit einer Fleisch- und Gemüsefüllung"},
    { id: 95 , type: "food",     name: "Manti", remark:"Türkische Teigtasche mit Joghurtsoße"},
    { id: 96 , type: "food",     name: "Icli Köfte", remark:"Mit Hackfleisch gefüllte Bulgurklöße"},
    { id: 97 , type: "food",     name: "Chicken-Nuggets"},
    { id: 101, type: "drink",    name: "Cola"          },
    { id: 102, type: "drink",    name: "Fanta/Mirinda" },
    { id: 103, type: "drink",    name: "Sprudel sauer" }, // auch Wasser, Mineralwasser
    { id: 104, type: "drink",    name: "Kaffee"        },
    { id: 105, type: "drink",    name: "Bier (Kellerbier)"        },
    { id: 106, type: "drink",    name: "Bier (Weizenbier)"        },
    { id: 107, type: "drink",    name: "Wein (Budakesser)"        },
    { id: 109, type: "drink",    name: "Wein (Neckarsulmer)"        },
    { id: 110, type: "drink",    name: "Alkoholfreie Getränke"        }, //TODO: merge mit cola,fanta, sprudel ,... ?!
    { id: 111, type: "drink",    name: "Alkoholische Getränke"        }, // TODO: wft? rausschmeißen!
    { id: 112, type: "drink",    name: "Espresso"        },
    { id: 113, type: "drink",    name: "Red Bull"        },
    { id: 114, type: "drink",    name: "Sekt"        },
    { id: 115, type: "drink",    name: "Cocktails"        }, // auch "Barbetrieb"
    { id: 116, type: "drink",    name: "Mixgetränke"        }, // unterschied Barbetrieb? Biermischgetränke? Cocktails? Longdrinks?
    { id: 117, type: "drink",    name: "Bier (Pils)"        }, // auch "Bier", "versch. Biere"
    { id: 121, type: "drink",    name: "Orangensaft"        },
    { id: 122, type: "drink",    name: "Sprudel süß"        },
    { id: 123, type: "drink",    name: "Bionade"        },
    { id: 124, type: "drink",    name: "Apfelsaftschorle"        },
    { id: 125, type: "drink",    name: "Traubensaft(schorle)"        }, // Traubensaft und Traubensaftschorle
    { id: 126, type: "drink",    name: "Wein (Neuer Wein)"        },
    { id: 127, type: "drink",    name: "Wein (Griechischer)"        },
    { id: 128, type: "drink",    name: "Spirituosen"        }, // auch Kurze, Schnaps
    { id: 129, type: "drink",    name: "Biermischgetränke"        },
    { id: 133, type: "drink",    name: "Bier (alkoholfrei)"        },
    { id: 134, type: "drink",    name: "Longdrinks"        },
    { id: 135, type: "drink",    name: "Bier (Kölsch)"        },
    { id: 136, type: "drink",    name: "Karamalz"        },
    { id: 137, type: "drink",    name: "Rugbeer"        },
    { id: 139, type: "drink",    name: "Bier (Export)"        },
    { id: 140, type: "drink",    name: "Cola light"        },
    { id: 141, type: "drink",    name: "Bier (Landbier)"        },
    { id: 142, type: "drink",    name: "Sprite"        },
    { id: 143, type: "drink",    name: "Ayran",remark:"Türkisches Joghurt-Getränk"        },
    { id: 144, type: "drink",    name: "Gazoz",remark:"Türkische Limonade" },
    { id: 145, type: "drink",    name: "Tee" },
    { id: 146, type: "drink",    name: "Tee (türkisch)" },
    { id: 201, type: "other",    name: "Ungarische Spezialitäten", remark:"In Gläsern und Tuben"  },
    { id: 202, type: "other",    name: "Ungarisches Paprikapulver", remark:"Süß oder Scharf"  },
    { id: 203, type: "other",    name: "Schnappfalle"  },
    { id: 204, type: "other",    name: "Tombola"  },
    { id: 205, type: "other",    name: "Bastelangebote"  },
    { id: 206, type: "other",    name: "Glücksrad"  },
    { id: 207, type: "other",    name: "Ponyreiten"  },
    { id: 208, type: "other",    name: "Kinderspiele"  },
    { id: 209, type: "other",    name: "Losbude"  },
    { id: 210, type: "other",    name: "Wurfwand"  },
    { id: 211, type: "other",    name: "Schießbudenstand"  },
  ];
  var events = [
    {id: 1,  poi: 59, start: "2015-09-05T16:00:00+0200", end: "2015-09-05T16:00:00+0200", type: 'speech', type: 'music', name: "Eröffnung", remark: "Oberbürgermeister Joachim Scholz<br>Württ. Weinprinziessin Annekatrin Gauger"},
    {id: 2,  poi: 59, start: "2015-09-05T16:00:00+0200", end: "2015-09-05T17:30:00+0200", type: 'music', name: "MV Obereisesheim", remark: ""},
    {id: 3,  poi: 59, start: "2015-09-05T18:40:00+0200", end: "2015-09-05T19:00:00+0200", type: 'dance', name: 'Tanzgruppe "Hotsteppers"', remark: "Neckarsulmer Sportunion"},
    {id: 4,  poi: 59, start: "2015-09-05T20:00:00+0200", end: "2015-09-05T23:00:00+0200", type: 'music', name: 'MGV Dahenfeld', remark: ""},
    {id: 5,  poi: 60, start: "2015-09-05T20:00:00+0200", end: "", type: 'music', name: 'Crazy Zoo', remark: ""},
    {id: 6,  poi: 62, start: "2015-09-05T19:00:00+0200", end: "", type: 'music', name: 'MET-Swing Unit', remark: ""},
    {id: 7,  poi: 59, start: "2015-09-06T11:00:00+0200", end: "2015-09-06T11:00:00+0200", type: 'music', name: "Harmonikaclub", remark: ""},
    {id: 8,  poi: 59, start: "2015-09-06T13:15:00+0200", end: "2015-09-06T13:45:00+0200", type: 'dance', name: "Square Dance", remark: "Neckar-Valley-Dancers"},
    {id: 9,  poi: 59, start: "2015-09-06T14:00:00+0200", end: "2015-09-06T14:30:00+0200", type: 'show', name: "Aikido-Vorführung", remark: "SC Amorbach"},
    {id: 10, poi: 59, start: "2015-09-06T15:00:00+0200", end: "2015-09-06T17:00:00+0200", type: 'music', name: "Blaskapelle der Audi Bläserphilharmonie aus Ingolstadt", remark: ""},
    {id: 11, poi: 59, start: "2015-09-06T18:00:00+0200", end: "", type: 'music', name: "Biggi & Friends", remark: ""},
    {id: 12, poi: 60, start: "2015-09-06T18:00:00+0200", end: "", type: 'music', name: "The Shakermakers", remark: ""},
    {id: 13, poi: 61, start: "2015-09-06T10:15:00+0200", end: "", type: 'church', name: "Ökumenischer Gottesdienst zum Ganzhornfest", remark: "bei schlechtem Wetter in der Klosterkirche"},
    {id: 14, poi: 62, start: "2015-09-06T11:00:00+0200", end: "2015-09-06T14:00:00+0200", type: 'music', name: "Oldtime Jazz Collegium", remark: ""},
    {id: 15, poi: 62, start: "2015-09-06T19:00:00+0200", end: "", type: 'music', name: "Jazzophonics Big Band", remark: ""},
    {id: 16, poi: 59, start: "2015-09-07T17:40:00+0200", end: "2015-09-07T18:00:00+0200", type: 'dance', name: 'Tanzgruppe "Hotsteppers"', remark: "Neckarsulmer Sportunion"},
    {id: 17, poi: 59, start: "2015-09-07T19:30:00+0200", end: "2015-09-07T22:00:00+0200", type: 'music', name: "Polizei Big Band Heilbronn", remark: ""},
    {id: 18, poi: 60, start: "2015-09-07T19:00:00+0200", end: "", type: 'music', name: "Janas Jungs", remark: ""},
    {id: 19, poi: 62, start: "2015-09-07T19:00:00+0200", end: "", type: 'music', name: "Combination Big Band", remark: ""},
    {id: 20, poi: 61, start: "2015-09-05T16:00:00+0200", end: "2015-09-05T18:30:00+0200", type: 'youth', name: "Graffiti Battle", remark: ""},
    {id: 21, poi: 61, start: "2015-09-05T16:00:00+0200", end: "2015-09-05T18:00:00+0200", type: 'youth', name: "Ponyreiten", remark: "Jugendfarmverein"},
    {id: 22, poi: 61, start: "2015-09-05T16:30:00+0200", end: "2015-09-05T18:30:00+0200", type: 'show', name: "Kreatief Open Stage", remark: "Kreatief"},
//    {id: 23, poi: 61, start: "2015-09-05T20:00:00+0200", end: "2015-09-05T23:30:00+0200", type: 'music', name: "Bunter Musikcocktail mit Bands aus der Region", remark: 'Highlight: "Fürschd Renjay + Nafdalin (Schwobeland)"'},
    {id: 25, poi: 61, start: "2015-09-06T11:00:00+0200", end: "2015-09-06T18:00:00+0200", type: 'youth', name: "Ponyreiten", remark: "Jugendfarmverein"},
    {id: 26, poi: 61, start: "2015-09-06T14:00:00+0200", end: "2015-09-06T18:00:00+0200", type: 'youth', name: 'BAMBOX', remark: 'Kreativaktion vom Kinder-Jugend-Kultur Zentrum "Gleis 3"'},
    {id: 27, poi: 61, start: "2015-09-06T14:00:00+0200", end: "2015-09-06T18:00:00+0200", type: 'youth', name: 'Kinderschminken', remark: "Mit dem Tigermobil"},
    {id: 28, poi: 61, start: "2015-09-06T14:00:00+0200", end: "2015-09-06T14:40:00+0200", type: 'youth', name: 'Kaspertheater', remark: ""},
    {id: 29, poi: 61, start: "2015-09-06T16:00:00+0200", end: "2015-09-06T16:40:00+0200", type: 'youth', name: 'Kaspertheater', remark: ""},
    {id: 30, poi: 61, start: "2015-09-06T16:00:00+0200", end: "2015-09-06T17:00:00+0200", type: 'youth', name: 'Kunterbunte Spiel- und Bastelaktionen', remark: "Freier Kindergarten und Waldkindergarten"},
//    {id: 31, poi: 61, start: "2015-09-06T19:00:00+0200", end: "2015-09-06T23:00:00+0200", type: 'music', name: 'Bands aus der Region von Rock bis Metal', remark: "Highlights: Strangelet, The Prophecy 23"},
    {id: 33, poi: 61, start: "2015-09-07T17:00:00+0200", end: "", type: 'show', name: "Kreatiefes Abendprogramm", remark: ""},
    {id: 34, poi: 61, start: "2015-09-07T18:30:00+0200", end: "2015-09-07T19:00:00+0200", type: 'music', name: "Smartins", remark: "A Cappella Chor"},
    {id: 35, poi: 61, start: "2015-09-07T19:00:00+0200", end: "2015-09-07T19:30:00+0200", type: 'music', name: "Annalena & Sofie", remark: "Acoustic Duo"},
    {id: 36, poi: 61, start: "2015-09-07T19:45:00+0200", end: "2015-09-07T20:30:00+0200", type: 'music', name: "Beauties and the Beats", remark: "A Cappella Chor"},
    {id: 37, poi: 61, start: "2015-09-07T20:30:00+0200", end: "2015-09-07T23:00:00+0200", type: 'music', name: "3,2,1...los! mit Special Guests", remark: "Party Cover Band"},
    {id: 38, poi: 61, start: "2015-09-05T20:00:00+0200", end: "2015-09-05T20:45:00+0200", type: 'music', name: "We caught the Birds", remark: ''},
    {id: 39, poi: 61, start: "2015-09-05T21:00:00+0200", end: "2015-09-05T21:20:00+0200", type: 'music', name: "Sanil", remark: ''},
    {id: 40, poi: 61, start: "2015-09-05T21:30:00+0200", end: "2015-09-05T22:15:00+0200", type: 'music', name: 'LAX & Q.U.E.', remark: ''},
    {id: 41, poi: 61, start: "2015-09-05T22:30:00+0200", end: "2015-09-05T23:30:00+0200", type: 'music', name: 'Fürschd Renjay & Nafdalin (Schwobeland)', remark: ''},
    {id: 42, poi: 61, start: "2015-09-06T19:45:00+0200", end: "2015-09-06T20:30:00+0200", type: 'music', name: 'Chase the Sun', remark: ""},
    {id: 43, poi: 61, start: "2015-09-06T20:45:00+0200", end: "2015-09-06T21:30:00+0200", type: 'music', name: 'Strangelet', remark: ""},
    {id: 44, poi: 61, start: "2015-09-06T21:45:00+0200", end: "2015-09-06T22:30:00+0200", type: 'music', name: 'The Prophecy 23', remark: ""},
  ];
  var directions = [
    {id: 1, destination: "Amorbach (Linie 624/691/Sonderbus)"},
    {id: 2, destination: "Dahenfeld (Linie 624/629/Sonderbus)"},
    {id: 3, destination: "Neuberg (Linie 92/Sonderbus)"},
    {id: 4, destination: "Obereisesheim (Linie 694/695/94/Sonderbus)"},
  ];
  var busstops = [
    {id: 1,  direction: 1, time: "2015-09-05T19:33:00+0200"},
    {id: 2,  direction: 1, time: "2015-09-05T20:18:00+0200"},
    {id: 3,  direction: 1, time: "2015-09-05T21:18:00+0200"},
    {id: 4,  direction: 1, time: "2015-09-05T22:09:00+0200"},
    {id: 5,  direction: 1, time: "2015-09-05T22:18:00+0200"},
    {id: 6,  direction: 1, time: "2015-09-05T22:45:00+0200"},
    {id: 7,  direction: 1, time: "2015-09-05T23:19:00+0200"},
    {id: 8,  direction: 1, time: "2015-09-05T23:45:00+0200"},
    {id: 9,  direction: 1, time: "2015-09-06T00:45:00+0200"},
    {id: 10, direction: 1, time: "2015-09-06T19:48:00+0200"},
    {id: 11, direction: 1, time: "2015-09-06T20:10:00+0200"},
    {id: 12, direction: 1, time: "2015-09-06T20:48:00+0200"},
    {id: 13, direction: 1, time: "2015-09-06T21:13:00+0200"},
    {id: 14, direction: 1, time: "2015-09-06T21:48:00+0200"},
    {id: 15, direction: 1, time: "2015-09-06T22:45:00+0200"},
    {id: 16, direction: 1, time: "2015-09-06T22:48:00+0200"},
    {id: 17, direction: 1, time: "2015-09-06T23:45:00+0200"},
    {id: 18, direction: 1, time: "2015-09-07T00:45:00+0200"},
    {id: 19, direction: 1, time: "2015-09-07T19:48:00+0200"},
    {id: 20, direction: 1, time: "2015-09-07T20:18:00+0200"},
    {id: 21, direction: 1, time: "2015-09-07T20:48:00+0200"},
    {id: 22, direction: 1, time: "2015-09-07T21:18:00+0200"},
    {id: 23, direction: 1, time: "2015-09-07T21:48:00+0200"},
    {id: 24, direction: 1, time: "2015-09-07T22:18:00+0200"},
    {id: 25, direction: 1, time: "2015-09-07T22:48:00+0200"},
    {id: 26, direction: 1, time: "2015-09-07T23:48:00+0200"},
    {id: 27, direction: 1, time: "2015-09-08T00:45:00+0200"},
    {id: 28, direction: 2, time: "2015-09-05T19:33:00+0200"},
    {id: 29, direction: 2, time: "2015-09-05T22:09:00+0200"},
    {id: 30, direction: 2, time: "2015-09-05T22:45:00+0200"},
    {id: 31, direction: 2, time: "2015-09-05T23:19:00+0200"},
    {id: 32, direction: 2, time: "2015-09-05T23:45:00+0200"},
    {id: 33, direction: 2, time: "2015-09-06T00:19:00+0200"},
    {id: 34, direction: 2, time: "2015-09-06T00:45:00+0200"},
    {id: 35, direction: 2, time: "2015-09-06T20:10:00+0200"},
    {id: 36, direction: 2, time: "2015-09-06T21:13:00+0200"},
    {id: 37, direction: 2, time: "2015-09-06T22:45:00+0200"},
    {id: 38, direction: 2, time: "2015-09-06T23:45:00+0200"},
    {id: 39, direction: 2, time: "2015-09-07T00:45:00+0200"},
    {id: 40, direction: 2, time: "2015-09-07T19:48:00+0200"},
    {id: 41, direction: 2, time: "2015-09-07T20:48:00+0200"},
    {id: 42, direction: 2, time: "2015-09-07T21:48:00+0200"},
    {id: 43, direction: 2, time: "2015-09-07T22:33:00+0200"},
    {id: 44, direction: 2, time: "2015-09-07T22:48:00+0200"},
    {id: 45, direction: 2, time: "2015-09-07T23:48:00+0200"},
    {id: 46, direction: 2, time: "2015-09-08T00:45:00+0200"},
    {id: 47, direction: 3, time: "2015-09-05T19:48:00+0200"},
    {id: 48, direction: 3, time: "2015-09-05T22:30:00+0200"},
    {id: 49, direction: 3, time: "2015-09-05T23:30:00+0200"},
    {id: 50, direction: 3, time: "2015-09-05T23:48:00+0200"},
    {id: 51, direction: 3, time: "2015-09-06T00:18:00+0200"},
    {id: 52, direction: 3, time: "2015-09-06T00:30:00+0200"},
    {id: 53, direction: 3, time: "2015-09-06T19:48:00+0200"},
    {id: 54, direction: 3, time: "2015-09-06T22:30:00+0200"},
    {id: 55, direction: 3, time: "2015-09-06T23:30:00+0200"},
    {id: 56, direction: 3, time: "2015-09-07T00:30:00+0200"},
    {id: 57, direction: 3, time: "2015-09-07T19:48:00+0200"},
    {id: 58, direction: 3, time: "2015-09-07T20:48:00+0200"},
    {id: 59, direction: 3, time: "2015-09-07T21:48:00+0200"},
    {id: 60, direction: 3, time: "2015-09-07T22:33:00+0200"},
    {id: 61, direction: 3, time: "2015-09-07T22:48:00+0200"},
    {id: 61, direction: 3, time: "2015-09-07T23:30:00+0200"},
    {id: 62, direction: 3, time: "2015-09-08T00:30:00+0200"},
    {id: 63, direction: 4, time: "2015-09-05T19:48:00+0200"},
    {id: 64, direction: 4, time: "2015-09-05T20:48:00+0200"},
    {id: 65, direction: 4, time: "2015-09-05T21:48:00+0200"},
    {id: 66, direction: 4, time: "2015-09-05T22:48:00+0200"},
    {id: 67, direction: 4, time: "2015-09-05T23:10:00+0200"},
    {id: 68, direction: 4, time: "2015-09-05T23:42:00+0200"},
    {id: 69, direction: 4, time: "2015-09-06T00:10:00+0200"},
    {id: 70, direction: 4, time: "2015-09-06T00:12:00+0200"},
    {id: 71, direction: 4, time: "2015-09-06T19:48:00+0200"},
    {id: 72, direction: 4, time: "2015-09-06T20:20:00+0200"},
    {id: 73, direction: 4, time: "2015-09-06T21:18:00+0200"},
    {id: 74, direction: 4, time: "2015-09-06T22:18:00+0200"},
    {id: 75, direction: 4, time: "2015-09-06T23:10:00+0200"},
    {id: 76, direction: 4, time: "2015-09-07T00:10:00+0200"},
    {id: 77, direction: 4, time: "2015-09-07T19:48:00+0200"},
    {id: 78, direction: 4, time: "2015-09-07T20:03:00+0200"},
    {id: 79, direction: 4, time: "2015-09-07T20:18:00+0200"},
    {id: 80, direction: 4, time: "2015-09-07T20:36:00+0200"},
    {id: 81, direction: 4, time: "2015-09-07T21:03:00+0200"},
    {id: 82, direction: 4, time: "2015-09-07T21:36:00+0200"},
    {id: 83, direction: 4, time: "2015-09-07T21:48:00+0200"},
    {id: 84, direction: 4, time: "2015-09-07T22:06:00+0200"},
    {id: 85, direction: 4, time: "2015-09-07T22:27:00+0200"},
    {id: 86, direction: 4, time: "2015-09-07T23:05:00+0200"},
    {id: 87, direction: 4, time: "2015-09-08T00:10:00+0200"},
  ];

  var markerIconMap = {
    'club'       : 'record',
    'first_aid'  : 'ios-medkit',
    'busstop'    : 'android-bus',
    'stage'      : 'ios-musical-notes',
    'playground' : 'ios-football',
    'toilets'    : 'waterdrop',
  };

  // posible colors: 'red', 'darkred', 'orange', 'green', 'darkgreen', 'blue', 'purple', 'darkpuple', 'cadetblue'
  var markerColorMap = {
    'club'       : 'cadetblue',
    'first_aid'  : 'red',
    'busstop'    : 'orange',
    'stage'      : 'darkpurple',
    'playground' : 'green',
    'toilets'    : 'blue',
  };

  // params optional type
  // returns array all POIs or only those of given type
  _getPois = function(type) {
    if(typeof type === 'undefined') {
      return pois;
    };
    var poisOfGivenType = pois.filter(function(poi) {
      return poi.type === type;
    });
    return poisOfGivenType;
  };

  // returns array clubs
  _getClubs = function() {
    var clubs = _getPois("club");
    return clubs;
  };

  // params optional string itemType
  // returns array items
  _getItems = function(itemType) {
    if(typeof itemType === 'undefined') {
      return items;
    };
    var itemsOfGivenType = items.filter(function(item) { 
      return item.type === itemType; 
    });
    itemsOfGivenType.sort(function(a, b) {
      return a.name.localeCompare(b.name);
    })
    return itemsOfGivenType;
  };

  // returns array foods
  _getFoods = function() {
    var foods = _getItems('food');
    return foods;
  };

  // returns array drinks
  _getDrinks = function() {
    var drinks = _getItems('drink');
    return drinks;
  };

  // returns array otherItems
  _getOtherItems = function() {
    var otherItems = _getItems('other');
    return otherItems;
  };

  _generateMarkers = function(poisList) {
    var markersHash = {};
    var geoinfo = geodata;

    if(typeof poisList === 'undefined') {
      var poisList = pois;
    };

    for(var i=0; i<poisList.length; i++) {
      var poi = poisList[i];
      if(typeof poi.geoid === 'number') {
        markersHash[poi.id] = {
          lat: geoinfo[poi.geoid].lat,
          lng: geoinfo[poi.geoid].lng,
          message: poi.name,
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
            lat: geoinfo[poi.geoid[j]].lat,
            lng: geoinfo[poi.geoid[j]].lng,
            message: poi.name,
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
    var keys = Object.keys(markersHash);
    if(keys.length === 1) {
      markersHash[keys[0]].focus = true;
    };
    return markersHash;
  };

  _getCenter = function() {
    return {
      lat: geodata[46].lat,
      lng: geodata[46].lng,
      zoom: 17,
    };
  };
  _getMapDefaults = function() {
    return {
      tileLayer: "http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      minZoom: 17,
      tileLayerOptions: {
        maxZoom: 21,
        maxNativeZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
      },
    };
  };
 
  return {

    // returns array pois
    getPois: _getPois,

    // params number clubId
    // returns object club
    getPoi: function(poiId,poiType) {
      var pois = _getPois(poiType);
      for(var i=0; i<pois.length; i++) {
        if (pois[i].id === poiId) {
          var poi = pois[i];
          break;
        };
      };
      return poi;
    },

    // params number itemId
    // returns array clubs which provide itemId
    filterClubsByItem: function(itemId) {
      var clubs = _getClubs();
      var clubsWithCertainItem = clubs.filter(function(club) {
        return club.items.indexOf(itemId) !== -1;
      });
      return clubsWithCertainItem;
    },

    // returns hash items
    getItems: _getItems,

    // params array itemIds
    // returns array items which match given Ids
    getItemsByIds: function(itemIds) {
      var items = _getItems();
      var filteredItemsArray = [];
      if ( typeof itemIds === 'string') {
        filteredItemsArray = items.filter(function(item) {
          return item.id === parseInt(itemIds);
        });
      } else {
        filteredItemsArray = items.filter(function(item) {
          return itemIds.indexOf(item.id) !== -1;
        });
      }
      return filteredItemsArray;
    },

    // params string category
    // returns array all clubs or all items of given category
    getListByCategory: function(category) {
      if(category === 'stands') {
        return _getClubs();
      } else {
        return _getItems(category);
      };
    },

    // returns array events
    getEvents: function() {
      return events;
    },

    // returns hash stages
    getStages: function() {
      return _getPois("stage");
    },

    // return array busstops
    getBusstops: function() {
      return busstops;
    },

    // returns hash geodata
    getGeodata: function() {
      return  geodata;
    },

    getDirections: function() {
      return directions;
    },
    getMarker: function(poi) {
      return _generateMarkers([poi]);
    },
    getMarkers: function(pois) {
      return _generateMarkers(pois);
    },
    clubs: _getClubs(),
    foods: _getFoods(),
    drinks: _getDrinks(),
    otherItems: _getOtherItems(),
    markers: _generateMarkers(),
    center: _getCenter(),
    mapDefaults: _getMapDefaults(),
  };
})
.run(function(Detail){
})
;

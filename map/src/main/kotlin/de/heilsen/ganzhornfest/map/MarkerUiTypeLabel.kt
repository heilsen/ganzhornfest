package de.heilsen.ganzhornfest.map

fun MarkerUiType.germanLabel(): String =
    when (this) {
        MarkerUiType.CLUB -> "Stand"
        MarkerUiType.EVENT_LOCATION -> "Veranstaltungsort"
        MarkerUiType.PLAYGROUND -> "Spielplatz"
        MarkerUiType.ATTRACTION -> "Attraktion"
        MarkerUiType.WC -> "WC"
        MarkerUiType.FIRST_AID -> "Erste Hilfe"
        MarkerUiType.BUS_STOP -> "Bus"
    }

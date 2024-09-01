package de.heilsen.ganzhornfest.map

sealed interface MapEvent {
    data object Init: MapEvent
}

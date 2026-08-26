package de.heilsen.ganzhornfest.map

sealed interface MapEvent {
    data object Init : MapEvent

    data class ApplyLatLng(
        val coordinateId: Long,
        val lat: Double,
        val lng: Double,
    ) : MapEvent
}

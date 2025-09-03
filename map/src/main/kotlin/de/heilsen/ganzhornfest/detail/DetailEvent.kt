package de.heilsen.ganzhornfest.detail

sealed interface DetailEvent {
    data class Club(val clubName: String): DetailEvent
    data class Offer(val offerName: String) : DetailEvent
    data object Init: DetailEvent
}


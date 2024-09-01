package de.heilsen.ganzhornfest.detail

sealed interface DetailEvent {
    data class Club(val clubName: String): DetailEvent
    data class Offer(val offerMame: String, val offerType: OfferTypeUi): DetailEvent
    data object Init: DetailEvent
}


package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.map.MapModel

sealed interface DetailModel {
    data object Loading : DetailModel

    data class Club(
        val clubName: String,
        val offerList: List<String>,
        val mapModel: MapModel
    ) : DetailModel

    data class Offer(
        val offerName: String,
        val clubs: List<String>,
        val mapModel: MapModel
    ) : DetailModel
}
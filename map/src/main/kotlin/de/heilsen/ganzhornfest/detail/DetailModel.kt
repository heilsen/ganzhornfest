package de.heilsen.ganzhornfest.detail

import androidx.annotation.Keep
import de.heilsen.ganzhornfest.map.MapModel

sealed interface DetailModel {
    data object Loading : DetailModel
    data class Success(
        val title: String,
        val type: DetailType,
        val mapModel: MapModel,
        val items: List<String>
    ) : DetailModel
}

@Keep // needed because it is part of a serializable Navigation destination
enum class DetailType {
    Club, Offer;
}
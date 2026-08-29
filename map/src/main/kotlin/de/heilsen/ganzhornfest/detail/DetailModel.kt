package de.heilsen.ganzhornfest.detail

import androidx.annotation.Keep
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet

sealed interface DetailModel {
    data object Loading : DetailModel

    data class Success(
        val title: String,
        val type: DetailType,
        val items: List<DetailItem>,
    ) : DetailModel
}

fun DetailModel.Success.highlightTitles(): ImmutableSet<String> =
    when (type) {
        DetailType.Club, DetailType.Poi -> persistentSetOf(title)
        DetailType.Offer, DetailType.PoiCategory -> items.map { it.name }.toPersistentSet()
    }

data class DetailItem(
    val name: String,
    val description: String? = null,
    val routeKey: String = name,
)

@Keep
enum class DetailType {
    Club,
    Offer,
    Poi,
    PoiCategory,
}

package de.heilsen.ganzhornfest.detail

import androidx.annotation.Keep
import de.heilsen.ganzhornfest.map.MarkerUiType
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentSet

sealed interface DetailModel {
    data object Loading : DetailModel

    data class Success(
        val title: String,
        val target: DetailTarget,
        val items: List<DetailItem>,
    ) : DetailModel
}

fun DetailModel.Success.highlightTitles(): ImmutableSet<String> =
    when (target) {
        is DetailTarget.Club, is DetailTarget.Poi -> persistentSetOf(title)
        is DetailTarget.Offer, is DetailTarget.Category -> items.map { it.name }.toPersistentSet()
    }

data class DetailItem(
    val name: String,
    val description: String? = null,
    val target: DetailTarget,
)

sealed interface DetailTarget {
    data class Club(
        val poiId: Long,
    ) : DetailTarget

    data class Offer(
        val offerId: Long,
    ) : DetailTarget

    data class Poi(
        val poiId: Long,
    ) : DetailTarget

    data class Category(
        val type: MarkerUiType,
    ) : DetailTarget
}

@Keep
enum class DetailType {
    Club,
    Offer,
    Poi,
}

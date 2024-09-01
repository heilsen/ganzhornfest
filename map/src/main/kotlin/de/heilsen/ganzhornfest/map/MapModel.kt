package de.heilsen.ganzhornfest.map

import kotlinx.collections.immutable.ImmutableSet

sealed interface MapModel {
    data class Data(
        val markers: ImmutableSet<MarkerUi>,
    ) : MapModel

    data object Loading : MapModel
}

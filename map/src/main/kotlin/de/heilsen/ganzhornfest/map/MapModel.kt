package de.heilsen.ganzhornfest.map

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf

sealed class MapModel(
    open val isFullscreen: Boolean = true,
) {
    data class Data(
        val markers: ImmutableSet<MarkerUi>,
        override val isFullscreen: Boolean = true,
        val showLegend: Boolean = true,
        val showWindowInfo: Boolean = false,
        val pins: ImmutableList<ClubPin> = persistentListOf(),
    ) : MapModel(isFullscreen)

    data class Loading(
        override val isFullscreen: Boolean = true,
    ) : MapModel(isFullscreen)
}

data class PinEditorModel(
    val pins: ImmutableList<ClubPin>,
    val selected: ClubPin?,
)

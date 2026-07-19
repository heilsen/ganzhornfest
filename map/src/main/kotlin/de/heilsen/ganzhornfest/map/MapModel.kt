package de.heilsen.ganzhornfest.map

import kotlinx.collections.immutable.ImmutableSet

sealed class MapModel(
    open val isFullscreen: Boolean = true,
) {
    data class Data(
        val markers: ImmutableSet<MarkerUi>,
        override val isFullscreen: Boolean = true,
        val showLegend: Boolean = true,
        val showWindowInfo: Boolean = false,
    ) : MapModel(isFullscreen)

    data class Loading(
        override val isFullscreen: Boolean = true,
    ) : MapModel(isFullscreen)
}

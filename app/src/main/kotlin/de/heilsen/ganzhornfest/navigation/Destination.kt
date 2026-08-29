package de.heilsen.ganzhornfest.navigation

import de.heilsen.ganzhornfest.detail.DetailType
import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Map : Destination

    @Serializable
    data class Program(
        val stage: String? = null,
    ) : Destination

    @Serializable
    data object Info : Destination

    @Serializable
    data object Bus : Destination

    @Serializable
    data class Detail(
        val title: String,
        val type: DetailType,
    ) : Destination
}

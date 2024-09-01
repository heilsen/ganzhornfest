package de.heilsen.ganzhornfest.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Home : Destination
    @Serializable
    data object Map : Destination
    @Serializable
    data object Search : Destination
    @Serializable
    data object Program : Destination
    @Serializable
    data object Info : Destination
    @Serializable
    data object Bus : Destination
    @Serializable
    data class Detail(val title: String, val type: String) : Destination
}
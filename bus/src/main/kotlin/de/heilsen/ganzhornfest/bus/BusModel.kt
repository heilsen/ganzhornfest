package de.heilsen.ganzhornfest.bus

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

sealed interface BusModel {
    data object Loading: BusModel
    @Immutable
    data class Data(
        val destination: String,
        val destinations: ImmutableList<String>,
        val departureDate: LocalDate,
        val departureDates: ImmutableList<LocalDate>,
        val connections: ImmutableList<BusConnection>
    ): BusModel
}
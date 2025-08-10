package de.heilsen.ganzhornfest.bus

import kotlinx.datetime.LocalDate

sealed interface BusEvent {
    data object Init: BusEvent
    data class ChangeDestination(val destination: String): BusEvent
    data class ChangeDeparture(val departure: LocalDate): BusEvent
}
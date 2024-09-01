package de.heilsen.ganzhornfest.program

import kotlinx.datetime.LocalDate

sealed interface ProgramEvent {
    data object Init: ProgramEvent
    data class ChangeLocation(val location: String?): ProgramEvent
    data class ChangeDate(val date: LocalDate): ProgramEvent
}
package de.heilsen.ganzhornfest.program

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

sealed interface ProgramModel {
    data class Data(
        val locations: ImmutableList<String>,
        val selectedLocation: String?,
        val dates: ImmutableList<LocalDate>,
        val selectedDate: LocalDate,
        val programs: ImmutableList<Program>
    ) : ProgramModel
    data object Loading : ProgramModel
}
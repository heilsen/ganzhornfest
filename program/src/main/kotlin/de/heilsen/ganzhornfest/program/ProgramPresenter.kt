package de.heilsen.ganzhornfest.program

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.heilsen.ganzhornfest.core.GetOpeningDaysUseCase
import de.heilsen.ganzhornfest.core.SelectDefaultDateUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import javax.inject.Inject

class ProgramPresenter @Inject constructor(
    private val getPrograms: GetProgramsUseCase,
    private val getStages: GetStagesUseCase,
    selectDefaultDate: SelectDefaultDateUseCase,
    getOpeningDays: GetOpeningDaysUseCase
) {
    private var selectedDate: LocalDate by mutableStateOf(selectDefaultDate())
    private val dates = getOpeningDays()

    @Composable
    fun present(events: Flow<ProgramEvent>): ProgramModel {
        val locations: ImmutableList<String> by getStages().collectAsState(initial = persistentListOf())
        var selectedLocation: String? by remember { mutableStateOf("") }
        if (locations.isNotEmpty() && selectedLocation.isNullOrEmpty()) selectedLocation = locations.first()

        val event: ProgramEvent by events.collectAsState(initial = ProgramEvent.Init as ProgramEvent)

        when (val currentEvent = event) {
            is ProgramEvent.ChangeDate -> selectedDate = currentEvent.date
            is ProgramEvent.ChangeLocation -> selectedLocation = currentEvent.location
            ProgramEvent.Init -> {
                /*no-op*/
            }
        }
        val start = selectedDate.atStartOfDayIn(TimeZone.currentSystemDefault())
        val end = selectedDate.plus(1, DateTimeUnit.DAY).atTime(3, 0).toInstant(TimeZone.currentSystemDefault())
        val programListOrNull: ImmutableList<Program>? by getPrograms(selectedLocation, start, end).collectAsState(initial = null)

        val programList = programListOrNull ?: return ProgramModel.Loading
        val programs = programList.sortedBy { it.start }.toPersistentList()

        return ProgramModel.Data(
            locations,
            selectedLocation,
            dates,
            selectedDate,
            programs
        )
    }
}
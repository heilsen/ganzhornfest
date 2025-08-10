package de.heilsen.ganzhornfest.bus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.heilsen.ganzhornfest.core.GetOpeningDaysUseCase
import de.heilsen.ganzhornfest.core.SelectDefaultDateUseCase
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import javax.inject.Inject

class BusPresenter @Inject constructor(
    private val getDepartures: GetBusConnectionsUseCase,
    selectDefaultDate: SelectDefaultDateUseCase,
    getOpeningDays: GetOpeningDaysUseCase
) {
    private var destination: String by mutableStateOf("Amorbach")
    private val destinations: PersistentList<String> = persistentListOf("Amorbach", "Dahenfeld", "Neuberg")
    private var departureDate: LocalDate by mutableStateOf(selectDefaultDate())
    private val departureDates = getOpeningDays()

    @Composable
    fun present(events: Flow<BusEvent>): BusModel {
        val event: BusEvent by events.collectAsState(initial = BusEvent.Init as BusEvent)

        when (val currentEvent = event) {
            is BusEvent.ChangeDeparture -> departureDate = currentEvent.departure
            is BusEvent.ChangeDestination -> destination = currentEvent.destination
            BusEvent.Init -> {
                /*no-op*/
            }
        }
        val start = departureDate.atTime(19, 0)
        val end = departureDate.plus(1, DateTimeUnit.DAY).atTime(3, 0)
        val busConnectionList: PersistentList<BusConnection>? by getDepartures(
            destination,
            start.toInstant(TimeZone.currentSystemDefault()),
            end.toInstant(TimeZone.currentSystemDefault())
        ).collectAsState(initial = null)

        val connections = busConnectionList ?: return BusModel.Loading
        val sortedConnections = connections.sortedBy { it.departureAt }.toPersistentList()

        return BusModel.Data(
            destination,
            destinations,
            departureDate,
            departureDates,
            sortedConnections
        )
    }
}
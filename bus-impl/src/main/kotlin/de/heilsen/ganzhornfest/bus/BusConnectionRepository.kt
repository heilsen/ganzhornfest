package de.heilsen.ganzhornfest.bus

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import de.heilsen.ganzhornfest.database.GanzhornfestDb
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

/**
 * Date Time is stored as Zulu time (Z = +T00:00) in our SQLite DB.
 */
class BusConnectionRepository @Inject constructor(
    private val ganzhornfestDb: GanzhornfestDb
) {
    fun getBusConnection(
        destination: String,
        startIsoDateTime: String,
        endIsoDateTime: String
    ): Flow<List<BusConnection>> = ganzhornfestDb
        .busConnectionQueries
        .getBusConnection(
            destination,
            startIsoDateTime,
            endIsoDateTime,
            ::toBusConnection
        )
        .asFlow()
        .mapToList(Dispatchers.IO)

    private fun toBusConnection(
        departureUnixEpoch: String,
        busLine: String,
        stops: String?,
        destination: String,
    ): BusConnection = BusConnection(
        Busline(
            busLine,
            destination,
            stops?.split("-")?.toPersistentList() ?: persistentListOf()
        ),
        Instant.parse(departureUnixEpoch).toLocalDateTime(TimeZone.currentSystemDefault())
    )
}